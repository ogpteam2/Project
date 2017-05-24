package rpg.inventory;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

abstract public class Immobile {
	
	public Immobile(double weight){
		ID = generateID();
		if(isValidWeight(weight)){
			this.weight = weight;
		} else {
			this.weight = 0;
		}
	}
	
	/************************************************
	 * Identifier
	 ************************************************/
	
	protected final long ID;
	
	/**
	 * Generates an ID in accordance with the item type's ID spec.
	 */
	protected abstract long generateID();
	
	/**
	 * Returns the item ID.
	 * @return
	 */
	@Raw
	@Basic
	public long getID(){
		return this.ID;
	}
	
	/************************************************
	 * Value
	 ************************************************/
	
	private int value;
	
	/************************************************
	 * Weight
	 ************************************************/
	
	private final double weight;
	
	@Raw
	@Immutable
	public double getWeight(){
		return this.weight;
	}
	
	public boolean isValidWeight(double weight){
		return weight >= 0;
	}
	
	
}
