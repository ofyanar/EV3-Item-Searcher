

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTTouchSensor;

/**
 * @author  Omer Yanar
 * @author  Kheyan Patel
 * @version 1.0
 *
 *Calibrate is a class that allows us to calibrate the sensors used throughout the program
 *only once by initialising them in finalised private static fields. 
 *
 *Each sensor is held by the field with the field name that represents them.
 *all three fields have getter methods which allows them to be accessed by other
 *classes within the same package Calibrate is in, without the need to instantiate
 *a Calibrate object.
 */
public class Calibrate {
	private final static EV3UltrasonicSensor  usSensor = new EV3UltrasonicSensor(SensorPort.S1);
	private final static EV3ColorSensor  colorSensor = new EV3ColorSensor(SensorPort.S2);
	private final static  NXTTouchSensor touchSensor = new NXTTouchSensor(SensorPort.S3);
	

	public Calibrate() {}
	
	/**
	 * getter method for the touchSensor field which holds the NXT touch sensor
	 * 
	 * @return the touchSensor field
	 */
	public static NXTTouchSensor getTouchSensor() {return touchSensor;}
	/**
	 * getter method for the colorSensor field which holds the EV3 colour sensor
	 * 
	 * @return the colorSensor field
	 */
    public static EV3ColorSensor getColorSensor() {return colorSensor;}
    /**
	 * getter method for the usSensor field which holds the EV3 ultrasonic sensor
	 * 
	 * @return the usSensor field
	 */
    public static EV3UltrasonicSensor getUsSensor() {return usSensor;}

}
