package rpg.inventory;

import rpg.utility.IDGenerator;
import rpg.utility.PrimeGenerator;
import rpg.value.Weight;

public class Armor extends Item {
	
	private static PrimeGenerator idGenerator = new PrimeGenerator();
	
	public Armor(Weight weight){
		super(weight);
	}
	
	protected IDGenerator getIDGenerator(){
		return idGenerator;
	}
	
	/****************************************
	 * Protection
	 ****************************************/
	
	private final int maximumProtection;
	
	private boolean isValidMaximumProtection(int maximumProtection){
		
	}
}