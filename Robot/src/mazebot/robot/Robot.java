package mazebot.robot;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import mazebot.behaviors.Behavior;
import mazebot.robot.mapmaker.MapMaker;
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
	private MapMaker mapMaker;

	// private static File searchingSound = new File("zSearching.wav");
	public static enum Sounds {
		UP, DOWN;
	}

	public static enum Wav {
		SEARCHING, CENTERING, APPROACHING, ENGAGING;
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
		
		say("lastColor: " + lastColor);

		setLastColor(color);
//		switch (currentOrientation) {
//		case NORTH:
//			lineFollowNorth();
//			break;
//		case SOUTH:
//			lineFollowSouth();
//			break;
//		case EAST:
//			lineFollowEast();
//			break;
//		case WEST:
//			lineFollowWest();
//			break;
//		}
	}

	private void turnLeft() {
		leftDriveMotor.setSpeed(SPEED - 50);
		rightDriveMotor.setSpeed(SPEED);
	}

	private void turnHardLeft() {
		leftDriveMotor.setSpeed(SPEED - 100);
		rightDriveMotor.setSpeed(SPEED);
	}

	private void turnRight() {
		leftDriveMotor.setSpeed(SPEED);
		rightDriveMotor.setSpeed(SPEED - 50);
	}

	private void turnHardRight() {
		leftDriveMotor.setSpeed(SPEED);
		rightDriveMotor.setSpeed(SPEED - 100);
	}

	private void lineFollowNorth() {

//		if (color == WHITE && offBlack == false && offGreen == false) {
//			if (lastColor == BLACK) {
//				offBlack = true;
//				turnHardLeft();
//			} else if (lastColor == GREEN) {
//				offGreen = true;
//				turnHardRight();
//			}
//		} else if (color == BLACK) {
//			offBlack = false;
//			turnLeft();
//		} else if (color == GREEN) {
//			offGreen = false;
//			turnRight();
//		} else if(offBlack) {
//			turnHardLeft();
//		} else if(offGreen) {
//			turnHardRight();
//		}
		int color = getColorId();
		if(color == BLACK) {
			turnLeft();
		}else if(color == WHITE) {
			turnRight();
		}
		
		say("lastColor: " + lastColor);

		setLastColor(color);

	}

	private void lineFollowSouth() {
		int color = getColorId();
		if(color == BLACK) {
			turnLeft();
		}else if(color == WHITE) {
			turnRight();
		}
		
		say("lastColor: " + lastColor);

		setLastColor(color);

	}

	private void lineFollowEast() {
		int color = getColorId();
		if(color == BLACK) {
			turnLeft();
		}else if(color == WHITE) {
			turnRight();
		}
		
		say("lastColor: " + lastColor);

		setLastColor(color);

	}

	private void lineFollowWest() {
		int color = getColorId();
		if(color == BLACK) {
			turnLeft();
		}else if(color == WHITE) {
			turnRight();
		}
		
		say("lastColor: " + lastColor);

		setLastColor(color);

	}

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

	public void map() {
		mapMaker.map();
	}

	public void setOrientation(Orientation orientation) {
		currentOrientation = orientation;
	}

	public static void say(String message) {
		System.out.println(message);
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
//			case SEARCHING: Sound.playSample(searchingSound, Sound.VOL_MAX); break; 
//	        case CENTERING: Sound.playSample(centeringSound, Sound.VOL_MAX); break; 
//			case APPROACHING: Sound.playSample(approachingSound, Sound.VOL_MAX); break; 
//	        case ENGAGING: Sound.playSample(engagingSound, Sound.VOL_MAX); break; 
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
}
