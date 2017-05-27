package rpg.inventory;

import rpg.utility.IDGenerator;
import rpg.value.DucatAmount;

public class Anchorpoint extends Container {

	public Anchorpoint() {
		super(0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getWeightOfContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canHaveAsContent(Item item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addToContents(DucatAmount ducatAmount) {
		// TODO Auto-generated method stub

	}

	@Override
	protected IDGenerator getIDGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

}
