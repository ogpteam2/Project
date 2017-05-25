package rpg.inventory;

import be.kuleuven.cs.som.annotate.Value;

@Value
public class DucatAmount implements Comparable<DucatAmount> {

	private Container container = null;
	
	public DucatAmount(double amount){
		this.amount = amount;
	}
	
	private final double amount;
	
	@Override
	public int compareTo(DucatAmount o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public DucatAmount add(DucatAmount other){
		
	}

}