package rpg.inventory;

import rpg.utility.BinomialGenerator;

public class Backpack extends Container {

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
