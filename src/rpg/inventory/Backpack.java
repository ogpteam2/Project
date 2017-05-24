package rpg.inventory;

import rpg.utility.BinomialGenerator;

public class Backpack extends Container {

	private static BinomialGenerator idGenerator = new BinomialGenerator();
	
	public Backpack(double weight){
		super(weight);
	}
	
	@Override
	public long generateID() {
		return idGenerator.generateID();
	}

	@Override
	public double getWeightOfContents() {
		// TODO Auto-generated method stub
		return 0;
	}

}
