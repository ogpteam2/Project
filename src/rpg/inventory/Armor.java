package rpg.inventory;

import rpg.utility.IDGenerator;
import rpg.utility.PrimeGenerator;
import rpg.value.DucatAmount;
import rpg.value.Weight;

/**
 * A class representing the Armor item. Subclass of Item.
 * 
 * @invar The maximum protection must be in line with the armor type
 * 		  |type.isValidMaximumProtection(maximumProtection)
 * @invar The current protection amount must be within boundaries
 * 		  |isValidCurrentProtection(currentProtection)
 * @invar The maximum value must be lower or equal to 1000
 * 		  |canHaveAsValue(maximumValue)
 * @author Elias, Robbe
 * @version 1.0
 *
 */
public class Armor extends Item {
	
	private ArmorType type;
	
	/****************************************
	 * Constructors
	 ****************************************/
	
	/**
	 * Creates a new piece of armor with the given attributes.
	 * @param weight
	 * 		  The weight of this piece of armor
	 * @param maximumValue
	 * 		  The maximum value this weapon can have 
	 * @param maximumProtection
	 * 		  The maximum protection this armor can provide
	 * @param type
	 * 		  The type this armor is. Currently only standard is available.
	 */
	public Armor(Weight weight, 
			DucatAmount maximumValue, 
			int maximumProtection,
			ArmorType type){
		super(weight);
		
		this.type = type;
		
		assert isValidMaximumProtection(maximumProtection);
		this.maximumProtection = maximumProtection;
		setCurrentProtection(maximumProtection);
		
		this.setValue(maximumValue);
		
		this.type = type;
	}
	
	/**
	 * Creates a new piece of armor of the standard type, with the given attributes.
	 * @param weight
	 * 		  The weight of this piece of armor
	 * @param maximumValue
	 * 		  The maximum value this weapon can have 
	 * @param maximumProtection
	 * 		  The maximum protection this armor can provide
	 */
	public Armor(Weight weight, DucatAmount value, int maximumProtection){
		this(weight, value, maximumProtection, ArmorType.STANDARD);
	}
	
	/****************************************
	 * Identification
	 ****************************************/
	
	/**
	 * Instance of IDGenerator for generating id's for the instances of
	 * this class.
	 */
	private static PrimeGenerator idGenerator = new PrimeGenerator();
	
	/**
	 * Returns the idgenerator this class is using.
	 * @return The idgenerator of this class
	 */
	protected IDGenerator getIDGenerator(){
		return idGenerator;
	}
	
	/****************************************
	 * Protection
	 ****************************************/
	
	/**
	 * The maximum protection value this piece of armor can have.
	 */
	private final int maximumProtection;
	
	/**
	 * Returns whether the given maximum protection value falls within the
	 * specification for this armor type.
	 * @param maximumProtection
	 * 		  The maximum protection value to be checked.
	 * @return Whether the value is valid.
	 * 		  |maximumProtection >= 1 && maximumProtection <= type.maximumProtection
	 */
	private boolean isValidMaximumProtection(int maximumProtection){
		return maximumProtection >= 1 
				&& maximumProtection <= this.type.getMaximumProtection();
	}
	
	/**
	 * Returns the maximum protection value.
	 * @return The maximum protection value.
	 */
	public int getMaximumProtection(){
		return this.maximumProtection;
	}
	
	/**
	 * The current protection value.
	 */
	private int currentProtection;
	
	/**
	 * Checks if the given value is a valid current protection value.
	 * @param currentProtection
	 * 		  The value to be checked
	 * @return Whether the given value falls between 0 and the maximum value.
	 * 		  |currentProtection >= 0 && currentProtection <= maximumProtection
	 */
	public boolean isValidCurrentProtection(int currentProtection){
		return (currentProtection <= this.getMaximumProtection() 
				&& currentProtection >= 0);
	}
	
	/**
	 * Sets the current protection value
	 * @param currentProtection
	 * 		  The value to set current protection to.
	 * @pre The current protection value must be valid
	 * 		|isValidCurrentProtection(currentProtection)
	 * @post The attribute current protection is set to the given value
	 * 		|getCurrentProtection()==currentProtection
	 */
	public void setCurrentProtection(int currentProtection){
		assert isValidCurrentProtection(currentProtection);
		this.currentProtection = currentProtection;
	}
	
	/**
	 * Returns the current protection value
	 * @return the current protection value
	 */
	public int getCurrentProtection(){
		return this.currentProtection;
	}
	
	/**
	 * Returns what percentage the current protection is of the maximum protection.
	 * @return the current protection divided by the maximum protection
	 */
	public double getProcentualProtection(){
		double procent = (double) this.getCurrentProtection()/this.getMaximumProtection();
		return procent;
	}
	
	/****************************************
	 * Value
	 ****************************************/
	
	protected boolean canHaveAsValue(DucatAmount value){
		return !value.isGreaterThan(new DucatAmount(1000));
	}
	
	public DucatAmount getValue(){
		return this.getRawValue().multiply(this.getProcentualProtection());
	}
	
	
}