package rpg.inventory;

public class Weapon extends Immobile {
	
	public Weapon(double weight) {
		super(weight);
		// TODO Auto-generated constructor stub
	}

	private static long idCounter = 0;
	
	@Override
	protected long generateID() {
		return (idCounter++) * 3;
	}
	
	

}
