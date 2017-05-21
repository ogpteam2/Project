package rpg;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import rpg.utility.BinomialIterator;

public class Backpack extends Item {

	private static BinomialIterator idGenerator = new BinomialIterator();
	private final long ID;
	
	public Backpack(){
		this.ID = generateID();
	}
	
	@Override
	protected long generateID() {
		if(!idGenerator.hasNext()){
			idGenerator.reset(); 
		}
		return idGenerator.nextLong();
	}
	
	@Basic
	@Raw
	public long getID(){
		return this.ID;
	}

}
