package rpg.inventory;

public abstract class Container extends Immobile{

	public Container(double weight) {
		super(weight);
		// TODO Auto-generated constructor stub
	}

	public double getTotalWeight(){
		return getWeight() + getWeightOfContents();
	}
	
	public abstract double getWeightOfContents();

}
