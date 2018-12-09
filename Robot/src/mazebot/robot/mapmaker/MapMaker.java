package mazebot.robot.mapmaker;


import java.util.ArrayList;
import java.util.Stack;

import lejos.hardware.port.SensorPort;
import mazebot.robot.Robot;
import mazebot.robot.Robot.Orientation;
import mazebot.robot.mapmaker.mapmakersupport.*;
import mazebot.robot.mapmaker.mapmakersupport.DestinationPoint;
import mazebot.robot.sensors.IRSensor;
import mazebot.robot.sensors.UltrasonicSensor;

/*Reading the map based and directional moves
 * 0,0-->x
 * |
 * V
 * y	
 *   
 *      S 
 * 	    ^
 * 	    |
 * E<---R--->W
 * 	    |
 *      v
 *      N
 */

public class MapMaker {
	
	private UltrasonicSensor leftWallSensor;
    private UltrasonicSensor rightWallSensor;
    private IRSensor frontWallSensor;
	private Orientation currentOrientation;
	public boolean backtracking;	//TODO: Make Private after testing map traversal.
	
	public  ArrayList<ArrayList<DestinationPoint>> map;
	public  Path currentPath;
	public 	Stack<Orientation> solvedPath;
	private DestinationPoint newPoint;
	

	/*
	 *  Constructor initializes the map maker
	 */  
	public MapMaker(Robot bot) {
		leftWallSensor = new UltrasonicSensor(SensorPort.S4);
	    rightWallSensor = new UltrasonicSensor(SensorPort.S3);
	    frontWallSensor = new IRSensor(SensorPort.S2);
		currentOrientation = bot.getCurrentOrientation();
		backtracking = false;
		//Map and Path
		map = new ArrayList<ArrayList<DestinationPoint>>();
		currentPath = new Path();
		solvedPath = new Stack<Orientation>();
		//Robot.playSound(Robot.Wav.MAPPING);
	}
	
	public Orientation traverseMap() {
		if(solvedPath.empty()) {	
			if(backtracking) {
				return backTrack();
			}else {
				//get input from IR
				Float fRange = frontWallSensor.getRange();
				Direction iR = new Direction(fRange < 20f);
				
				//get input from left Ultrasonic
				Float lRange = rightWallSensor.getRange();
				Direction lUS = new Direction(lRange < 0.2f);
				
				//get input from right Ultrasonic
				Float rRange = leftWallSensor.getRange();
				Direction rUS =  new Direction(rRange < 0.2f);
				return progressTraverse(iR, lUS, rUS);
			}
		} else {
			return solvedPath.pop();
		}
	}
	
	public void setSolvedPath() {
		while (currentPath.getEndPoint() != currentPath.getStartPoint()) {
			solvedPath.push(Robot.opposite((currentPath.getEndPoint().cameFrom())));
			currentPath.backTrackPath();
		}
	}
	
	private Orientation backTrack() {
		if(currentPath.getEndPoint() == currentPath.getStartPoint()) {
			if(currentPath.getStartPoint().isDeadEnd()) {
				System.out.println("Maze is unsolvable");	//The maze has no exit
				return get180Orientation();
			}
			currentOrientation = currentPath.getStartPoint().cameFrom();
			backtracking = false;
			currentPath.getStartPoint().setDeadEnd(true);
			currentPath.getStartPoint().setVisited(currentOrientation);
		} 
		else currentOrientation = getNextDirection();
	
		return currentOrientation;
	}
	
