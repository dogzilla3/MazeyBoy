package mazebot.robot;


import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import mazebot.behaviors.Behavior;
import mazebot.behaviors.TurnTo;
import mazebot.robot.mapmaker.MapMaker;
import mazebot.robot.sensors.ColorSensor;
import java.io.File;


public class Robot {

	private final float SPEED = 150f;
	private EV3LargeRegulatedMotor leftDriveMotor;
	private EV3LargeRegulatedMotor rightDriveMotor;	
	private ColorSensor colorSensor;
	private Behavior behavior;
	private Orientation currentOrientation;
	private MapMaker mapMaker;
	private int lastColor;
	//private static File searchingSound = new File("zSearching.wav");
	public static enum Sounds{ UP, DOWN; }
	public static enum Wav{ SEARCHING, CENTERING, APPROACHING, ENGAGING; }
	public static enum Orientation{ NORTH, EAST, SOUTH, WEST; } //DO NOT CHANGE THIS YA BOOBS
	
	public Robot(){
		leftDriveMotor = new EV3LargeRegulatedMotor(MotorPort.A);
		rightDriveMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		BaseRegulatedMotor[] array = {rightDriveMotor};
		leftDriveMotor.synchronizeWith(array);
		currentOrientation = Orientation.NORTH;
		mapMaker = new MapMaker(this);
		colorSensor = new ColorSensor(SensorPort.S1); 
		colorSensor.start();
		lastColor = -1;
		resetSpeed();
		halt();
	}
	
	public void runBehavior() {
		if(behavior != null) {
			behavior.run();
		} else {
			say("I dont know what to do!");
		}
	}
	
	public void changeBehavior(Behavior newBehavior) {
		this.behavior = newBehavior;
	}
	
	public static void say(String message) {
		System.out.println(message);
	}
	
	public void beep(Sounds sound) {
        switch (sound) { 
        case UP: Sound.beepSequenceUp(); break; 
        case DOWN: Sound.beepSequence(); break; 
        default: Sound.beep(); break; 
        } 
	}
	
	public static void beep() {
		Sound.beep();
	}
	
	public int getColorId() {
		return colorSensor.getColor();
	}
	
	public Orientation getCurrentOrientation() {
		return currentOrientation;
	}
	
	public void getCurrentOrientation(Orientation newOrientation) {
		currentOrientation = newOrientation;
	}
	
	public void forward() {
		leftDriveMotor.startSynchronization();
		leftDriveMotor.forward();
		rightDriveMotor.forward();
		
		switch(currentOrientation) {
			case NORTH: lineFollowNorth(); break;
			case SOUTH: lineFollowSouth(); break;
			case EAST:	lineFollowEast(); break;
			case WEST:	lineFollowWest(); break;
		}
			
		leftDriveMotor.endSynchronization();
	}
	
	private void turnLeft() {
		leftDriveMotor.setSpeed(SPEED - 40);
		rightDriveMotor.setSpeed(SPEED);
	}
	
	private void turnHardLeft() {
		leftDriveMotor.setSpeed(SPEED - 80);
		rightDriveMotor.setSpeed(SPEED);
	}
	private void turnRight() {
		leftDriveMotor.setSpeed(SPEED);
		rightDriveMotor.setSpeed(SPEED - 40);
	}
	
	private void turnHardRight() {
		leftDriveMotor.setSpeed(SPEED);
		rightDriveMotor.setSpeed(SPEED - 80);
	}
	
	// White == 6
	// Black == 7
	// Green == 2
	// Red == 0
	private void lineFollowNorth() {
		int color = getColorId();
		if (color == 7) {
			turnLeft();
		} else if (color == 2) {
			turnRight();
		} else if (color == 6) {
			if (lastColor == 7) {
				turnHardRight();
			} else if (lastColor == 2) {
				turnHardLeft();
			}
		} else {
			resetSpeed();
		}
		lastColor = color;
	}
	
