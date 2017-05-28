package rpg.inventory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import rpg.Mobile;
import rpg.exception.InvalidContentException;
import rpg.exception.ItemTransferException;
import rpg.utility.BinomialGenerator;
import rpg.utility.IDGenerator;
import rpg.value.DucatAmount;
import rpg.value.Weight;

public class Backpack extends Container {

	
	/************************************************
	 * Constructors
	 ************************************************/

	/**
	 * Creates a new backpack with the given parameters
	 * @param weight
	 * 			The weight of the backpack itself.
	 * @param value
	 * 			The value of the backpack itself.
	 * @param capacity
	 * 			The maximum value of the weight of the contents
	 */
	public Backpack(Weight weight, DucatAmount value, Weight capacity) {
		super(weight,value);
		this.capacity = capacity;
		this.contents = new HashMap<Long, ArrayList<Item>>();
	}

	/************************************************
	 * Value
	 ************************************************/
	
	/**
	 * Checks if the backpack can have this value.
	 */
	@Override
	protected boolean canHaveAsValue(DucatAmount value) {
		boolean lowerRange = value.compareTo(new DucatAmount(0)) == 1;
		boolean upperRange = value.compareTo(new DucatAmount(500)) != 1;
		return lowerRange && upperRange;
	}
	
	/**
	 * Returns the value of the backpack itself
	 */
	@Override
	public DucatAmount getValue() {
		return this.getRawValue();
	}
	
	/**
	 * Returns the value of the backpack plus its contents
	 * @return value of backpack and contents
	 */
	public DucatAmount getTotalValue(){
		return this.getValue().add(this.getValueOfContents());
	}
	
	/**
	 * Calculates the value of the contents of the backpack.
	 * @return the value of all the contents in the backpack
	 */
	public DucatAmount getValueOfContents(){
		BackpackEnumeration it = this.getIterator();
		DucatAmount value = new DucatAmount(BigDecimal.ZERO);
		while(it.hasMoreElements()){
			value = value.add(it.nextElement().getValue());
		}
		value = value.add(getDucatContent());
		return value;
	}
	
	/************************************************
	 * Weight
	 ************************************************/
	
	/**
	 * Calculates the weight of the items in the backpack. 
	 * @return
	 */
	public Weight getWeightOfItems(){
		BackpackEnumeration it = this.getIterator();
		Weight total = new Weight();
		while(it.hasMoreElements()){
			total = total.add(it.nextElement().getWeight());
		}
		return total;
	}
	
	/**
	 * Returns the weight of all the contents in the backpack.
	 */
	@Override
	public Weight getWeightOfContents() {
		Weight total = new Weight();
		total = total.add(getWeightOfItems());
		total = total.add(this.getDucatContent().getWeight());
		return total;
	}

	/************************************************
	 * Identification
	 ************************************************/
	
	/**
	 * The generator of id's for this class. Generates sequential binomial coefficients.
	 */
	private static BinomialGenerator idGenerator = new BinomialGenerator();
	
	/**
	 * Returns the idgenerator for this class
	 */
	protected IDGenerator getIDGenerator() {
		return idGenerator;
	}
	
	/************************************************
	 * Iteration
	 ************************************************/
	
	/**
	 * Creates an instance of BackpackEnumeration for the contents of this backpack.
	 * @return BackpackEnumeration for this backpack.
	 */
	public BackpackEnumeration getIterator(){
		BackpackEnumeration it = new BackpackEnumeration(this.getContents());
		return it;
	}

	/************************************************
	 * Contents
	 ************************************************/

	/**
	 * Hashmap with id as key, arraylist of items as value.
	 * 
	 * Using a hashmap for storing items with the same id makes locating these items 
	 * happen in constant time.
	 * Locating an item in the array of same id items happens in linear time.
	 */
	private HashMap<Long, ArrayList<Item>> contents;

	/**
	 * Returns the item storage construction for this backpack.
	 * @return contents
	 */
	private HashMap<Long, ArrayList<Item>> getContents(){
		return this.contents;
	}
	
	/
	public boolean canHaveAsContent(Item item) {
		if(isOverCapacity(this.getWeightOfContents().add(item.getWeight()))){
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
	 * Holder
	 ************************************************/
	
	@Override
	public void setHolder(Mobile holder){
		this.holder = holder;
		BackpackEnumeration it = this.getIterator();
		while(it.hasMoreElements()){
			it.nextElement().setHolder(holder);
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
		if(canHaveAsDucatContent(ducatAmount)){
			addToContents(ducatAmount);
		}
	}
	
	public boolean canHaveAsDucatContent(DucatAmount content){
		Weight newWeight = new Weight();
		newWeight = newWeight.add(getWeightOfItems());
		newWeight = newWeight.add(content.getWeight());
		return !isOverCapacity(newWeight);
	}

}
