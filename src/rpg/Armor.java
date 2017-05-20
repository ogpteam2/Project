package rpg;

import rpg.utility.PrimeIterator;

public class Armor extends Item {
	
	private static PrimeIterator idGenerator = new PrimeIterator();
	private final long ID;
	
	public Armor(){
		ID = generateID();
	}
	
	/**
	 * Generates an ID for the piece of armor
	 * @return ID for the armor
	 */
	@Override
	protected long generateID() {
		return idGenerator.nextLong();
	}
	
	/**
	 * Returns the ID of this piece of armor.
	 * @return armor ID
	 */
	public long getID(){
		return this.ID;
	}

}