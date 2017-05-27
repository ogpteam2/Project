package rpg.inventory;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import rpg.value.DucatAmount;
import rpg.value.Weight;

public abstract class Container extends Item{

	public Container(Weight weight, DucatAmount ducatContent) {
		super(weight);
		addToContents(ducatContent);
	}
	
	public Container(Weight weight){
		super(weight);
	}

	public Weight getTotalWeight(){
		return getWeight().add(getWeightOfContents());
	}
	
	public abstract Weight getWeightOfContents();
	
	public abstract boolean canHaveAsContent(Item item);
	
	private DucatAmount ducatContent = new DucatAmount();
	
	@Raw
	@Basic
	public DucatAmount getDucatContent(){
		return this.ducatContent;
	}
	
	@Raw
	public void setDucatContent(DucatAmount content){
		this.ducatContent = content;
	}

	public abstract void addToContents(DucatAmount ducatAmount);
}
