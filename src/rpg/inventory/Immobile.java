package rpg.inventory;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import rpg.utility.IDGenerator;

abstract public class Immobile {
	
	private static IDGenerator idGenerator = new IDGenerator(){
		public boolean hasNextID(){
			return true;
		}
		public long nextID(){
			return 1;
		}
		public void reset(){
			
		}
	};
	
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
	
	private final long ID;
	
	/**
	 * Generates an ID in accordance with the item type's ID spec.
	 */
	private long generateID(){
		return this.idGenerator.nextID();
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
