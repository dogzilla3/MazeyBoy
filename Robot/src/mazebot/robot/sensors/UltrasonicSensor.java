package mazebot.robot.sensors;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.NXTUltrasonicSensor;
import lejos.robotics.RangeFinder;
import lejos.robotics.SampleProvider;

public class UltrasonicSensor implements RangeFinder{
	
	NXTUltrasonicSensor sensor;
	SampleProvider rangeProvider;
	float[] range;
	
	public UltrasonicSensor(Port port) {
		sensor = new NXTUltrasonicSensor(port);
		rangeProvider = sensor.getDistanceMode();
		range = new float[5];
	}
	
	public void close() {
		sensor.close();
	}

	@Override
	public float getRange() {
		rangeProvider.fetchSample(range, 0);
		return range[0];
	}

	@Override
	public float[] getRanges() {
		rangeProvider.fetchSample(range, 0);
		return range;
	}
}