	// White == 6
	// Black == 7
	// Green == 2
	// Red == 0
	private void lineFollowSouth() {
		int color = getColorId();
		if (color == 7) { //turn left
			turnRight();
		} else if (color == 2) { // turn right
			turnLeft();
		} else if (color == 6) {
			if (lastColor == 7) {
				turnHardRight();
			} else if (lastColor == 2) {
				turnHardLeft();
			}
		} else {
			resetSpeed();
		}
		lastColor = color;
	}
	
	private void lineFollowEast() {
		int color = getColorId();
		if (color == 7) {
			turnLeft();
		} else if (color == 2) {
			turnRight();
		} else if (color == 6) {
			if (lastColor == 7) {
				turnHardRight();
			} else if (lastColor == 2) {
				turnHardLeft();
			}
		} else {
			resetSpeed();
		}
		lastColor = color;
	}
	
	// White == 6
	// Black == 7
	// Green == 2
	// Red == 0
	
	private void lineFollowWest() {
		int color = getColorId();
		if (color == 7) { //turn left
			turnRight();
		} else if (color == 2) { // turn right
			turnLeft();
		} else if (color == 6) {
			if (lastColor == 7) {
				turnHardLeft();
			} else if (lastColor == 2) {
				turnHardRight();
			}
		} else {
			resetSpeed();
		}
		lastColor = color;
	}
	
	public void pivotTowards(Orientation newOrientation) {
		int currentDirection = currentOrientation.ordinal();
		int desiredDirection = newOrientation.ordinal();
		
		int turn = desiredDirection - currentDirection;
		say(""+currentDirection);
		say("" + desiredDirection);
		switch(turn) {
			case 3:
			case -1: {
				resetSpeed();
				leftDriveMotor.startSynchronization();
				leftDriveMotor.forward();
				rightDriveMotor.backward();
				leftDriveMotor.endSynchronization();
				say("left turn");
				break;
			}
			case 0: 
				break;
			default: {
				resetSpeed();
				leftDriveMotor.startSynchronization();
				leftDriveMotor.backward();
				rightDriveMotor.forward();
				leftDriveMotor.endSynchronization();
				say("right turn");
				break;
			}
		}
	}
	
//	public void pivotRight() {
//
//	}
//	
//	public void pivotLeft() {
//		resetSpeed();
//		leftDriveMotor.startSynchronization();
//		leftDriveMotor.forward();
//		rightDriveMotor.backward();
//		leftDriveMotor.endSynchronization();
//	}
//	
	public void updateOrientation() {
		
	}
	
	public void halt() {
		leftDriveMotor.startSynchronization();
		leftDriveMotor.stop(true);
		rightDriveMotor.stop(true);
		leftDriveMotor.endSynchronization();
	}
	
	public void resetSpeed() {
		leftDriveMotor.setSpeed(SPEED);
		rightDriveMotor.setSpeed(SPEED);
	}
	
	public void map() {
		mapMaker.map();
	}
	
	public void setOrientation(Orientation orientation) {
		currentOrientation = orientation;
	}
	
	public static void debugPause(String message) {
		LCD.clearDisplay();
		Sound.beep();
		say(message);
		Button.LEDPattern(9);
		Button.waitForAnyPress();
		Button.LEDPattern(1);
	}
	
	public static void playSound(Wav wav) {
		switch(wav) {
//			case SEARCHING: Sound.playSample(searchingSound, Sound.VOL_MAX); break; 
//	        case CENTERING: Sound.playSample(centeringSound, Sound.VOL_MAX); break; 
//			case APPROACHING: Sound.playSample(approachingSound, Sound.VOL_MAX); break; 
//	        case ENGAGING: Sound.playSample(engagingSound, Sound.VOL_MAX); break; 
	        default: Sound.beep(); break; 
		}
	}
	
	public static void clearDisplay(int lines) {
		for (int i=0; i < lines; i++) { 
			System.out.println("");
		}
	}
}