	//Maps the current grid square at the current rotation of the robot
	private Orientation progressTraverse(Direction iR, Direction lUS, Direction rUS) {	
		//Cardinal Directions
		Direction north, south, east, west;	
		
		switch(currentOrientation){
			case NORTH:
				north = iR;
				south = new Direction(false, true);
				east = rUS;
				west = lUS;
				break;
			case SOUTH:
				north = new Direction(false, true);
				south = iR;
				east = lUS;
				west = rUS;
				break;
			case EAST:
				north = lUS;
				south = rUS;
				east = iR;
				west = new Direction(false, true);
				break;
			case WEST:
				north = rUS;
				south = lUS;
				east = new Direction(false, true);
				west = iR;
				break;
			default:
				//Trapped in a box if broken
				north = new Direction(true);
				south = new Direction(true);
				east = new Direction(true);
				west = new Direction(true);
				break;
		}
		
		DestinationPoint currentPoint = new DestinationPoint(north,south,east,west);
		
		
		//Starting point
		if(currentPath.getStartPoint() == null) {
			newPoint = currentPath.setStartPoint(currentPoint);
			ArrayList<DestinationPoint> newRow = new ArrayList<DestinationPoint>(1);
			newRow.add(newPoint);
			map.add(newRow);
		}
		//Appending existing map and path
		else {
			newPoint = currentPath.appendPath(currentPoint);
			appendMap();			
		}
		
		currentOrientation = getNextDirection();
		return currentOrientation;
	}
	
	//Add a new point to the map
	private void appendMap() {
		Coordinate prevPoint = getXYOfDestinationPoint(newPoint.getPreviousDestination());
		Coordinate possibleCurrentPoint = getNextPointCoordinate(currentOrientation, newPoint.getPreviousDestination());
		
		//The current point is not in the map
		if(possibleCurrentPoint.x < 0 || possibleCurrentPoint.y < 0) {
			//Rescales and adds the new point 
			switch(currentOrientation){
			case NORTH:
				if(prevPoint.y == map.size()-1)
					makeNewRowAtBound(prevPoint.x,prevPoint.y, false);
				break;
			case SOUTH:
				if(prevPoint.y == 0)
					makeNewRowAtBound(prevPoint.x,prevPoint.y, true);
				break;
			case EAST:
				if(prevPoint.x == 0) 
					makeNewColumnAtBound(prevPoint.x, prevPoint.y, true);
				break;
			case WEST:
				if(prevPoint.x == map.get(prevPoint.y).size()-1) 
					makeNewColumnAtBound(prevPoint.x, prevPoint.y, false);
				break;
			default:
				break;
			}
		}
		//The current point is in the map
		else{
			//The map has this point marked as undiscovered
			if(map.get(possibleCurrentPoint.y).get(possibleCurrentPoint.x).isUndiscovered()) {
				map.get(possibleCurrentPoint.y).remove(possibleCurrentPoint.x);
				map.get(possibleCurrentPoint.y).add(possibleCurrentPoint.x, newPoint);
			}
			//This point on the map is null 
			else
				map.get(possibleCurrentPoint.y).add(possibleCurrentPoint.x, newPoint);
		}
		
	}
	
	//The point is being added beyond the map's capacity in the north or south directions
	private void makeNewRowAtBound(int x, int y, boolean insertAtOrigin){
		ArrayList<DestinationPoint> newRow = new ArrayList<DestinationPoint>();
		for(int i = 0; i < map.get(0).size(); i++) {
			if(i == x)
				newRow.add(newPoint);
			else
				newRow.add(new DestinationPoint());
		}
		
		if(insertAtOrigin)
			map.add(0, newRow);
		else
			map.add(newRow);
	}
	//The point is being added beyond the map's capacity in the west or east directions
	private void makeNewColumnAtBound(int x, int y, boolean insertAtOrigin){
		for(int i = 0; i < map.size(); i++) {
			if(i == y) {
				if(insertAtOrigin) map.get(y).add(0, newPoint);
				else map.get(y).add(newPoint);
			}
			else {
				if(insertAtOrigin) map.get(i).add(0, new DestinationPoint());
				else map.get(i).add(new DestinationPoint());
			}
		}
	}
	
	//Gets the current coordinate of a point on the map.
	private Coordinate getXYOfDestinationPoint(DestinationPoint point){
		int x = 0;
		int y = 0;
		boolean foundIt = false;
		
		for(y = 0; y < map.size(); y++) {
			for(x = 0; x < map.get(y).size(); x++) 
				if(point == map.get(y).get(x)) {
					foundIt = true;
					break;
				}
			if(foundIt)
				break;
		}
		if(foundIt)
			return new Coordinate(x,y);
		else
			return new Coordinate(-1,-1);
	}
	
