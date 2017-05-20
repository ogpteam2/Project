package rpg;

import rpg.utility.PrimeIterator;

public class Armor extends Item {
	
	private static PrimeIterator idGenerator = new PrimeIterator();
	private final long ID;
	
	public Armor(){
		ID = generateID();
	}
	
	@Override
	protected long generateID() {
		return idGenerator.nextLong();
	}

}
