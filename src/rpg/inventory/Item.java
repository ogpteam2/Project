package rpg.inventory;

import java.math.BigDecimal;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import rpg.utility.IDGenerator;
import rpg.value.DucatAmount;

abstract public class Item {
	
	public Item(double weight, DucatAmount value){
		ID = generateID();
		if(isValidWeight(weight)){
			this.weight = weight;
		} else {
			this.weight = 0;
		}
		
	}
	
	public Item(double weight){
		this(weight, new DucatAmount(BigDecimal.ZERO));
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
	
	public void setValue(DucatAmount value){
		this.value = value;
	}
	
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
