


import java.util.HashMap;
import java.util.Map;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.robotics.Color;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

/**
 * @author  Omer Yanar
 * @author  Kheyan Patel
 * @version 1.0
 *
 *Main class is where our main method for the program is.
 *
 *in it, we have our UI menu for the program which we used the LCD to implement.
 *additionally we instantiate the MovePilot, OdometryPoseProvider, and the Flags
 *object we use in our behaviours throughout the program as well as the behaviours
 *of the program and the arbitrator for the behaviours.
 */
public class Main {
	/**
	 * Magic number for the axle length of the EV3
	 */
	final private static float AXLE_LENGTH = 115;
	/**
	 * Magic number for the wheel diameter of the EV3
	 */
	final private static float WHEEL_DIAMETER = 56;
	
 

	/**
	 * pilotBuilder method is used to instantiate and return a MovePilot object
	 * outside the main method so we don't clutter the main method with unnecessary code.
	 * 
	 * @param left		the motorport for the left wheel of the EV3
	 * @param right		the motorport for the right wheel of the EV3
	 * @param diam		the diameter of the wheel of EV3
	 * @param offset	the offset of the EV3
	 * @return			the instantiated MovePilot object
	 */
    private static MovePilot pilotBuilder(Port left, Port right, float diam, float offset) {
        BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(left);
        Wheel wL = WheeledChassis.modelWheel(mL, diam).offset(-1 * offset);
       
        BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(right);
        Wheel wR = WheeledChassis.modelWheel(mR, diam).offset(offset);
        
        Wheel[] wheels = new Wheel[] {wR, wL};
        Chassis chassis = new WheeledChassis(wheels, WheeledChassis.TYPE_DIFFERENTIAL);
        
        return new MovePilot(chassis);
        }
    
    

    /**
     * Firstly, we have a hash map of the String name of the colours mapped to the
     * colour's corresponding RGB value.  the String names allows the user to accurately select
     * the colour of the item that is being searched.
     * 
     * once the program starts and the version, author, and the robot name is displayed, the user can
     * scroll through the colour name list by using the UP and Down buttons on the EV3 and finally select 
     * value they wish by pressing ENTER. the delay is used in order to avoid skipping colours in the list,
     * as the while loop may iterate more than once when the condition of the conditional statement is being met.
     * 
     * once that is done, the MovePilot object and the OdometryPoseProvider objects are instantiated and
     * the MovePilot object's linear and angular speeds are set.
     * 
     * then each of the Behaviour objects are instantiated by passing them their appropriate parameters
     * and the Arbitrator object is created with a list of the Behaviour objects passed as parameter
     * and the returnWhenInactive set to true. 
     * 
     * by returnWhenInactive we are allowing the arbitrator to stop from running when the takeControl
     * method of every Behaviour returns false simultaneously.
     * 
     * @param args	command line arguments
     */
    public static void main(String[] args) {
    	
    	Map<String, Integer> colour = new HashMap<>();//key-->list
		
		colour.put("Blue", Color.BLUE);
		colour.put("Red", Color.RED);
		colour.put("Green", Color.GREEN);
		colour.put("Yellow", Color.YELLOW);
		colour.put("Orange", Color.ORANGE);
		
		int counter = 0;//
		String[] keyArray = {"Blue","Red", "Green", "Yellow", "Orange"};//
		
		LCD.drawString("  Welcome", 2, 1);
		LCD.drawString("  to the", 2, 2);
		LCD.drawString(" Jigglebean", 2, 3);
		LCD.drawString(" Robot System", 2, 4);
		LCD.drawString(" Version 1.0", 2, 5);
		Delay.msDelay(3000);
		LCD.clear();
		
		LCD.drawString("Made by", 2, 1);
		LCD.drawString(" Kheyan Patel", 2, 2);
		LCD.drawString(" Riona John", 2, 3);
		LCD.drawString(" Farah Azizi", 2, 4);
		LCD.drawString(" Omer Yanar", 2, 5);
		Delay.msDelay(3000);
		LCD.clear();
		
		LCD.drawString("To Select the", 1, 1);
		LCD.drawString("Colour of", 1, 2);
		LCD.drawString("the Item", 1, 3);
		LCD.drawString("You want ", 1, 4);
		LCD.drawString("Searched ", 1, 5);
		LCD.drawString("Press Enter ", 1, 6);
		Delay.msDelay(3000);
		LCD.clear();
		
		LCD.drawString("To Scroll", 1, 1);
		LCD.drawString("Through", 1, 2);
		LCD.drawString("the Color", 1, 3);
		LCD.drawString("List ", 1, 4);
		LCD.drawString("Press UP ", 1, 5);
		LCD.drawString("and Down ", 1, 6);
		Delay.msDelay(3000);
		LCD.clear();
		
		LCD.drawString("Please Select", 1,1);
		LCD.drawString("Colour:", 1,2);
		LCD.drawString(keyArray[counter], 1, 3);//
		
		while(Button.ENTER.isUp()) {
			
			if(Button.DOWN.isDown()) {
				Delay.msDelay(1000);
				LCD.clear();
				counter++;
				int temp = counter;
				counter = (counter >= colour.size()) ? (colour.size()-1) : temp;
				LCD.drawString("Please Select", 1,1);
				LCD.drawString("Colour:", 1,2);
				LCD.drawString(keyArray[counter], 1, 3);//
				
				
			}
			else if(Button.UP.isDown()) {
				Delay.msDelay(1000);
				LCD.clear();
				counter--;
				int temp = counter;
				counter = (counter < 0) ? 0 : temp;
				LCD.drawString("Please Select", 1,1);
				LCD.drawString("Colour:", 1,2);
				LCD.drawString(keyArray[counter], 1, 3);//
			}
		}
		LCD.clear();
    	
    	
    	MovePilot pilot = Main.pilotBuilder(MotorPort.A, MotorPort.B, WHEEL_DIAMETER, AXLE_LENGTH/2);
        
        PoseProvider poseProvider= new OdometryPoseProvider(pilot);
        
        pilot.setLinearSpeed(250);
        pilot.setAngularSpeed(100);
        
        Flags flags = new Flags();
        
        Behavior searchItem = new SearchItem(pilot, colour,keyArray[counter], poseProvider, flags);
        Behavior goForward= new GoForward(pilot, flags);
        Behavior turnNinety = new TurnNinety(pilot, flags);
        Behavior batteryLevel = new BatteryLevel(pilot, flags);
        Behavior emergencyStop = new EmergencyStop(pilot, flags);
        Behavior foundReaction = new FoundReaction(flags);
        
        Behavior[] behaviorArr = { goForward,turnNinety, searchItem, 
        		foundReaction,batteryLevel,emergencyStop};
        
        Arbitrator arbitrator = new Arbitrator(behaviorArr, true);
        
        arbitrator.go();
       }
    
}