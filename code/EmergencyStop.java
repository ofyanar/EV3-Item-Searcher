

import lejos.hardware.Button;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

/**
 * @author  Omer Yanar
 * @author  Kheyan Patel
 * @version 1.0
 *
 *EmergencyStop is a class that implements the Behavior interface.
 *It's main purpose is to end the program in the Main method and 
 *exit the program gracefully when the battery voltage level is low, 
 *or the ESCAPE button on the EV3 robot has been pressed. 
 */
public class EmergencyStop implements Behavior {
	private MovePilot pilot;
	
	private Flags flags;

	/**
	 * the constructor takes two arguments, 
	 * 
	 * @param pilot an instantiated MovePilot object
	 * @param flags an instantiated Flags object
	 */
	public EmergencyStop(MovePilot pilot, Flags flags) {
		this.pilot = pilot;
		this.flags = flags;
	}

	/**
	 *takeControl method allows the action method to run if it returns true
	 *
	 * @return true if the ESCAPE button on the EV3 is down, or the 
	 * 		   lowBatLevel field of the Flags object is true.
	 * 		   otherwise false.
	 */
	@Override
	public boolean takeControl() {
		return Button.ESCAPE.isDown() || flags.getLowBatLevel();
	}

	/**
	 * the action method of this class sets the foundReact, 
	 * found, lowBatLevel fields of the Flags object to true
	 * and increments the traversed field of the Flags object thrice.
	 * 
	 *by doing so, the program guarantees the takeControl method of 
	 *all the other behaviours in the arbitrator to return false, simultaneously.
	 *
	 *since in our Main class we have instantiated our arbitrator object with its 
	 *returnWhenInactive field set to true, the arbitrator stops,
	 *hence the whole program ends.
	 */
	@Override
	public void action() {
		
		flags.setFoundReact(true);
		flags.setFound(true);
		flags.setLowBatLevel(true);
		flags.incTraversed();
		flags.incTraversed();
		flags.incTraversed();
	}

	@Override
	public void suppress() {

	}

}
