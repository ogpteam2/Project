package rpg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import rpg.exception.InvalidContentException;
import rpg.inventory.Anchorpoint;
import rpg.inventory.Armor;
import rpg.inventory.Backpack;
import rpg.inventory.Item;
import rpg.inventory.Purse;
import rpg.inventory.Weapon;
import rpg.value.Unit;
import rpg.value.Weight;

public class Monster extends Mobile {

	public Monster(String name,long hitpoints, BigDecimal strength,
			Weapon claws, Armor skin,EnumMap<Anchorpoint,Item> items) 
			throws IllegalArgumentException 
	{
		super(name,hitpoints,strength);
		this.skin = skin;
		this.claws = claws;
		List<Anchorpoint> valid = new ArrayList<Anchorpoint>();
		valid.add(Anchorpoint.BACK);
		valid.add(Anchorpoint.BELT);
		valid.add(Anchorpoint.BODY);
		valid.add(Anchorpoint.LEFT);
		valid.add(Anchorpoint.RIGHT);
		int random = ThreadLocalRandom.current().nextInt(0, 6);
		List<Anchorpoint> ableToSet = valid.subList(0, random+1);
		this.setValidAnchorpoints(ableToSet);
		for (EnumMap.Entry<Anchorpoint, Item> entry : items.entrySet()){
			if (ableToSet.contains(entry.getKey()))
				setItemAt(entry.getValue(),entry.getKey());
		}
		
	}
	
	
	/************************************************
	 * Name
	 ************************************************/
	
	/**
	 * Checks whether the given name is a valid one.
	 * 
	 * @return True if the given names satisfies the validNamePattern.
	 * 		   | result == validNamePattern.matcher(name).matches()
	 */
	@Override
	public boolean isValidName(String heroName){
		return pattern.matcher(heroName).matches();
		
	}
	
	
	private final Pattern pattern = Pattern.compile("['A-Za-z\\s]+");
	
	/************************************************
	 * Capacity
	 ************************************************/
	
	public Weight getCapacity(){
		BigDecimal strength = this.getRawStrength();
		BigDecimal result = strength.multiply(BigDecimal.valueOf(9));
		return new Weight(result,Unit.kg);
	}
	
	/************************************************
	 * Protection And Damage
	 ************************************************/
	
	
	public long getTotalProtection(){
		return getSkin().getCurrentProtection();
	}
	
	
	public Armor getSkin(){
		return this.skin;
	}
	
	public Claws getClaws(){
		return this.claws;
	}

	private Armor skin;
	
	private Weapon claws;
	
	/************************************************
	 * anchorpoints
	 ************************************************/
	
	/**
	 * Checks whether the given item is valid for the given anchorpoint.
	 * 
	 * @param item
	 * 		  The item to check at the given anchorpoint.
	 * @param anchorpoint
	 * 		  The anchorpoint to check with the given item.
	 * @return False if the anchorpoint is not effective.
	 *		   | result == (anchorpoint == null)
	 * @return True otherwise.
	 */   
	public boolean isValidItemAt(Item item,Anchorpoint anchorpoint){
		if (anchorpoint == null)
			return false;
		return true;
	}
	
	/************************************************
	 * Hit
	 ************************************************/
	
	protected int getTotalStrength(){
		int result = this.getRawStrength().intValue();
		return getClaws().getStrength() + result;
	}
	

	@Override
	protected int calculateDamage(){
		return (int)(getTotalStrength()-10)/2;
	}
	
	@Override
	protected void heal(){}
	
	@Override
	protected void collectTreasures(Mobile other)
			throws InvalidContentException
	{
		int random = ThreadLocalRandom.current().nextInt(0, other.getNbAnchorpoints() + 1);
		EnumMap<Anchorpoint,Item> otherAnchorpoints = other.getAnchorpoints();
		int i = 0;
		
		for (EnumMap.Entry<Anchorpoint, Item> entry : otherAnchorpoints.entrySet()){
			if (i>random){
				break;
			}
			else {
				changeItem(entry.getValue(),entry.getKey());
			}
			i++;
		}

		
	}
	
	private void changeItem(Item item, Anchorpoint anchorpoint)
		throws IllegalArgumentException
	{
		this.removeItemAt(anchorpoint);
		this.setItemAt(item, anchorpoint);
	}
	
	
}










