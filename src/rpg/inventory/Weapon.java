package rpg.inventory;

import rpg.utility.IDGenerator;
import rpg.utility.WeaponIDGenerator;
import rpg.value.DucatAmount;
import rpg.value.Weight;

public class Weapon extends Item {

	private static WeaponIDGenerator idGenerator = new WeaponIDGenerator();

	private final static int MAX_DAMAGE = 100;
	private final static DucatAmount VALUE_PER_DAMAGE = new DucatAmount(2);

	public Weapon(Weight weight, int damage) {
		super(weight);
		setDamage(damage);
	}

	protected IDGenerator getIDGenerator() {
		return idGenerator;
	}

	private int damage = 0;

	public int getDamage() {
		return this.damage;
	}

	public void setDamage(int damage) {
		assert canHaveAsDamage(damage);
		this.damage = damage;
	}

	public boolean canHaveAsDamage(int damage) {
		boolean divisibleBySeven = (damage % 7 == 0);
		boolean withinRange = (damage > 0 && damage <= MAX_DAMAGE);
		return divisibleBySeven && withinRange;
	}

	@Override
	protected boolean canHaveAsValue(DucatAmount value) {
		boolean lowerRange = value.compareTo(new DucatAmount(1)) == 1;
		boolean upperRange = value.compareTo(new DucatAmount(200)) != 1;
		return lowerRange && upperRange;
	}

	@Override
	public DucatAmount getValue() {
		return VALUE_PER_DAMAGE.multiply(getDamage());
	}
}
