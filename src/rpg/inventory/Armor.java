package rpg.inventory;

import rpg.utility.IDGenerator;
import rpg.utility.PrimeGenerator;

public class Armor extends Item {
	
	private static PrimeGenerator idGenerator = new PrimeGenerator();
	
	public Armor(float weight){
		super(weight);
	}
	
	protected IDGenerator getIDGenerator(){
		return idGenerator;
	}
}