	//Get the orientation the robot needs to turn to.
	private Orientation getNextDirection() {
		Orientation nextDirection;
		
		//Forward is the best route
		if(!currentPath.getEndPoint().getDirection(currentOrientation).hasVisited() && !isNextPointVisted(currentOrientation)) {
			nextDirection = currentOrientation;
			backtracking = false;
		}
		//Turning left is the best route
		else if(!currentPath.getEndPoint().getDirection(getLeftOrientation()).hasVisited() && !isNextPointVisted(getLeftOrientation())) {
			nextDirection = getLeftOrientation();
			backtracking = false;
		}
		//Turning right is the best route
		else if(!currentPath.getEndPoint().getDirection(getRightOrientation()).hasVisited() && !isNextPointVisted(getRightOrientation())) {
			nextDirection = getRightOrientation();
			backtracking = false;
		}
		//I need to back track
		else {
			if(!backtracking) {
				backtracking = true;
			}
			nextDirection = currentPath.getEndPoint().cameFrom();	
			currentPath.getEndPoint().setVisited(nextDirection);
			currentPath.getEndPoint().setDeadEnd(true);	//Mark  previous point as a deadEnd
			currentPath.backTrackPath();				//Backtrack the path
			return nextDirection;
		}
		//set the direction the robot will go to visited
		currentPath.getEndPoint().setVisited(nextDirection);
		
		return nextDirection;
	}
	
	//get a new orientation based on the current orientation.
	private Orientation getLeftOrientation() {
		switch (currentOrientation) {
	        case NORTH:
	            return Orientation.WEST;
	        case SOUTH:
	            return Orientation.EAST;
	        case EAST:
	            return Orientation.NORTH;
	        case WEST:
	            return Orientation.SOUTH;
	        default:
	        	return currentOrientation;
		}
	}
	private Orientation getRightOrientation() {
		switch (currentOrientation) {
	        case NORTH:
	            return Orientation.EAST;
	        case SOUTH:
	            return Orientation.WEST;
	        case EAST:
	            return Orientation.SOUTH;
	        case WEST:
	            return Orientation.NORTH;
	        default:
	        	return currentOrientation;
		}
	}
	private Orientation get180Orientation() {
		switch (currentOrientation) {
	        case NORTH:
	            return Orientation.SOUTH;
	        case SOUTH:
	            return Orientation.NORTH;
	        case EAST:
	            return Orientation.WEST;
	        case WEST:
	            return Orientation.EAST;
	        default:
	        	return currentOrientation;
		}
	}
	
	//Returns the coordinate of the next point from the given point in the given direction. 
	private Coordinate getNextPointCoordinate(Orientation direction, DestinationPoint point) {
		Coordinate currentDestination = getXYOfDestinationPoint(point);
		int nX = currentDestination.x;
		int nY = currentDestination.y;
		
		switch(direction) {
			case NORTH:
				nY++;
				break;
			case SOUTH:
				nY--;
				break;			
			case EAST:
				nX--;
				break;				
			case WEST:
				nX++;
				break;				
			default:
				nX = -1;
				nY = -1;
				break;
		}	
		if((nX < 0 || nY < 0) || (nY >= map.size() || nX >= map.get(0).size())) {
			nX = -1;
			nY = -1;
		}
		
		return new Coordinate(nX,nY);
	}
	
	//Checks to see if the next possible point in a given direction has been visited.
	private boolean isNextPointVisted(Orientation direction) {
		Coordinate possibleNextPoint = getNextPointCoordinate(direction, currentPath.getEndPoint());
		//The possible next point in the given direction is on the map.
		if(possibleNextPoint.x >= 0 && possibleNextPoint.y >= 0) {
			//That point has already been discovered
			if(!map.get(possibleNextPoint.y).get(possibleNextPoint.x).isUndiscovered())
				return true;
		}
		//Either the possible next point was not on the map, or the next point is undiscovered.
		return false;
	}
	
