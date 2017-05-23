package rpg.inventory;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

abstract public class Immobile {
	
	public Immobile(){
		ID = generateID();
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
	
}
