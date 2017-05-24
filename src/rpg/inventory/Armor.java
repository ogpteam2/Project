package rpg.inventory;

import rpg.utility.PrimeGenerator;

public class Armor extends Immobile {
	
	private static PrimeGenerator idGenerator = new PrimeGenerator();
	
	public Armor(float weight){
		super(weight);
	}
	
	/**
	 * Generates an ID for the piece of armor
	 * @return ID for the armor
	 */
	@Override
	public long generateID() {
		return idGenerator.generateID();
	}

}