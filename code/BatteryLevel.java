

import lejos.hardware.Battery;


import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

/**
 * @author  Omer Yanar
 * @author  Kheyan Patel
 * @version 1.0
 *
 *BatteryLevel is a class that implements the Behavior interface.
 *It's main purpose is to let another Behavior (i.e. EmergencyStop) know it's time to
 *end the program in the Main method and exit the program gracefully
 *when the battery voltage level is below a predetermined certain level. 
 */
public class BatteryLevel implements Behavior {
	/**
	 * the battery voltage level below which is considered low
	 */
	final private float lowVoltage = 6.0f ;
	/**
	 * a boolean flag used in this class
	 */
	private boolean flag = false;
	
	private Flags flags;
	private MovePilot pilot;

	/**
	 * the constructor takes two arguments, 
	 * 
	 * @param pilot an instantiated MovePilot object
	 * @param flags an instantiated Flags object
	 */
	public BatteryLevel(MovePilot pilot, Flags flags) {
		this.pilot = pilot;
		this.flags = flags;
	}
	
	/**
	 *takeControl method allows the action method to run if it returns true
	 *
	 * @return true if the battery level of the robot is below 6.0 Volts
	 * 		   and the lowBatLevel field of the Flags object is false.
	 * 		   otherwise false.
	 */
	@Override
	public boolean takeControl() {
		this.flag = Battery.getVoltage() < this.lowVoltage ;
		return this.flag && !flags.getLowBatLevel();
	}
	
	/**
	 * makes the MovePilot of the robot halt the robot,
	 * makes the robot beep twice and display the text
	 * "LOW BATTERY LEVEL" on the robots LCD screen for
	 * five seconds. Finally it sets the lowBatLevel field
	 * of the Flags object to true.
	 */
	@Override
	public void action() {
		pilot.stop();
		Sound.twoBeeps();
		
		LCD.clear();
		LCD.drawString("LOW BATTERY", 2,2);
		LCD.drawString("LEVEL", 2,3);
		Delay.msDelay(5000);

		flags.setLowBatLevel(true);
		

	}

	/**
	 * sets the boolean flag "flag" to false
	 */
	@Override
	public void suppress() {
		flag = false;

	}

}
