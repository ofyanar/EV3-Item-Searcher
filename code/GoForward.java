


import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

/**
 * @author  Omer Yanar
 * @author  Kheyan Patel
 * @version 1.0
 * 
 *GoForward is a class that implements the Behavior interface.
 *It's main purpose is to command the pilot object of the EV3 
 *to go forward until the item is found.
 */
public class GoForward implements Behavior {
	
	private MovePilot pilot;
	
	private Flags flags;
	
	/**
	 * a boolean flag used in this class
	 */
	private boolean flag = true;
	
	/**
	 * the constructor takes two arguments, 
	 * 
	 * @param pilot an instantiated MovePilot object
	 * @param flags an instantiated Flags object
	 */
	public GoForward(MovePilot pilot, Flags flags) {
		this.pilot = pilot;
		this.flags = flags;
	}

	/**
	 *takeControl method allows the action method to run if it returns true
	 *
	 * @return true if the found field of the Flags object is false, and
	 * 		   the flag field of the class is true. otherwise false.
	 */
	@Override
	public boolean takeControl() {
		
		return flag && !flags.getFound();
	}

	/**
	 * makes the MovePilot object drive the EV3 to go forward.
	 */
	@Override
	public void action() {
		pilot.forward();
		flag = false;
		
		while(!flag) {
			Thread.yield();
		}

	}

	/**
	 * sets the boolean flag "flag" to false
	 */
	@Override
	public void suppress() {
		flag = true;
		
	}
	

}
