package rpg.inventory;

public abstract class Container extends Item{

	public Container(double weight) {
		super(weight);
		// TODO Auto-generated constructor stub
	}

	public double getTotalWeight(){
		return getWeight() + getWeightOfContents();
	}
	
	public abstract double getWeightOfContents();
	
	public abstract boolean canHaveAsContent(Item item);
	
	private DucatAmount ducatContent;

	public abstract void addToContents(DucatAmount ducatAmount);
}
