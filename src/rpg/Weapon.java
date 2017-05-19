package rpg;

public class Weapon extends Item {
	
	private static long idCounter = 0;
	
	@Override
	protected long generateID() {
		return (idCounter++) * 3;
	}
	
	

}
