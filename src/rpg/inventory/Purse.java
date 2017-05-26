package rpg.inventory;

import rpg.utility.FibonacciGenerator;
import rpg.utility.IDGenerator;

public class Purse extends Container {
	
	public Purse(float weight) {
		super(weight);
		// TODO Auto-generated constructor stub
	}

	/************************************************
	 * Identification
	 ************************************************/
	
	private static FibonacciGenerator idGenerator = new FibonacciGenerator();
	
	@Override
	protected IDGenerator getIDGenerator(){
		return idGenerator;
	}

	/************************************************
	 * Contents
	 ************************************************/
		
	private DucatAmount ducatCapacity;
	
	public DucatAmount getDucatCapacity(){
		return this.ducatCapacity;
	}
	
	public void setDucatCapacity(DucatAmount capacity){
		this.ducatCapacity = capacity;
	}
	
	public void addToContents(DucatAmount ducatAmount){
		
	}
	
	public double getWeightOfContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public boolean isOverCapacity(){
		return this.getDucatContent().isGreaterThan(this.getDucatCapacity());
	}
	
	/************************************************
	 * Tearing
	 ************************************************/
	
	private boolean torn = false;
	
	public boolean isTorn(){
		return this.torn;
	}
	
	private void tear(){
		this.torn = true;
	}
	
	/************************************************
	 * Value
	 ************************************************/
	
	public DucatAmount getValue(){
		
	}
	
}
