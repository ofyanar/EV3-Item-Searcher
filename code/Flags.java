

import lejos.robotics.navigation.Pose;

/**
 * @author  Omer Yanar
 * @author  Kheyan Patel
 * @version 1.0
 *
 *Flags is a class created out of necessity caused by the interdependence between our 
 *behaviour classes.it consists of five fields, each with it's getter and setter methods.
 *
 *these fields allow us to control the dependence between behaviours whilst additionally
 *allowing us to control how many times behaviours "takeOver". 
 *
 *these fields are also useful in allowing us to end the arbitrator from running infinitely.
 */
public class Flags {
	
	/**
	 * field allowing us to track how many times the area has been traversed
	 */
	private static int traversed = 0;
	/**
	 * field allowing us to store the pose of the location of the item after it has been found
	 */
	private Pose pose;
	/**
	 * field allowing us to track whether the item has been found
	 */
	private boolean found = false;
	/**
	 * field allowing us to track whether the robot has reacted to finding/not finding the item
	 */
	private boolean foundReaction = false;
	/**
	 * field allowing us to track if the robots battery is at a critical level
	 */
	private boolean lowBatLevel = false;
		
	public Flags() {
		
	}
	
	public  boolean getFound() {return found;}
    public  void setFound(boolean value) {found = value;}
    
    public  boolean getFoundReact() {return foundReaction;}
    public  void setFoundReact(boolean value) {foundReaction = value;}
    
    public  boolean getLowBatLevel() {return lowBatLevel;}
    public  void setLowBatLevel(boolean value) {lowBatLevel = value;}
    
    public  Pose getItemPose() {return pose;}
    public  void setItemPose(Pose value) {pose = value;}
    
    
    public  int getTraversed() {return traversed;}
	/**
	 * the traversed field does not have a setter method. 
	 * instead, this method is used to increment the traversed field by one
	 */
    public  void incTraversed() {traversed++;}
}
