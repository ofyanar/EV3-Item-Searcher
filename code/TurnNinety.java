

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTTouchSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

/**
 * @author  Omer Yanar
 * @author  Kheyan Patel
 * @version 1.0
 * 
 *TurnNinety is a class that implements the Behavior interface.
 *It's main purpose is to coordinate which direction the EV3 turns
 *at the end of each straight line, and track the number of times
 *the EV3 has traversed a complete path.
 */
public class TurnNinety implements Behavior {
	private MovePilot pilot;
	private Flags flags;
	
	/**
	 * a boolean flag used to check whether the EV3 has turned +90 degrees
	 */
	private boolean tracker_90 = false;
	/**
	 * a boolean flag used to check whether the EV3 has turned -90 degrees
	 */
	private boolean tracker_neg90 = false;
	/**
	 * a boolean flag used in this class
	 */
	private boolean flag;
	
	/**
	 * a field storing the touch sensor
	 */
	private NXTTouchSensor touchSensor = Calibrate.getTouchSensor();
	/**
	 * sample provider field, used to collect samples from the touch sensor
	 */
	private SampleProvider spT = touchSensor.getTouchMode();
	/**
	 * a field used to store the latest sample collected from the sample
	 * provider for the touch sensor
	 */
	private float[] touch = new float[1];
	
	/**
	 * a field storing the ultra sonic sensor
	 */
	private EV3UltrasonicSensor usSensor = Calibrate.getUsSensor();
	/**
	 * sample provider field, used to collect samples from the ultra sonic sensor
	 */
	private SampleProvider spUS = usSensor.getDistanceMode();
	/**
	 * a field used to store the latest sample collected from the sample
	 * provider for the ultra sonic sensor
	 */
	private float[] distance = new float[1];
	
	/**
	 * the constructor takes two arguments, 
	 * 
	 * @param flags  an instantiated Flags object
	 * @param pilot  an instantiated MovePilot object
	 */
	public TurnNinety(MovePilot pilot, Flags flags) {
		this.pilot=pilot;
		this.flags = flags;
	}

	/**
	 *takeControl method allows the action method to run if it returns true
	 *uses the sample provider to collect samples from the touch sensor.
	 *
	 * @return true if the sample collected from the touch sensor 
	 * 		   is 1, which indicated the touch sensor has been pressed
	 * 		   by the EV3 hitting an object.
	 * 		   otherwise false.
	 */
	@Override
	public boolean takeControl() {
		spT.fetchSample(this.touch, 0);
		flag = (this.touch[0] ==(float) 1);
		return flag;
	}

	/**
	 * makes the MovePilot of the robot go backwards, the delay
	 * allows the robot to back up a finite distance.
	 * 
	 * Since the robot is going up and down the room, the direction it turns 
	 * at the end of each straight line differs, i.e. it must turn either clock-wise or
	 * anti clock-wise.
	 * 
	 * since we assume the robot's starting position is (0,0) in the room it is searching,
	 * we want it's first turn to be +90 degrees. once it turns +90 degrees it sets the 
	 * boolean flag for +90 degree to true.
	 * 
	 * the succeeding turns are dependent on which one of the +90/-90 boolean flags are true or false,
	 * turning in the direction that is set to false.
	 * 
	 * The complete path the EV3 takes in its search, is up and down traversal parallel to the width of the room,
	 * and the up and down traversal parallel to the height of the room, which covers the entirety of the room. 
	 * The maximum traversal of the room are these two traversals. if the item is not found within these two 
	 * traversals, the program must stop.
	 * 
	 * in order to track this, every time the EV3 comes to the end of a straight line, backs up and turns
	 * -90/+90 degrees, we must check it's distance to the wall and if it is less than 15cm increments 
	 * the traversed field of the Flags object and then turns 180 degrees in order to start its new path, because
	 * it should be perpendicular to its previous path, if it hasn't already completed two paths.
	 * 
	 * if the distance from the wall is not less than 15cm, the EV3 goes a short distance forward and turns
	 * +90/-90 degrees again in order to carry on with its current traversal.
	 * 
	 * note: the angles are 5/10 degrees larger than intended to compensate for friction
	 */
	@Override
	public void action() {
		pilot.backward();
		Delay.msDelay(400);
		if (tracker_90 == false) {//if it hasnt turned 90 executes this
			
			pilot.rotate(95);
			Delay.msDelay(300);
			
			spUS.fetchSample(this.distance, 0);
			if(distance[0]<(float)0.15 ) {
				flags.incTraversed();
				pilot.rotate(190);
				tracker_90 = true;
				tracker_neg90 = false;}
			else {
				pilot.forward();
				Delay.msDelay(300);
				pilot.rotate(95);
				tracker_90 = true;
				tracker_neg90 = false;}
			}
		else if (tracker_neg90 == false) {//if it hasnt turned -90 executes this
			pilot.rotate(-95);
			Delay.msDelay(300);
			spUS.fetchSample(this.distance, 0);
			if(distance[0]<(float)0.15 ) {
				flags.incTraversed();
				pilot.rotate(-190);
				tracker_neg90 = true;
				tracker_90 = false;
			}
			else {
				pilot.forward();
				Delay.msDelay(300);
				pilot.rotate(-95);
				tracker_neg90 = true;
				tracker_90 = false;}
		}
	}

	/**
	 * sets the boolean flag "flag" to false
	 */
	@Override
	public void suppress() {
		flag = false;
	}

}
