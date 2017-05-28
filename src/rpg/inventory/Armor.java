package rpg.inventory;

import rpg.utility.IDGenerator;
import rpg.utility.PrimeGenerator;
import rpg.value.DucatAmount;
import rpg.value.Weight;

public class Armor extends Item {
	
	private ArmorType type;
	
	/****************************************
	 * Constructors
	 ****************************************/
	
	public Armor(Weight weight, 
			DucatAmount maximumValue, 
			int maximumProtection,
			ArmorType type){
		super(weight);
		
		this.type = type;
		
		assert isValidMaximumProtection(maximumProtection);
		this.maximumProtection = maximumProtection;
		
		this.setValue(maximumValue);
		
		this.type = type;
	}
	
	public Armor(Weight weight, DucatAmount value, int maximumProtection){
		this(weight, value, maximumProtection, ArmorType.STANDARD);
	}
	
	/****************************************
	 * Identification
	 ****************************************/
	
	private static PrimeGenerator idGenerator = new PrimeGenerator();
	
	protected IDGenerator getIDGenerator(){
		return idGenerator;
	}
	
	/****************************************
	 * Protection
	 ****************************************/
	
	private final int maximumProtection;
	
	private boolean isValidMaximumProtection(int maximumProtection){
		return maximumProtection >= 1 
				&& maximumProtection <= this.type.getMaximumProtection();
	}
	
	public int getMaximumProtection(){
		return this.maximumProtection;
	}
	
	private int currentProtection;
	
	public boolean isValidCurrentProtection(int currentProtection){
		return (currentProtection <= this.getMaximumProtection() 
				&& currentProtection >= 0);
	}
	
	public void setCurrentProtection(int currentProtection){
		assert isValidCurrentProtection(currentProtection);
		this.currentProtection = currentProtection;
	}
	
	public int getCurrentProtection(){
		return this.currentProtection;
	}
	
	public double getProcentualProtection(){
		double procent = (double) this.getCurrentProtection()/this.getMaximumProtection();
		return procent;
	}
	
	/****************************************
	 * Value
	 ****************************************/
	
	protected boolean canHaveAsValue(DucatAmount value){
		return !value.isGreaterThan(new DucatAmount(1000));
	}
	
	public DucatAmount getValue(){
		return this.getRawValue().multiply(this.getProcentualProtection());
	}
	
	
}