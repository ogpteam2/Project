package rpg.inventory;

import rpg.utility.FibonacciGenerator;

public class Purse extends Container {
	
	private static FibonacciGenerator idGenerator = new FibonacciGenerator();
	
	public Purse(float weight) {
		super(weight);
		// TODO Auto-generated constructor stub
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
