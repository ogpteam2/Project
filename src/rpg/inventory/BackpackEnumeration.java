package rpg.inventory;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map.Entry;

public class BackpackEnumeration implements Enumeration<Item> {

	private HashMap<Long, ArrayList<Item>> backpackContents;
	
	public BackpackEnumeration(HashMap<Long, ArrayList<Item>> backpackContents){
		this.backpackContents = backpackContents;
	}
	
	private Iterator<Entry<Long, ArrayList<Item>>> mapIterator = backpackContents.entrySet().iterator();
	private Entry<Long, ArrayList<Item>> currentEntry = null;
	private ListIterator<Item> entryIterator = null;
	
	@Override
	public boolean hasMoreElements() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Item nextElement() {
		if((entryIterator == null || !entryIterator.hasNext()) && mapIterator.hasNext()){
			currentEntry  = mapIterator.next();
			entryIterator = currentEntry.getValue().listIterator();
		}
		return entryIterator.next();
	}

}
