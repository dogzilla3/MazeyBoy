package mazebot.robot.mapmaker;


import lejos.hardware.port.SensorPort;
import mazebot.robot.Robot;
import mazebot.robot.sensors.IRSensor;
import mazebot.robot.sensors.UltrasonicSensor;

public class MapMaker {
	
	private UltrasonicSensor leftWallSensor;
	private UltrasonicSensor rightWallSensor;
	private IRSensor frontWallSensor;
	
	Robot robot;
	/*
	 *  Constructor initializes the map maker
	 */  
	public MapMaker(Robot robot) {
		this.robot = robot;
		this.leftWallSensor = new UltrasonicSensor(SensorPort.S3);
		this.rightWallSensor = new UltrasonicSensor(SensorPort.S4);
		this.frontWallSensor = new IRSensor(SensorPort.S2);
		//Robot.playSound(Robot.Wav.CALCULATING);
	}

	//Maps the current grid square at the current rotation of the robot
	public void map() {
		
		leftWallSensor.getRange();
		/* put mapping stuff here */
		
		//get the current orientation
//		robot.getCurrentOrientation()
//		
//		//get input from IR
//		Direction iR = new Direction(/*if wall: true; else: false*/);
//		//get input from left Ultrasonic
//		Direction lUS = new Direction(/*if wall: true; else: false*/);
//		//get input from right Ultrasonic
//		Direction rUS =  new Direction(/*if wall: true; else: false*/);
//		
//		switch(/*Current orientation*/){
//			case NORTH:
//				north = iR;
//				south = new Direction(false, true);
//				east = rUS;
//				west = lUS;
//				break;
//			case SOUTH:
//				north = new Direction(false, true);
//				south = iR;
//				east = lUS;
//				west = rUS;
//				break;
//			case EAST:
//				north = lUS;
//				south = rUS;
//				east = iR;
//				west = new Direction(false, true);
//				break;
//			case WEST:
//				north = rUS;
//				south = lUS;
//				east = new Direction(false, true);
//				west = iR;
//				break;
//			default:
//			//you is not supposed to the be here
//			break;
	}


}
