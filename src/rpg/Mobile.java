package rpg;

import java.util.EnumMap;
import java.util.List;
import java.util.regex.*;
import be.kuleuven.cs.som.annotate.*;
import rpg.inventory.Anchorpoint;
import rpg.inventory.Item;
import rpg.inventory.*;
import rpg.utility.PrimeGenerator;
import java.math.*;
import rpg.value.*;


/**
 * An abstract class of entities that can move (mobiles).
 * 
 * @invar Each mobile must have a properly spelled name.  
 * 		  |isValidName(getName())
 * @invar Each mobile must have a valid maximumHitpoints
		  |isValidMaximumHitpoints(getMaximumHitpoints())
 * @invar Each mobile must a valid currentHitpoints
 * 		  | canHaveAsMaximumHitpoints(getCurrentHitpoints())
 * @invar The number of anchorpoints must be valid for each mobile.
 * 		  | canHaveAsNbAnchorpoints(getNbAnchorPoints)
 * @invar Each mobile can have each of its items at its anchorpoints.
 *        | for each I in anchorpoints:
 *        | 	canHaveAsItemAt(getItemAt(I))
 * @author Robbe, Elias
 * @version 1.0
 *
 */
abstract public class Mobile {
	

	
	/************************************************
	 * Constructors
	 ************************************************/
	
	public Mobile(String name) throws IllegalArgumentException{
		setName(name);
	}
	
	
	/************************************************
	 * Name - defensive programming
	 ************************************************/
	
	/**
	 * Return the name of this mobile.
	 */
	@Raw @Basic 
	public String getName() {
		return this.name;
	}
	
	/**
	 * Check whether the name is a valid name.
	 * 
	 * @param name
	 * 		  The name to be checked.
	 * @return True if the given string is a valid name for the mobile.
	 * @throws IllegalArgumentException
	 * 		   This name that was given is not a valid name.
	 * 		   | (name == null || !(getValidNamePattern.matcher(name).matches())  
	 * @note  The valid names will be decided in the each subclass .
	 */
	@Raw
	abstract public boolean isValidName(String name) throws IllegalArgumentException;
	

	/**
	 * A pattern which names of mobiles should be made of.
	 * 
	 * @return A pattern of acceptable characters for the name.
	 * @note   This implementation is worked out in each subclass.
	 */
	@Raw @Immutable 
	public abstract Pattern getValidNamePattern();
	

	/**
	 * Sets the name of the mobile to the given name.
	 * 
	 * @param name
	 * 		  The new name of the mobile.
	 * @Post If the given name is a valid one set the name to the new name of the mobile.
	 * 		 | if(isValidName(name))
	 * 		 | then (new).getName.equals(name)
	 * @throws IllegalArgumentException
	 * 		   The given name is not valid.
	 * 		   | !(isValidName(name))
	 * 		   
	 */
	@Raw 
	public void setName(String name) throws IllegalArgumentException{
		if(isValidName(name)){
			this.name = name;
		}
		else{
			throw new IllegalArgumentException("Invalid name");
		}
	}
	
	/**
	 * Variable for storing the name of the mobile.
	 */
	private String name = null;
	
	
	/************************************************
	 * Hitpoints - nominal programming
	 ************************************************/
	
	
	/**
	 * Gives the current hitpoints.
	 */
	@Raw @Basic
	public long getCurrentHitpoints(){
		return this.currentHitpoints;
	}
	
	/**
	 * Gives the maximum hitpoints.
	 */
	@Raw @Basic
	public long getMaximumHitpoints(){
		return this.maximumHitpoints;
	}
	
	/**
	 * Gives the prime generator.
	 */
	@Raw @Basic
	public PrimeGenerator getPrimeGenerator(){
		return primeGen;
	}
	
	/**
	 * Check wheter a given hitpoints can be set as the currentHitpoints.
	 * 
	 * @param hitpoints
	 * 		  The hitpoints to check.
	 * @return True if given hitpoints are equal or less than the 
	 * 		   maximumHitpoints and greater than or equal to 0.
	 * 		   | result == (hitpoints<=this.getMaximumHitpoints() && hitpoints>0)
	 */
	public boolean canHaveAsCurrentHitpoints(long hitpoints){
		return (hitpoints<=this.getMaximumHitpoints() && hitpoints>=0);
	}
	

