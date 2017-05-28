package rpg.inventory;

public enum ArmorType {

	STANDARD(100);
	
	private final int maxProtection;
	
	private ArmorType(int maxProtectionRange){
		this.maxProtection = maxProtectionRange;
	}
	
	public int getMaximumProtection(){
		return this.maxProtection;
	}
	
}
