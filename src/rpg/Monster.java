package rpg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import be.kuleuven.cs.som.annotate.*;
import rpg.exception.InvalidContentException;
import rpg.inventory.Anchorpoint;
import rpg.inventory.Armor;
import rpg.inventory.Item;
import rpg.inventory.Weapon;
import rpg.value.Unit;
import rpg.value.Weight;

/**
 * A class of monsters.
 * 
 * No new invars imposed.
 * 
 * @author Robbe, Elias
 *
 */
public class Monster extends Mobile {

	/**
	 * Initialiazes a new monster with given name, hitpoints, strength, a weapon
	 * as claws and an armor as skin and an map of items.
	 * 
	 * @param name
	 *            The new name.
	 * @param hitpoints
	 *            The new current and maximum Hitpoints.
	 * @param strength
	 *            The new Strength.
	 * @param claws
	 *            The new Claws.
	 * @param skin
	 *            The new skin.
	 * @param items
	 *            The items that the monster may possibly wear.
	 * @post the skin is set to the given skin | this.skin = skin
	 * @post the claws is set to the given claws. | this.claws = claws
	 * @effect A random number of anchorpoints are valid for the monster. | let
	 *         valid = new ArrayList<Anchorpoint>() | for (Anchorpoint
	 *         anchorpoint: Anchorpoint) | valid.add(Anchorpoint(anchorpoint)) |
	 *         let random = ThreadLocalRandom.current().nextInt(0, 6); | let
	 *         ableToSet = valid.subList(0, random+1) |
	 *         this.setValidAnchorpoints(ableToSet)
	 * @effect The items that can be set on a anchorpoint are set. | for
	 *         (EnumMap.Entry<Anchorpoint, Item> entry : items.entrySet()){ | if
	 *         (ableToSet.contains(entry.getKey())) |
	 *         setItemAt(entry.getValue(),entry.getKey())
	 */
	public Monster(String name, long hitpoints, BigDecimal strength, Weapon claws, Armor skin,
			EnumMap<Anchorpoint, Item> items) throws IllegalArgumentException {
		super(name, hitpoints, strength);
		this.skin = skin;
		this.claws = claws;
		List<Anchorpoint> valid = new ArrayList<Anchorpoint>();
		valid.add(Anchorpoint.LEFT);
		valid.add(Anchorpoint.RIGHT);
		valid.add(Anchorpoint.BODY);
		valid.add(Anchorpoint.BELT);
		valid.add(Anchorpoint.BACK);
		int random = ThreadLocalRandom.current().nextInt(0, 6);
		List<Anchorpoint> ableToSet = valid.subList(0, random);
		this.setValidAnchorpoints(ableToSet);
		for (EnumMap.Entry<Anchorpoint, Item> entry : items.entrySet()) {
			if (ableToSet.contains(entry.getKey()))
				setItemAt(entry.getValue(), entry.getKey());
		}
	}

	/************************************************
	 * Name
	 ************************************************/

	/**
	 * Checks whether the given name is a valid one.
	 * 
	 * @return True if the given names satisfies the validNamePattern. | result
	 *         == validNamePattern.matcher(name).matches()
	 */
	@Override
	public boolean isValidName(String heroName) {
		return pattern.matcher(heroName).matches();

	}

	/**
	 * A pattern with the valid name pattern for a monster.
	 */
	private static final Pattern pattern = Pattern.compile("['A-Za-z\\s]+");

	/************************************************
	 * Capacity
	 ************************************************/

	/**
	 * Return the total capacity of the monster.
	 * 
	 * @return The total capacity of the monster. | let strength =
	 *         this.getRawStrength() | let result =
	 *         strength.multiply(BigDecimal.valueOf(9)) | result == new
	 *         Weigth(result,Unit.kg)
	 */
	public Weight getCapacity() {
		BigDecimal strength = this.getRawStrength();
		BigDecimal result = strength.multiply(BigDecimal.valueOf(9));
		return new Weight(result, Unit.kg);
	}

	/************************************************
	 * Protection And Damage
	 ************************************************/

	/**
	 * Returns the total protection
	 */
	@Basic
	@Raw
	public long getTotalProtection() {
		return getSkin().getCurrentProtection();
	}

	/**
	 * Returns the skin.
	 */
	@Basic
	@Raw
	public Armor getSkin() {
		return this.skin;
	}

	/**
	 * Returns the claws.
	 */
	@Basic
	@Raw
	public Weapon getClaws() {
		return this.claws;
	}