	/**
	 * Checks whether the given hitpoints is valid.
	 * 
	 * @param hitpoints
	 * 		  The hitpoints to check.
	 * @return true if the given hitpoints is greater than 1 and prime.
	 * 		   | result == (hitpoints>1 && primeGen.isPrime(hitpoints))
	 */
	@Raw
	public static boolean isValidMaximumHitpoints(long hitpoints){
		return ((hitpoints > 1) && (primeGen.isPrime(hitpoints)));
	}
	
	
	/**
	 * Sets the currentHitpoints to the given hitpoints
	 * 
	 * @param hitpoints
	 * 		  The new currentHitpoints of the mobile.
	 * @pre  The given hitpoints must be legal.
	 * 		 | canHaveAsCurrentHitpoints(hitpoints)
	 * @post The currentHipoints is set to the given hitpoints.
	 * 		 | new.getCurrentHitpoints == hitpoints
	 */
	@Raw
	public void setCurrentHitpoints(long hitpoints){
		this.currentHitpoints = hitpoints;
	}
		
	/**
	 * Sets the maximum hitpoints to the given hitpoints.
	 * 
	 * 
	 * @param hitpoints
	 * 		  The new maximumHitpoints of the mobile.
	 * @pre  the given hitpoints must be legal.
	 * 		 | isValidMaximumHitpoints(hitpoints)  	
	 * @post The maximumHitpoints is set to the given hitpoints.
	 * 		 | new.getMaximumHitpoints == hitpoints	  
	 */
	@Raw
	protected void setMaximumHitpoints(long hitpoints){
		this.maximumHitpoints = hitpoints;
	}
	
	
	/**
	 * A variable that stores the current hitpoints of the mobile.
	 */
	private long currentHitpoints;
	/**
	 * A variable that stores the maximum hitpoints of the mobile.
	 */
	private long maximumHitpoints;
	/**
	 * A variable that stores a prime generator.
	 */
	protected static PrimeGenerator primeGen = new PrimeGenerator();
	
	/************************************************
	 * Strength - total programming
	 ************************************************/
	
	/**
	 * Returns the rawStrength of the mobile.
	 */
	@Raw @Basic
	public BigDecimal getRawStrength(){
		return this.rawStrength;
	}
	
	/**
	 * Multiplies the rawStrenth with an int.
	 * 
	 * @param amount
	 * 		  The multiplier of rawStrength.
	 * @post The new rawStrenth is the product of amount and getRawStrength.
	 * 		 | new.rawStrength.equals(getRawStrength*amount)
	 * @post  If there is not enough memory to store the new rawStrength
	 * 		  nothing happens.
	 */
	public void multiplyRawStrength(int amount){
		try{
			BigDecimal multiplier = 
			BigDecimal.valueOf(amount).setScale(rawStrengthPrecision, RoundingMode.HALF_UP);
			setRawStrength(getRawStrength().multiply(multiplier));
		}
		catch (ArithmeticException ex){}

	}
	
	/**
	 * Sets the RawStrength to the given amount.
	 * 
	 * @param amount
	 * 		  The new rawStrength.
	 * @post sets the rawStrength to the given amount with two decimal places.
	 * 	     | new.getRawStrenth() == 
	 * 		 | amount.setScale(strenthPrecision, RoundingMode.HALF_UP)
	 */
	protected void setRawStrength(BigDecimal amount){
		this.rawStrength = amount.setScale(rawStrengthPrecision, RoundingMode.HALF_UP);
		setCapacity();
	}
	

	/**
	 * A variable that stores the rawStrength of a mobile.
	 */
	private BigDecimal rawStrength = new BigDecimal("0").setScale(rawStrengthPrecision, RoundingMode.HALF_UP);

	/**
	 * A variable that stores the precision of the strength.
	 */
	private static final int rawStrengthPrecision = 2;
	
	
	/************************************************
	 * Capacity
	 ************************************************/
	
	/**
	 * Return the capacity of this mobile.
	 */
	@Raw @Basic
	public Weight GetCapacity(){
		return this.capacity;
	}
	
	/**
	 * Sets the capacity of a mobile based on the raw strength.
	 * 
	 * @post The new capacity is set to a weight based on the raw strength.
	 * @note The precise definition of this method is worked out in each subclass.
	 */
	protected abstract void setCapacity();
	
	
	/**
	 * A variable referencing the capacity of this mobile.
	 */
	private Weight capacity = new Weight(BigDecimal.ZERO);
	
	
	/************************************************
	 * Protection
	 ************************************************/
	
	
	/************************************************
	 * Anchors
	 ************************************************/
	
