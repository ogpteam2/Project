package rpg.inventory;

import rpg.utility.IDGenerator;
import rpg.utility.WeaponIDGenerator;

public class Weapon extends Item {
	
	private static WeaponIDGenerator idGenerator = new WeaponIDGenerator();
	
	public Weapon(double weight, int damage) {
		super(weight);
		// TODO Auto-generated constructor stub
	}

	protected IDGenerator getIDGenerator(){
		return idGenerator;
	}
	
	private int damage = 0;
	
	public int getDamage(){
		return this.damage;
	}
	
	public void setDamage(int damage){
		assert canHaveAsDamage(damage);
		this.damage = damage;
	}
	
	public boolean canHaveAsDamage(int damage){
		boolean divisibleBySeven = (damage%7==0);
		boolean withinRange = (damage > 0 && damage < 101);
		return divisibleBySeven && withinRange;
	}
}
