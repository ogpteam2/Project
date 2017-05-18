package rpg;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

abstract public class Item {
	
	public Item(){
		generateID();
	}
	
	/************************************************
	 * Identifier
	 ************************************************/
	
	private final int ID;
	
	/**
	 * Generates an ID in accordance with the item type's ID spec.
	 */
	protected abstract void generateID();
	
	/**
	 * Returns the item ID.
	 * @return
	 */
	@Raw
	@Basic
	public int getID(){
		return this.ID;
	}
	
	@Raw
	@Basic
	protected void setID(int ID){
		this.ID = ID;
	}
	
	/************************************************
	 * Value
	 ************************************************/
}
