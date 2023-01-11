

import java.io.File;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

/**
 * @author  Omer Yanar
 * @author  Kheyan Patel
 * @version 1.0
 * 
 *FoundReaction is a class that implements the Behavior interface.
 *It's main purpose is to react when it finds/doesn't find the item 
 *it has been searching for 
 */
public class FoundReaction implements Behavior {//group
	
	private Flags flags;

	/**
	 * the constructor takes one argument, 
	 * 
	 * @param flags an instantiated Flags object
	 */
	public FoundReaction(Flags flags) {
		this.flags = flags;
	}

	/**
	 *takeControl method allows the action method to run if it returns true
	 *
	 * @return true if the Flags object fields found is true
	 * 		   or traversed is equal to 2, and foundReact is false. 
	 * 		   otherwise false.
	 */
	@Override
	public boolean takeControl() {
		return (flags.getFound() || flags.getTraversed() == 2) && !flags.getFoundReact();
	}

	/**
	 * if the item has been found, the found field of the Flags
	 * object will be true, hence executing the if block. this makes
	 * the EV3 robot retrieve the pose of the object found from the itemPose
	 * field of the Flags object and display it on the LCD screen of the EV3.
	 * finally the EV3 creates a File object with the .wav file name and plays 
	 * a tune to indicate it has found the object it has been searching.
	 * 
	 *  if the item has not been found, the traversed field of the Flags object 
	 *  will be equal to 2, hence executing the else if block. this makes
	 * the EV3 robot display "Item Not Found" on the LCD screen of the EV3.
	 * finally the EV3 plays a beep sequence to indicate it has not found the 
	 * object it has been searching.
	 * 
	 * the delay of ten seconds allows the display of the outcome
	 * of the search until the robot is handled and the program ends.
	 * 
	 * finally the found React field of the Flags object is set to true.
	 */
	@Override
	public void action() {
		
		if(flags.getFound()) {
			LCD.clear();
			LCD.drawString("Item's pose:", 1, 2);
			LCD.drawString(""+flags.getItemPose(), 1, 3);
			
			tune(new File("foundReact.wav"));
			
			//play found tune
		}
		
		else if(flags.getTraversed() == 2) {
			LCD.clear();
			LCD.drawString("Item Not Found", 1, 2);
			Sound.beepSequence();
		}
		
		Delay.msDelay(10000);
		
		flags.setFoundReact(true);
		
		
		
		
	}

	@Override
	public void suppress() {

	}
	
	/**
	 * This method allows the audial File object it has been passed 
	 * to be played by the robot.
	 * 
	 * @param tune the object for the .wav file which is expected to play
	 */
	private void tune(File tune) {
		int player = Sound.playSample(tune);
		try {
			Thread.sleep(player);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
