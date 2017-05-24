package rpg.inventory;

import rpg.utility.BinomialGenerator;

public class Backpack extends Container {

	private static BinomialGenerator idGenerator = new BinomialGenerator();
	
	public Backpack(double weight){
		super(weight);
	}
	
	@Override
	protected long generateID() {
		if(!idGenerator.hasNextID()){
			idGenerator.reset(); 
		}
		return idGenerator.nextID();
	}

	@Override
	public double getWeightOfContents() {
		// TODO Auto-generated method stub
		return 0;
	}

}
