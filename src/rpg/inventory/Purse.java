package rpg.inventory;

import rpg.utility.FibonacciGenerator;
import rpg.utility.IDGenerator;
import rpg.value.DucatAmount;
import rpg.value.Weight;

public class Purse extends Container {

	public Purse(Weight weight, DucatAmount contents) {
		super(weight, contents);
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

	public DucatAmount getDucatCapacity() {
		return this.ducatCapacity;
	}

	public void setDucatCapacity(DucatAmount capacity) {
		this.ducatCapacity = capacity;
	}

	public void addToContents(DucatAmount ducatAmount) {
		DucatAmount newContent = this.getDucatContent().add(ducatAmount);
		if(newContent.isGreaterThan(ducatCapacity)){
			this.tear();
		} else {
			this.setDucatContent(newContent);
		}
	}

	public Weight getWeightOfContents() {
		return this.getDucatContent().getWeight();
	}

	public boolean isOverCapacity() {
		return this.getDucatContent().isGreaterThan(this.getDucatCapacity());
	}

	/************************************************
	 * Tearing
	 ************************************************/

	private boolean torn = false;

	public boolean isTorn() {
		return this.torn;
	}

	private void tear() {
		this.torn = true;
	}

	/************************************************
	 * Value
	 ************************************************/

	public DucatAmount getValue() {

	}

}
