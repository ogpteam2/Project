package rpg.inventory;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import rpg.utility.FibonacciGenerator;
import rpg.utility.IDGenerator;
import rpg.value.DucatAmount;
import rpg.value.Weight;

/**
 * Class for a container that can contain a certain amount of ducats. 
 * @author Robbe, Elias
 *
 */

public class Purse extends Container {

	/**
	 * Creates a new purse 
	 * @param weight
	 * @param contents
	 * @param ducatCapacity
	 * 		The maximum amount of ducats this purse can contain.
	 */
	
	public Purse(Weight weight, DucatAmount contents, DucatAmount ducatCapacity) {
		super(weight, contents);
		setDucatCapacity(ducatCapacity);
	}
	
	public Purse(Weight weight){
		super(weight);
	}

	/************************************************
	 * Identification
	 ************************************************/

	private static FibonacciGenerator idGenerator = new FibonacciGenerator();

	@Override
	protected IDGenerator getIDGenerator() {
		return idGenerator;
	}

	/************************************************
	 * Contents
	 ************************************************/

	private DucatAmount ducatCapacity;

	/**
	 * @return the maximum amount of ducats this purse can contain.
	 */
	@Basic
	@Raw
	public DucatAmount getDucatCapacity() {
		return this.ducatCapacity;
	}

	/**
	 * Set the amount of ducats this purse can contain.
	 * @param capacity the max capacity
	 */
	public void setDucatCapacity(DucatAmount capacity) {
		this.ducatCapacity = capacity;
	}
	
	/**
	 * Add these ducats to the contents of this purse.
	 */
	public void addToContents(DucatAmount ducatAmount) {
		DucatAmount newContent = this.getDucatContent().add(ducatAmount);
		if(newContent.isGreaterThan(ducatCapacity)){
			this.tear();
		} else {
			this.setDucatContent(newContent);
		}
	}
	
	/**
	 * @return the weight of the ducats in this purse.
	 */
	public Weight getWeightOfContents() {
		return this.getDucatContent().getWeight();
	}

	/**
	 * @return whether the amount of ducats in this purse exceeds its capacity.
	 */
	public boolean isOverCapacity() {
		return this.getDucatContent().isGreaterThan(this.getDucatCapacity());
	}

	/************************************************
	 * Tearing
	 ************************************************/

	private boolean torn = false;

	/**
	 * Whether the purse has been over capacity.
	 * @return purse is torn when over capacity.
	 */
	
	public boolean isTorn() {
		return this.torn;
	}

	
	/**
	 * Tear this purse and drop contents in parent container.
	 */
	private void tear() {
		this.torn = true;
		this.getContainer().addToContents(getDucatContent());
		this.getContainer().removeFromContents(this);
	}

	/************************************************
	 * Value
	 ************************************************/

	/**
	 * @return the value of the ducats in this purse
	 */
	public DucatAmount getValue() {
		return this.getDucatContent();
	}
	
	/**
	 * A purse cannot contain items.
	 */
	@Override
	public boolean canHaveAsContent(Item item) {
		return false;
	}
	
	@Override
	protected boolean canHaveAsValue(DucatAmount value) {
		return false;
	}

}
