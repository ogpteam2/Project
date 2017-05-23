package rpg.inventory;

public class Weapon extends Immobile {
	
	private static long idCounter = 0;
	
	@Override
	protected long generateID() {
		return (idCounter++) * 3;
	}
	
	

}
