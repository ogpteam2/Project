package rpg;

import rpg.utility.PrimeGenerator;

public class Armor extends Item {
	
	private static PrimeGenerator idGenerator = new PrimeGenerator();
	
	public Armor(){
		
	}
	
	/**
	 * Generates an ID for the piece of armor
	 * @return ID for the armor
	 */
	@Override
	protected long generateID() {
		return idGenerator.nextID();
	}

}