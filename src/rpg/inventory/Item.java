package rpg.inventory;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import rpg.utility.IDGenerator;

abstract public class Item {
	
	public Item(double weight){
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
	
	private final long ID;
	
	/**
	 * Get the static 
	 * @return
	 */
	protected abstract IDGenerator getIDGenerator();
	
	/**
	 * Generates an ID in accordance with the item type's ID spec.
	 */
	private long generateID(){
		return getIDGenerator().generateID();
	}
	
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
	
	private DucatAmount value;
	
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
	
	
	/************************************************
	 * Owner
	 ************************************************/
	
	private Container container = null;
	
	public void setContainer(Container container){
		
	}
	
	public Container getContainer(){
		return this.container;
	}
	
}
