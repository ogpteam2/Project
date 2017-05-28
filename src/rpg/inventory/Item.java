package rpg.inventory;

import java.math.BigDecimal;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import rpg.Mobile;
import rpg.utility.IDGenerator;
import rpg.value.DucatAmount;
import rpg.value.Weight;

abstract public class Item {
	
	public Item(Weight weight, DucatAmount value){
		ID = generateID();
		if(isValidWeight(weight)){
			this.weight = weight;
		} else {
			this.weight = new Weight(BigDecimal.ZERO);
		}
		setValue(value);
	}
	
	public Item(Weight weight){
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
	
	@Raw
	protected DucatAmount getRawValue(){
		return this.value;
	}
	
	protected void setValue(DucatAmount value){
		assert canHaveAsValue(value);
		this.value = value;
	}
	
	protected abstract boolean canHaveAsValue(DucatAmount value);
	
	public abstract DucatAmount getValue();
	
	/************************************************
	 * Weight
	 ************************************************/
	
	private final Weight weight;
	
	@Raw
	@Immutable
	public Weight getWeight(){
		return this.weight;
	}
	
	public boolean isValidWeight(Weight weight){
		return weight != null;
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
	
	public Mobile holder = null;
	
	protected void setHolder(Mobile holder){
		
	}
	
}
