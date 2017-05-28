package rpg.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import rpg.exception.InvalidContentException;
import rpg.utility.BinomialGenerator;
import rpg.utility.IDGenerator;
import rpg.value.Weight;

public class Backpack extends Container {

	private static BinomialGenerator idGenerator = new BinomialGenerator();

	/************************************************
	 * Constructors
	 ************************************************/

	public Backpack(Weight weight, Weight capacity) {
		super(weight);
		this.capacity = capacity;
	}

	@Override
	public Weight getWeightOfContents() {
		
	}

	/************************************************
	 * Identification
	 ************************************************/

	protected IDGenerator getIDGenerator() {
		return idGenerator;
	}

	/************************************************
	 * Contents
	 ************************************************/

	private HashMap<Long, ArrayList<Item>> contents;

	public boolean canHaveAsContent(Item item) {
		return false;
	}

	public void addToContents(Item item) throws InvalidContentException {
		if (canHaveAsContent(item)) {
			insertIntoContents(item);
		} else {
			throw new InvalidContentException();
		}
	}

	private void insertIntoContents(Item item) {
		ArrayList<Item> sameIDItems = contents.get(item.getID());
		if (sameIDItems == null)
			sameIDItems = new ArrayList<Item>();
		sameIDItems.add(item);
		contents.put(item.getID(), sameIDItems);
	}

	/************************************************
	 * Capacity
	 ************************************************/

	private final Weight capacity;

	public Weight getCapacity() {
		return this.capacity;
	}
	
	
	

}
