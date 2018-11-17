package mazebot.robot.sensors;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import mazebot.robot.Robot;

// White == 6
// Black == 7
// Green == 2
// Red == 0

public class ColorSensor implements Runnable {

	private volatile int foundColor = -1;
  
    private EV3ColorSensor ev3ColorSensor;
    
    @Override
    public void run() {
    	while(true) { // Continuously get the color from the sensor
        	foundColor = ev3ColorSensor.getColorID();
    	}
    }
	
	public ColorSensor(Port port) {
		ev3ColorSensor = new EV3ColorSensor(port);
		ev3ColorSensor.setFloodlight(Color.WHITE); 
	}

	public int getColor() {
		return foundColor;
	}
}