	/**
	 * Return the number of anchor points ascribed to this mobile.
	 */
	@Basic @Raw
	public int getNbAnchorpoints(){
		if (anchorpoints != null)
			return anchorpoints.size();
		return 0;
	}
	
	/**
	 * Return the number of valid anchor points ascribed to this mobile.
	 */
	@Basic @Raw
	public int getNbValidAnchorpoints(){
		if (validAnchorpoints != null)
			return validAnchorpoints.size();
		return 0;
	}
	
	/**
	 * Checks whether the given number of anchor points is valid.
	 * 
	 * @param nbAnchorpoints
	 * 		  The number to check.
	 * @return True iff the given number lays between 0 and 5 or is 0 or 5.
	 * 		   | result == ((0<=nbAnchorpoints) && (nbAnchorpoints<=5))
	 */
	public static boolean isValidNbAnchorpoints(int nbAnchorpoints){
		return ((0<=nbAnchorpoints) && (nbAnchorpoints<=5));
	}
	
	
	/**
	 * Checks whether this mobile can have the given number as a number of
	 * 	      anchor points.
	 * @param number
	 * 		  The number to check.
	 * @return True iff the number is a valid and is less than or equal to the number
	 * 		   of valid anchor points.
	 * 		   | result ==
	 * 		   | 	(isValidNbAnchorpoints(number) && number<=getNbValidAnchorpoints())
	 */
	@Raw 
	public boolean canHaveAsNbAnchorpoints(int number){
		if (isValidNbAnchorpoints(number) && number<=getNbValidAnchorpoints())
			return true;
		return false;
	}
	
	
	/**
	 * Returns the item at a given anchorpoint
	 * 
	 * @param anchorpoint
	 * 		  The item of this anchorpoint is given.
	 * @return The item at the anchorpoint if the anchorpoint is not null.
	 * 		   | if (anchorpoint != null)
	 * 		   | then	result == anchorpoints.get(anchorpoint)
	 * @return Null if the anchorpoint is null.
	 * 		   | if (anchorpoint == null)
	 * 		   |	then result == null
	 */
	@Basic @Raw
	public Item getItemAt(Anchorpoint anchorpoint){
		if (anchorpoint != null)
			return anchorpoints.get(anchorpoint);
		return null;
	}
	
	/**
	 * Checks whether the given item is valid for the given anchorpoint.
	 * 
	 * @param item
	 * 		  The item to check at the given anchorpoint.
	 * @param anchorpoint
	 * 		  The anchorpoint to check with the given item.
	 * @return False if the anchorpoint is not effective.
	 *		   | result == (anchorpoint == null)
	 * @return False if the item is a purse but it is not equipped at the belt.
	 * 		   | result == (item instanceof Purse) && (anchorpoint.getAnchorpoint()!="BELT")
	 * @return True otherwise.
	 */   
	public static boolean isValidItemAt(Item item,Anchorpoint anchorpoint){
		if (anchorpoint == null)
			return false;
		if ( (item instanceof Purse) && (anchorpoint.getAnchorpoint()!="BELT"))
			return false;
		return true;
	}
	

	/**
	 * Checks whether the given anchor point is valid for this mobile.
	 * 
	 * @param anchorpoint
	 * 		  The anchor point to check.
	 * @return True iff the valid anchor points contains the given anchor point.
	 * 	       | result == alidAnchorpoints.contains(anchorpoint)
	 */
	public boolean canHaveAsAnchorpoint(Anchorpoint anchorpoint){
		return validAnchorpoints.contains(anchorpoint);
	}
	
