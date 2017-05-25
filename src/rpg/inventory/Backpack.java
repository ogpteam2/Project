package rpg.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import rpg.exception.InvalidContentException;
import rpg.utility.BinomialGenerator;
import rpg.utility.IDGenerator;

public class Backpack extends Container {

	private static BinomialGenerator idGenerator = new BinomialGenerator();

	/************************************************
	 * Constructors
	 ************************************************/

	public Backpack(double weight, double capacity) {
		super(weight);
		this.capacity = capacity;
	}

	@Override
	public double getWeightOfContents() {
		// TODO Auto-generated method stub
		return 0;
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

	private final double capacity;

	public double getCapacity() {
		return this.capacity;
	}

}
