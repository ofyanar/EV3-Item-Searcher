
import java.util.HashMap;
import java.util.Map;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

/**
 * @author  Omer Yanar
 * @author  Kheyan Patel
 * @version 1.0
 *
 *SearchItem is a class that implements the Behavior interface.
 *It's main purpose is to use the colour sensor to scan for the
 *colour of the item being searched, and if found store the item's pose.
 */
public class SearchItem implements Behavior {
	private MovePilot pilot;
	private Map<String, Integer> colourMap;
	private String key;
	private Flags flags;
	private PoseProvider poseP;
	
	/**
	 * a field storing the colour sensor
	 */
	private EV3ColorSensor colorSensor = Calibrate.getColorSensor();
	/**
	 * sample provider field, used to collect samples from the colour sensor
	 */
	private SampleProvider spColor = colorSensor.getColorIDMode();
	
	/**
	 * a field used to store the latest sample collected
	 */
	private  float[] color = new float[1];
	/**
	 * a boolean flag used in this class
	 */
	private boolean flag;

	/**
	 * the constructor takes five arguments, 
	 * 
	 * @param key		 the String name of the colour that has been selected to be searched
	 * @param poseP 	 an instantiated OdometryPoseProvider object
	 * @param flags 	 an instantiated Flags object
	 * @param pilot 	 an instantiated MovePilot object
	 * @param colourMap  a hash map containing String names  
	 * 					 of colours mapped to their corresponding RGB values
	 */
	public SearchItem(MovePilot pilot,Map<String, Integer> colourMap, String key,  PoseProvider poseP, Flags flags) {
		this.poseP = poseP;
		this.pilot = pilot;
		this.colourMap = (HashMap<String, Integer>)colourMap;
		this.key = key;
		this.flags = flags;
	}

	/**
	 *takeControl method allows the action method to run if it returns true
	 *uses the sample provider to collect samples from the colour sensor.
	 *
	 * @return true if the sample collected from the colour sensor 
	 * 		   matches the RGB value of the colour being searched
	 * 		   otherwise false.
	 */
	@Override
	public boolean takeControl() {
		spColor.fetchSample(color, 0);
		flag = (color[0]) == ((float)colourMap.get(key));
		return flag && !flags.getFound();
	}

	/**
	 * makes the MovePilot of the robot halt the robot,
	 * uses the odometry pose provider collect the EV3's
	 * current pose, which is the corresponding pose of the
	 * item being searched and sets the value of itemPose
	 * field of the Flags object.
	 * Finally, sets the found field of the Flags object to true.
	 */
	@Override
	public void action() {
		pilot.stop();
		
		flags.setItemPose(poseP.getPose());
		
		flags.setFound(true);
	}

	/**
	 * sets the boolean flag "flag" to false
	 */
	@Override
	public void suppress() {
		flag = false;
	}
	

}
