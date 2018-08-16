
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class initSensor {
	public EV3ColorSensor cl; // color sensor
	public EV3UltrasonicSensor us; // ultrasonic sensor
	
	
	public void initSensor() {
	}
	
	public void initColor(EV3ColorSensor cl) {
		cl = new EV3ColorSensor(SensorPort.S1);
		
	}
	
	public void initUltra(EV3UltrasonicSensor us) {
		us = new EV3UltrasonicSensor(SensorPort.S4);
		
	}
}
