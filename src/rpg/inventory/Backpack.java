package rpg.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import rpg.exception.InvalidContentException;
import rpg.exception.ItemTransferException;
import rpg.utility.BinomialGenerator;
import rpg.utility.IDGenerator;
import rpg.value.DucatAmount;
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

	/************************************************
	 * Value
	 ************************************************/
	
	@Override
	protected boolean canHaveAsValue(DucatAmount value) {
		boolean lowerRange = value.compareTo(new DucatAmount(0)) == 1;
		boolean upperRange = value.compareTo(new DucatAmount(500)) != 1;
		return lowerRange && upperRange;
	}

	@Override
	public DucatAmount getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Weight getWeightOfContents() {
		BackpackEnumeration it = this.getIterator();
		Weight total = new Weight();
		while(it.hasMoreElements()){
			total.add(it.nextElement().getWeight());
		}
		return total;
	}

	/************************************************
	 * Identification
	 ************************************************/

	protected IDGenerator getIDGenerator() {
		return idGenerator;
	}
	
	/************************************************
	 * Iteration
	 ************************************************/
	
	public BackpackEnumeration getIterator(){
		BackpackEnumeration it = new BackpackEnumeration(this.getContents());
		return it;
	}

	/************************************************
	 * Contents
	 ************************************************/

	private HashMap<Long, ArrayList<Item>> contents;

	private HashMap<Long, ArrayList<Item>> getContents(){
		return this.contents;
	}
	
	public boolean canHaveAsContent(Item item) {
		if(isOverCapacity(this.getWeight().add(item.getWeight()))){
			return false;
		} else {
			return true;
		}
	}

	public void addToContents(Item item) throws InvalidContentException {
		if (canHaveAsContent(item)) {
			insertIntoContents(item);
			item.setContainer(this);
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
	
	public void removeFromContents(Item item){
		ArrayList<Item> sameIDItems = contents.get(item.getID());
		sameIDItems.remove(item);
		if(sameIDItems.isEmpty())
			contents.remove(item.getID());
		item.setContainer(null);
		item.setHolder(null);
	}
	
	public boolean hasAsContent(Item item){
		ArrayList<Item> sameIDItems = contents.get(item.getID());
		if(sameIDItems == null) return false;
		else {
			return sameIDItems.indexOf(item) != -1;
		}
	}
	
	/************************************************
	 * Item transfer
	 ************************************************/
	
	/**
	 * 
	 * @param item
	 * @param backpack
	 * @throws ItemTransferException
	 */
	
	public void transferItemTo(Item item, Backpack other) throws ItemTransferException,InvalidContentException{
		if(item==null){
			throw new InvalidContentException();
		} else if(!hasAsContent(item)){
			throw new ItemTransferException("Item is not in this container!");
		} else if(!other.canHaveAsContent(item)){
			throw new ItemTransferException("Target cannot accept given item!");
		} else {
			this.removeFromContents(item);
			other.addToContents(item);
		}
	}

	/************************************************
	 * Capacity
	 ************************************************/

	private final Weight capacity;

	public Weight getCapacity() {
		return this.capacity;
	}
	
	private boolean isOverCapacity(Weight weight){
		return this.getCapacity().compareTo(weight)==-1;
	}

	@Override
	public void addToContents(DucatAmount ducatAmount) {
		// TODO Auto-generated method stub
		
	}

}
