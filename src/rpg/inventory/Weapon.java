package rpg.inventory;

import rpg.utility.WeaponIDGenerator;

public class Weapon extends Immobile {
	
	private static WeaponIDGenerator idGenerator = new WeaponIDGenerator();
	
	public Weapon(double weight) {
		super(weight);
		// TODO Auto-generated constructor stub
	}

	@Override
	public long generateID() {
		return idGenerator.generateID();
	}

}
