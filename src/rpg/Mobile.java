package rpg;

import java.util.regex.*;
import be.kuleuven.cs.som.annotate.*;
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
 * 
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
	 * Variable referencing an array assembling the anchors ascribed to this mobile.
	 */
	private Object anchors[];
	
}
