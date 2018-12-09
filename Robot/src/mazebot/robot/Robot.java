package mazebot.robot;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import mazebot.behaviors.Behavior;
import mazebot.robot.mapmaker.MapMaker;
import mazebot.robot.mapmaker.mapmakersupport.Direction;
import mazebot.robot.sensors.ColorSensor;
import java.io.File;

public class Robot {

	public static final int WHITE = 6;
	public static final int BLACK = 7;
	public static final int GREEN = 2;
	public static final int RED = 0;
	public static final float SPEED = 150f;

	private EV3LargeRegulatedMotor leftDriveMotor;
	private EV3LargeRegulatedMotor rightDriveMotor;
	private ColorSensor colorSensor;
	private Thread colorSensorThread;
	private int lastColor;
	private boolean offGreen = false;
	private boolean offBlack = false;

	private Behavior currentBehavior;
	public MapMaker mapMaker;

	private static File mappingSound = new File("Mapping.wav");
	private static File turningSound = new File("Turning.wav");
	private static File completedSound = new File("Completed.wav");
	private static File backtrackingSound = new File("Backtraking.wav");
	private static File resolvingSound = new File("Resolving.wav");
	private static File travelingSound = new File("Traveling.wav");
	
	public static enum Sounds {
		UP, DOWN;
	}

	public static enum Wav {
		 MAPPING, TURNING, COMPLETED, BACKTRACKING, RESOLVING, TRAVELING;
	}

	public static enum Orientation {
		NORTH, EAST, SOUTH, WEST;
	} // DO NOT CHANGE THIS YA BOOBS

	private Orientation currentOrientation;

	public Robot() {
		initialize();
	}

	private void initialize() {
		leftDriveMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		rightDriveMotor = new EV3LargeRegulatedMotor(MotorPort.A);
		currentOrientation = Orientation.NORTH;
		mapMaker = new MapMaker(this);
		colorSensor = new ColorSensor(SensorPort.S1);
		colorSensorThread = new Thread(colorSensor);
		colorSensorThread.start();
		lastColor = -1;
		resetSpeed();
		halt();
	}

	public void runBehavior() {
		if (currentBehavior != null) {
			currentBehavior.run();
		} else {
			say("I dont know what to do!");
		}
	}

	public void changeBehavior(Behavior newBehavior) {
		System.gc();
		this.currentBehavior = newBehavior;
	}

	public int getColorId() {
		return colorSensor.getColor();
	}

	public void setLastColor(int color) {
			if (color == RED)
				lastColor = RED;
			else if (color == BLACK)
				lastColor = BLACK;
			else if (color == GREEN)
				lastColor = GREEN;
			else if (color == WHITE)
				lastColor = WHITE;
			else if (color == -1)
				lastColor = -1;
	}


	public Orientation getCurrentOrientation() {
		return currentOrientation;
	}

	public void getCurrentOrientation(Orientation newOrientation) {
		currentOrientation = newOrientation;
	}

	public void forward() {
		leftDriveMotor.forward();
		rightDriveMotor.forward();
	}

	public void lineFollow() {
		int color = getColorId();
		if(color == BLACK) {
			turnLeft();
		}else if(color == WHITE) {
			turnRight();
		}
	}

	private void turnLeft() {
		leftDriveMotor.setSpeed(SPEED - 50);
		rightDriveMotor.setSpeed(SPEED);
	}

//	private void turnHardLeft() {
//		leftDriveMotor.setSpeed(SPEED - 100);
//		rightDriveMotor.setSpeed(SPEED);
//	}

	private void turnRight() {
		leftDriveMotor.setSpeed(SPEED);
		rightDriveMotor.setSpeed(SPEED - 50);
	}

//	private void turnHardRight() {
//		leftDriveMotor.setSpeed(SPEED);
//		rightDriveMotor.setSpeed(SPEED - 100);
//	}


	public void pivotRight() {
		resetSpeed();
		leftDriveMotor.forward();
		rightDriveMotor.backward();
	}

	public void pivotLeft() {
		resetSpeed();
		leftDriveMotor.backward();
		rightDriveMotor.forward();
	}

	public void halt() {
		leftDriveMotor.stop(true);
		rightDriveMotor.stop(true);
	}

	public void resetSpeed() {
		leftDriveMotor.setSpeed(SPEED);
		rightDriveMotor.setSpeed(SPEED);
	}
	
	public void setOrientation(Orientation orientation) {
		currentOrientation = orientation;
	}

	public static void say(String message) {
		LCD.drawString(message, 1, 1);
	}

	public void beep(Sounds sound) {
		switch (sound) {
		case UP:
			Sound.beepSequenceUp();
			break;
		case DOWN:
			Sound.beepSequence();
			break;
		default:
			Sound.beep();
			break;
		}
	}

	public static void beep() {
		Sound.beep();
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
		switch (wav) {
//			case MAPPING: Sound.playSample(mappingSound, Sound.VOL_MAX); break; 
//	        case TURNING: Sound.playSample(turningSound, Sound.VOL_MAX); break; 
//			case COMPLETED: Sound.playSample(completedSound, Sound.VOL_MAX); break; 
//	        case RESOLVING: Sound.playSample(resolvingSound, Sound.VOL_MAX); break; 
//	        case TRAVELING: Sound.playSample(travelingSound, Sound.VOL_MAX); break; 
//	        case BACKTRACKING: Sound.playSample(backtrackingSound, Sound.VOL_MAX); break; 
		default:
			Sound.beep();
			break;
		}
	}

	public static void clearDisplay(int lines) {
		for (int i = 0; i < lines; i++) {
			System.out.println("");
		}
	}
	
	public static Orientation opposite(Orientation o) {
		switch(o) {
		 case NORTH:
	            return Orientation.SOUTH;
	        case SOUTH:
	            return Orientation.NORTH;
	        case EAST:
	            return Orientation.WEST;
	        case WEST:
	            return Orientation.EAST;
	        //return a North if the parameter is wrong
	        default:
	        	return Orientation.NORTH;
		}
	}
}
