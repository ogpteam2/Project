package rpg.inventory;


import be.kuleuven.cs.som.annotate.*;

/**
 * An enumeration introducing different anchor points.
 * 
 * @version 1.0
 * @author Robbe,Elias
 *
 */
@Value
public enum Anchorpoint {

	BODY("BODY"),BACK("BACK"),BELT("BELT"),RIGHT("RIGHT"),LEFT("LEFT");
	
	/**
	 * Initialize the given anchor point wit given anchorpoint.
	 * 
	 * @param anchorpoint
	 * 		  The anchor point for this new anchor point.
	 * @post  The anchor point of this anchor point is set to the given achor point.
	 * 		  | this.anchorpoint.equals(anchorpoint)
	 */
	@Raw
	private Anchorpoint(String anchorpoint){
		this.anchorpoint = anchorpoint;
	}
	
	/**
	 * Returns the anchorpoint.
	 */
	@Raw @Basic @Immutable
	public String getAnchorpoint(){
		return this.anchorpoint;
	}

	
	/**
	 * A variable referencing the type of this anchorpoint.
	 */
	private final String anchorpoint;
	

}
