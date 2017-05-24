package rpg.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import rpg.utility.BinomialGenerator;

public class Backpack extends Container {

	private static BinomialGenerator idGenerator = new BinomialGenerator();

	/************************************************
	 * Constructors
	 ************************************************/
	
	public Backpack(double weight, double capacity){
		super(weight);
		this.capacity = capacity;
	}
	
	@Override
	public long generateID() {
		return idGenerator.generateID();
	}

	@Override
	public double getWeightOfContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/************************************************
	 * Contents
	 ************************************************/
	
	private HashMap<Long, ArrayList<Immobile>> contents;
	
	public boolean canHaveAsContent(Immobile item){
		
	}
	
	public void add
	
	/************************************************
	 * Capacity
	 ************************************************/
	
	private final double capacity;
	
	public double getCapacity(){
		return this.capacity;
	}
	
}