	//Take a Map Maker map output a Visual Representation of the map
	public String mapToUnicodeString() {
		String mapString =   "";
		for(int y = 0; y < map.size(); y++) {
			for(int x = 0; x < map.get(y).size(); x++) {
				DestinationPoint current = map.get(y).get(x);
				mapString += getUnicodeVisualPointRepresentation(current.getRawInfo());
			}
			mapString += "\n";
		}
		return mapString;
	}
	public String mapToASCIIString() {
		String mapString =   "";
		for(int y = 0; y < map.size(); y++) {
			for(int x = 0; x < map.get(y).size(); x++) {
				DestinationPoint current = map.get(y).get(x);
				mapString += getASCIIVisualPointRepresentation(current.getRawInfo());
			}
			mapString += "\n";
		}
		return mapString;
	}
	public String mapToRawDataString() {
		String mapString =   "";
		for(int y = 0; y < map.size(); y++) {
			for(int x = 0; x < map.get(y).size(); x++) {
				DestinationPoint current = map.get(y).get(x);
				mapString += (current.getRawInfo() + ", ");
			}
			mapString += "\n";
		}
		return mapString;
	}
	//Accept Raw Data and give back the Visual Representation of that point
	public String getUnicodeVisualPointRepresentation(String rawInfo) {
		String info = "";
		//First Character
		if(rawInfo.startsWith("W")) info = "|";
		else {
			if(rawInfo.startsWith("V")) {
				if(rawInfo.charAt(1) == 'W') info = addUnderBar("←");
				else if(rawInfo.charAt(2) == 'W') info = addOverBar("←");
				else info = "←";
			} else {
				if(rawInfo.charAt(1) == 'W') {
					if(rawInfo.charAt(2) == 'W') info = underAndOverBar();
					else info = "_";
				}else if(rawInfo.charAt(2) == 'W') info = addOverBar(" ");
				else info = " ";
			}
		}
		//Second Character
		switch(rawInfo.substring(1,3)) {
			case "00": info += " "; break;
			case "AV":
			case "0V": info += "↑"; break;
			case "VA":
			case "V0": info += "↓"; break;
			case "VV": info += "↕"; break;
			case "WA":
			case "W0": info += "_"; break;
			case "WV": info += addUnderBar("↑"); break;
			case "AW":
			case "0W": info += addOverBar(" "); break;
			case "VW": info += addOverBar("↓"); break;
			case "WW": info += underAndOverBar(); break;
			default: return "???";
		}
		//Third Character
		if(rawInfo.endsWith("W")) 
			info += "|";
		else {
			String temp;
			if(rawInfo.endsWith("V")) {
				if(rawInfo.charAt(1) == 'W') temp = addUnderBar("→");
				else if(rawInfo.charAt(2) == 'W') temp = addOverBar("→");
				else temp = "→";
			} else {
				if(rawInfo.charAt(1) == 'W') {
					if(rawInfo.charAt(2) == 'W') temp = underAndOverBar();
					else temp = "_";
				}else if(rawInfo.charAt(2) == 'W') temp = addOverBar(" ");
				else temp = " ";
			}
			info += temp;
		}
		
		return info;
	}
	public String getASCIIVisualPointRepresentation(String rawInfo) {
		String info = "";
		//First Character
		switch(rawInfo.charAt(0)) {
			case 'W': 	info += '|'; break;
			case 'V': 	info += '<'; break;
			case '0': 	info += ' '; break;
			default: 	info += rawInfo.charAt(0); break;
		}
		//Second Character
		switch(rawInfo.charAt(1)) {
			case 'W': 	info += '_'; break;
			case 'V': 	info += 'v'; break;
			case '0': 	info += ' '; break;
			default: 	info += rawInfo.charAt(1); break;
		}
		//Third Character
		switch(rawInfo.charAt(2)) {
			case 'W': 	info += '-'; break;
			case 'V': 	info += '^'; break;
			case '0': 	info += ' '; break;
			default: 	info += rawInfo.charAt(2); break;
		}
		//Fourth Character
		switch(rawInfo.charAt(3)) {
			case 'W': 	info += '|'; break;
			case 'V': 	info += '>'; break;
			case '0': 	info += ' '; break;
			default: 	info += rawInfo.charAt(3); break;
		}
		return info;
	}
	//Support for Visual Representation
	public String addUnderBar(String str) {return ("\u0332" + str);}
	public String addOverBar(String str) {return ("\u0305" + str);}
	public String underAndOverBar() {return ("\u0305_");}
}
