package rpg;

import rpg.utility.BinomialGenerator;

public class Backpack extends Item {

	private static BinomialGenerator idGenerator = new BinomialGenerator();
	
	public Backpack(){
		
	}
	
	@Override
	protected long generateID() {
		if(!idGenerator.hasNextID()){
			idGenerator.reset(); 
		}
		return idGenerator.nextID();
	}

}
