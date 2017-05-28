package rpg.inventory;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map.Entry;

public class BackpackEnumeration implements Enumeration<Item> {

	private HashMap<Long, ArrayList<Item>> backpackContents;
	
	private Iterator<Entry<Long, ArrayList<Item>>> mapIterator;
	private Entry<Long, ArrayList<Item>> currentEntry;
	private ListIterator<Item> entryIterator;
	
	/**
	 * Creates an iterator that goes over all the elements in a backpack
	 * @param backpackContents the hashmap with all the elements of the backpack in it.
	 */
	public BackpackEnumeration(HashMap<Long, ArrayList<Item>> backpackContents){
		this.backpackContents = backpackContents;
		mapIterator = backpackContents.entrySet().iterator();
		currentEntry = null;
		entryIterator = null;
	}
	
	/**
	 * Checks if the backpack has more elements to iterate over
	 */
	@Override
	public boolean hasMoreElements() {
		boolean entryHasNext = false;
		try{
			entryHasNext = entryIterator.hasNext();
		} catch (NullPointerException e){}
		return mapIterator.hasNext() || entryHasNext;
	}

	/**
	 * Gets the next item in the backpack
	 * @return the next item in the backpack
	 */
	@Override
	public Item nextElement() {
		boolean entryNext;
		try{
			entryNext = entryIterator.hasNext();
		} catch (NullPointerException e) {
			entryNext = false;
		}
		if(entryNext && mapIterator.hasNext()){
			currentEntry  = mapIterator.next();
			entryIterator = currentEntry.getValue().listIterator();
		}
		return entryIterator.next();
	}

}