	/**
	 * A variable referencing the skin of this monster.
	 */
	private Armor skin;

	/**
	 * A variable referencing the claws of this monster.
	 */
	private Weapon claws;

	/************************************************
	 * anchorpoints
	 ************************************************/

	/**
	 * Checks whether the given item is valid for the given anchorpoint.
	 * 
	 * @param item
	 *            The item to check at the given anchorpoint.
	 * @param anchorpoint
	 *            The anchorpoint to check with the given item.
	 * @return False if the anchorpoint is not effective. | result ==
	 *         (anchorpoint == null)
	 * @return True otherwise.
	 */
	@Override
	public boolean isValidItemAt(Item item, Anchorpoint anchorpoint) {
		if (anchorpoint == null)
			return false;
		return true;
	}

	/************************************************
	 * Hit
	 ************************************************/
	
	/**
	 * Return a pseudorandom number between 0 and 100 including both ends.
	 * If the random number is greater than or equal to currentHitpoints,
	 * return currentHitpoints.
	 * @return A number between 0 and 100.
	 * 		   | for one I in 1..100 
	 * 	       | 	result == I.
	 * @return CurrentHitpoints
	 * 		  | let random = ThreadLocalRandom.current().nextInt(0, 100 + 1);
	 * 		  | 	if (random>=this.getCurrentHitpoints()){
	 *		  |	 		 result == (int)this.getCurrentHitpoints();
	 */		
	@Override
	protected int randomZeroToHundred(){
		int random = ThreadLocalRandom.current().nextInt(0, 100 + 1);
		if (random>=this.getCurrentHitpoints()){
			return (int)this.getCurrentHitpoints();
		}
		return random;
	}
	
	
	/**
	 * Gets the total strength of this a monter.
	 * 
	 * @return The total strength of the monser. | let result =
	 *         this.getRawStrength().intValue() | result ==
	 *         getClaws().getStrength() + result
	 */
	protected int getTotalStrength() {
		int result = this.getRawStrength().intValue();
		return getClaws().getDamage() + result;
	}

	/**
	 * Calculates the damage of a monster.
	 * 
	 * @return The damage. | result.equals((int)(getTotalStrength()-10)/2)
	 */
	@Override
<<<<<<< HEAD
	public int calculateDamage(){
		return (int)(getTotalStrength()-10)/2;
=======
	public int calculateDamage() {
		return (int) (getTotalStrength() - 10) / 2;
>>>>>>> branch 'master' of https://github.com/ogpteam2/Project.git
	}

	/**
	 * Heals the monster for nothing.
	 */
	@Override
	protected void heal() {
	}

	/**
	 * Collects treasures if a monster kills another monster or hero.
	 * 
	 * @param other
	 *            The other mobile.
	 * @effect The monster switches a random number of his items with the dead
	 *         other. | let random = ThreadLocalRandom.current().nextInt(0,
	 *         other.getNbAnchorpoints() + 1) | let otherAnchorpoints =
	 *         other.getAnchorpoints() | let i = 0 | for
	 *         (EnumMap.Entry<Anchorpoint, Item> entry :
	 *         otherAnchorpoints.entrySet()) | if (i>random) | break | else
	 *         changeItem(entry.getValue(),entry.getKey()) | i++
	 */
	@Override
	protected void collectTreasures(Mobile other) throws InvalidContentException {
		int random = ThreadLocalRandom.current().nextInt(0, other.getNbAnchorpoints() + 1);
		EnumMap<Anchorpoint, Item> otherAnchorpoints = other.getAnchorpoints();
		int i = 0;

		for (EnumMap.Entry<Anchorpoint, Item> entry : otherAnchorpoints.entrySet()) {
			if (i >= random) {
				break;
			} else {
				changeItem(entry.getValue(), entry.getKey());
			}
			i++;
		}

	}

	/**
	 * Changes an item with the given item at given anchorpoint.
	 * 
	 * @param item
	 *            The new item
	 * @param anchorpoint
	 *            The anchorpoint to set the new item.
	 * @effect The old item is removed and the new is added. |
	 *         this.removeItemAt(anchorpoint) | this.setItemAt(item,
	 *         anchorpoint)
	 */
	private void changeItem(Item item, Anchorpoint anchorpoint) throws IllegalArgumentException {
		if (this.getValidAnchorpoints().contains(anchorpoint)){
			if (this.getItemAt(anchorpoint)!= null){
				this.removeItemAt(anchorpoint);
			}
			this.setItemAt(item, anchorpoint);
		}

	}

}