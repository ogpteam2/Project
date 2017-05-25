package rpg.inventory;

import rpg.utility.IDGenerator;
import rpg.utility.WeaponIDGenerator;

public class Weapon extends Item {
	
	private static WeaponIDGenerator idGenerator = new WeaponIDGenerator();
	
	public Weapon(double weight) {
		super(weight);
		// TODO Auto-generated constructor stub
	}

	protected IDGenerator getIDGenerator(){
		return idGenerator;
	}
}
