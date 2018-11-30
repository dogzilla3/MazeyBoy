package mazebot.robot.mapmaker.mapmakersupport;

import mazebot.robot.Robot.Orientation;

public class DestinationPoint{
    
	Direction north;
    Direction south;
    Direction east;
    Direction west;

    DestinationPoint previousDestination;
    boolean deadEnd;
    boolean undiscovered;
    
    public DestinationPoint() {
    	undiscovered = true;
    }

    public DestinationPoint(Direction n, Direction s, Direction e, Direction w){
        north = new Direction(n);
        south = new Direction(s);
        east = new Direction(e);
        west = new Direction(w);
        deadEnd = false;
        undiscovered = false;
    }
    
    public DestinationPoint(Direction n, Direction s, Direction e, Direction w, DestinationPoint previous){
        this(n,s,e,w);
        previousDestination = previous;
    }
    
    //Getters
    public Direction getDirection(Orientation direction) {
    	switch (direction) {
	        case NORTH:
	            return north;
	        case SOUTH:
	            return south;
	        case EAST:
	            return east;
	        case WEST:
	            return west;
	        //return a wall if the parameter is wrong
	        default:
	        	return new Direction(true);
    	}
    }
    
    public boolean getVisitStatus(Orientation direction) {
    	switch (direction) {
	        case NORTH:
	            return north.hasVisited();
	        case SOUTH:
	            return south.hasVisited();
	        case EAST:
	            return east.hasVisited();
	        case WEST:
	            return west.hasVisited();
	        default:
	        	return true;
	    }
    }
    
    public boolean hasUnvistedDirections(){
    	
        switch (cameFrom()) {
	        case NORTH:
	            return !(south.hasVisited() && east.hasVisited() && west.hasVisited());
	        case SOUTH:
	            return !(north.hasVisited() && east.hasVisited() && west.hasVisited());
	        case EAST:
	            return !(north.hasVisited() && south.hasVisited() && west.hasVisited());
	        case WEST:
	            return !(north.hasVisited() && south.hasVisited() && east.hasVisited());
	        //You are trapped in a box if you get here
	        default:
	        	return false;
		}
    }

    public DestinationPoint getPreviousDestination(){
        return previousDestination;
    }

    public Orientation cameFrom(){
    	if(north.hasLeftFrom())
            return Orientation.NORTH;
        else if(south.hasLeftFrom())
            return  Orientation.SOUTH;
        else if(east.hasLeftFrom())
            return  Orientation.EAST;
        else if(west.hasLeftFrom())
            return  Orientation.WEST;
        //If all else fails I gues it is north...?
        else
            return  Orientation.NORTH;
    }
    
    public boolean isUndiscovered() { return undiscovered; }
    public boolean isDeadEnd() { return deadEnd; }
    
    
    //Setters
    public void setPreviousDestination(DestinationPoint previous){
        previousDestination = previous;
    }

    public void setVisited(Orientation direction){
        switch (direction) {
            case NORTH:
                north.setVisited(true);
                break;
            case SOUTH:
                south.setVisited(true);
                break;
            case EAST:
                east.setVisited(true);
                break;
            case WEST:
                west.setVisited(true);
                break;
            default:
                break;
        }
    }
    
    public void setDeadEnd(boolean de) {deadEnd = de;}
    
    @Override
    public String toString() {
    	return getRawInfo();
    }
    
    public String getRawInfo() {
		if(isUndiscovered())
			return "????";
		
		return 	east.getDirectionInfo()
				+ north.getDirectionInfo()
				+ south.getDirectionInfo()
				+ west.getDirectionInfo();
	}
}