	/**
	 * Checks wheter all items in the anchor points have the same unit.
	 * 
	 * @param unit
	 * 		  The unit to check.
	 * @return True iff all items have the same type.
	 * 		   | for (Item w: anchorpoints.values())
	 *         | 	if (w != null && w.getWeight().getUnit() != unit)
	 *         |		result == false
	 *         | result == true
	 */  
	public boolean alItemsInUnit(Unit unit){
		for (Item w: anchorpoints.values()){
			if (w != null && w.getWeight().getUnit() != unit){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns the total capacity of the anchor points.
	 * 
	 * @pre The items in the anchor points should have the unit kg.
	 *      | alItemsInUnit(Unit.kg)
	 * @return The total capacity of the anchorpoints if they all have the same unit.
	 * 		   | if (alItemsInUnit(Unit.kg)
	 * 	       | then 
	 * 		   | let total = 0
	 *         | for (Item w: anchorpoints.values())
	 *         | 	total = total.add(w.getWeight().getNumeral())
	 *         | result.equals(total)
	 * @throws IllegalArgumentException
	 * 	       The items have different units.
	 * 		   |  (!alItemsInUnit(Unit.kg)
	 */ 
	public Weight getCapacityOfAnchorpoints()
		throws IllegalArgumentException
	{
		if (alItemsInUnit(Unit.kg)){
			Weight total = new Weight(BigDecimal.ZERO,Unit.kg);
			for (Item w: anchorpoints.values()){
				if (w != null){
					total = total.add(w.getWeight());
				}
			}
			return total;
		}
		else{
			throw new IllegalArgumentException("Put the items in kg.");
		}
			
	}

	/**
	 * Checks whether the item can be set at the given anchor point.
	 * @pre Everything should be in kg.
	 * 		| getCapacityOfAnchorpoints().getUnit() == Unit.kg &&
	 * 		| 	item.getWeigh().getUnit() == Unit.kg
	 * @param item
	 *        The item to check.
	 * @param anchorpoint
	 *        The anchor point to check where the item will be.
	 * @return True if the current weight of the anchor points plus the 
	 * 		   weight of the item is less than this capacity.
	 * 		  | if (canHaveAsAnchorpoint(anchorpoint) && isValidItemAt(item,anchorpoint))
	 * 		  | then let currentCapacityOfAnchorpoints =  getCapacityOfAnchorpoints()
	 *        | 	if ((currentCapacityOfAnchorpoints.add(item.getWeight()))
	 *		  |			.compareTo(GetCapacity()) == -1)
	 *		  | 	then	result == true
	 */
	public boolean canHaveAsItemAt(Item item, Anchorpoint anchorpoint){
		if (canHaveAsAnchorpoint(anchorpoint) && isValidItemAt(item,anchorpoint)){
			Weight currentCapacityOfAnchorpoints =  getCapacityOfAnchorpoints();
			if ((currentCapacityOfAnchorpoints.add(item.getWeight()))
					.compareTo(GetCapacity()) == -1){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @pre The anchor point should be empty.
	 * 	    | null == getItemAt(anchorpoint)
	 * @pre The anchor point should be valid.
	 *      | canHaveAsAnchorpoint(anchorpoint)
	 * @pre The anchor point should be able to set the current item.
	 *      | canHaveAsItemAt(item,anchorpoint)
	 * @param item
	 * 		  The item to set at the given anchor point.
	 * @param anchorpoint
	 * 		  The anchor point to set te given item.
	 * @post The item is set at the given anchor point.
	 * 	     | anchorpoints.put(anchorpoint, item);
	 * 
	 */
	public void setItemAt(Item item,Anchorpoint anchorpoint){
		assert (getItemAt(anchorpoint) == null);
		assert canHaveAsAnchorpoint(anchorpoint);
		assert isValidItemAt(item,anchorpoint);
		anchorpoints.put(anchorpoint, item);
	}
	
	/**
	 * Removes the item at the given anchor point.
	 * 
	 * @param anchorpoint
	 * 		  The anchorpoint to remove.
	 * @post The holder of the item at the anchorpoint is removed
	 * 		 | item.removeHolder()
	 * @post The item at the anchorpoint is removed.
	 *       | anchorpoints.remove(anchorpoint)
	 */
	public void removeItemAt(Anchorpoint anchorpoint){
		Item item = anchorpoints.get(anchorpoint);
		item.removeHolder();
		anchorpoints.remove(anchorpoint);
	}

	/**
	 * A variable referencing the anchor point with the corresponding Item.
	 */
	private EnumMap<Anchorpoint,Item> anchorpoints = new EnumMap<>(Anchorpoint.class);
	
	/**
	 * A variable referencing the valid anchor points of this mobile,
	 * if the anchor point is in the list the anchor point is valid.
	 */
	private List<Anchorpoint> validAnchorpoints;
	
	/************************************************
	 * Hit
	 ************************************************/
	
	
	
	
	
	
	
	
	
	
	
	

}
