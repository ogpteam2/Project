package rpg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.*;
import be.kuleuven.cs.som.annotate.*;
import rpg.exception.InvalidContentException;
import rpg.inventory.*;
import rpg.value.DucatAmount;
import rpg.value.Unit;
import rpg.value.Weight;
import java.math.*;
/**
 * A class of heroes.
 * 
 * No new invars imposed.
 * 
 * @author Robbe, Elias
 *
 */
public class Hero extends Mobile {

	/************************************************
	 * Constructors
	 ************************************************/
	/**
	 * Initialize a new hero with given name, hitpoints, strength and items.
	 * 
	 * @param name
	 * 	     The name of the hero.
	 * @param hitpoints
	 * 		  The maximum and current hitpoints of the hero.
	 * @param strength
	 * 		  The strength of the hero.
	 * @param items
	 * 		  The items to initialize the hero.
	 * @effect  The anchorpoints of the hero are set with the given items.
	 * 	      | for item in items
	 * 		  | 	setItemAt(item,anchorpoint)
	 * @throws IllegalArgumentException
	 * 		   The hero is not initialized with armor
	 * 		   | !(items.get(Anchorpoint.BODY) instanceof Armor)
	 */
	public Hero(String name,long hitpoints, BigDecimal strength,
			EnumMap<Anchorpoint,Item> items) 
			throws IllegalArgumentException 
	{
		super(name,hitpoints,strength);
		assert isValidEnumMap(items);
		List<Anchorpoint> validAnchorpoints = new ArrayList<Anchorpoint>();
		validAnchorpoints.add(Anchorpoint.BACK);
		validAnchorpoints.add(Anchorpoint.BELT);
		validAnchorpoints.add(Anchorpoint.BODY);
		validAnchorpoints.add(Anchorpoint.LEFT);
		validAnchorpoints.add(Anchorpoint.RIGHT);
		setValidAnchorpoints(validAnchorpoints);
		initializeAvailableCapacities();
		for (EnumMap.Entry<Anchorpoint, Item> entry : items.entrySet()){
			setItemAt(entry.getValue(),entry.getKey());
		}
		if (!(items.get(Anchorpoint.BODY) instanceof Armor)){
			throw new IllegalArgumentException("A hero must be initialized with armor.");
		}
		
	}

	/**
	 * Initialized a hero with a name, two hitpoints, zero strength 
	 * and a standard armorpiece.
	 * 
	 * @param name
	 * 		  The name of the hero.
	 * @effect The new hero is initialized with standard armor.
	 * 		   | this(name,0L,BigDecimal.ZERO,initializeStandardArmor())
	 */
	public Hero(String name){
		this(name,2,BigDecimal.ZERO,initializeStandardArmor());
	}
	
	/**
	 * Creates a EnumMap to initialize a standardArmor. 
	 * The standard armor has zero weight, zero protection, zero value and is standard.
	 * 
	 * @return	An EnumMap with an armor inside.
	 * 	        | result == Enumap(Anchorpoint.BODY, standardArmor)
	 */
	@Model
	private static EnumMap<Anchorpoint,Item> initializeStandardArmor(){
		EnumMap<Anchorpoint,Item> map = new EnumMap<Anchorpoint,Item>(Anchorpoint.class);
		Armor standardArmor = new Armor(Weight.kg_0,
				new DucatAmount(BigDecimal.ZERO),1,ArmorType.STANDARD);
		map.put(Anchorpoint.BODY, standardArmor);
		return map;
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
		boolean nameIsValid = firstPattern.matcher(heroName).matches();
		int numberOfApos = getNumberOfMatches(patternApo,heroName);
		boolean colonNotSpace = patternColonSpace.matcher(heroName).matches();
		return  nameIsValid && numberOfApos<=2 && !colonNotSpace;
	}
	
	/**
	 * Gets the number of matches with a given pattern.
	 * 
	 * @param pattern
	 * 		  The pattern to check the string against.
	 * @param string
	 * 		  The string to check against the pattern.
	 * @return The number of matches.
	 * 	       | let count = 0
	 * 		   | 	matcher = pattern.matcher(string)
	 * 	       | 	while (matcher.find())
	 *	       |		count++
	 *		   | result.equals(count) 
	 *
	 */
	private int getNumberOfMatches(Pattern pattern, String string){
		int count = 0;
		Matcher matcher = pattern.matcher(string);
		while (matcher.find())
		    count++;
		return count;
	}
	
	/**
	 * The first pattern to check the name against.
	 */
	private static final Pattern firstPattern = Pattern.compile("[A-Z][:'A-Za-z\\s]+");
	/**
	 * The second pattern to check the name against.
	 */
	private static final Pattern patternApo = Pattern.compile("'");
	/**
	 * The third pattern to check the name against.
	 */
	private static final Pattern patternColonSpace = Pattern.compile(":^\\s");
	
	
	/************************************************
	 * Capacity
	 ************************************************/
	
	/**
	 * Return the capacity of this hero in kg.
	 * 
	 * @return Zero if strength is zero or less.
	 * 		   | if (this.getRawStrength().compareTo(BigDecimal.ZERO) == -1 || 
	 *		   | this.getRawStrength().compareTo(BigDecimal.ZERO) == 0)
	 *		   | 	result == 0kg
	 * @return Strength multiplied with 10 if strength between 1 and 10.
	 * 		   | if (this.getRawStrength().compareTo(BigDecimal.ONE) == 1 &&
	 *		   | 	this.getRawStrength().compareTo(BigDecimal.TEN) == -1)
	 *		   | 	result == this.getRawStrength().multiply(BigDecimal.TEN)
	 * @return getCapacityByStrengthBetweenTenAndTwenty if between 10 and 20
	 * 		   | result == getCapacityByStrengthBetweenTenAndTwenty()
	 * @return getCapacityByStrengthAboveTwenty(getRawStrength())
	 * 		   | getCapacityByStrengthAboveTwenty(getRawStrength())
	 */
	public Weight getCapacity(){
		BigDecimal calculatedCapacity;
		BigDecimal rawStrength = this.getRawStrength();
		if (rawStrength.compareTo(BigDecimal.ZERO) == -1 || 
				rawStrength.compareTo(BigDecimal.ZERO) == 0)
			calculatedCapacity = BigDecimal.ZERO;
		else if(rawStrength.compareTo(BigDecimal.ONE) == 1 &&
				rawStrength.compareTo(BigDecimal.TEN) == -1)
			calculatedCapacity = rawStrength.multiply(BigDecimal.TEN);
		else if(rawStrength.compareTo(BigDecimal.TEN) == 1 &&
				rawStrength.compareTo(BigDecimal.valueOf(20)) == -1)
			calculatedCapacity = getCapacityByStrengthBetweenTenAndTwenty(getRawStrength());
		else{
			calculatedCapacity =getCapacityByStrengthAboveTwenty(rawStrength);
		}
		return new Weight(calculatedCapacity,Unit.kg);
	}
	
	/**
	 * A variable storing the availableCapacities if strength is between 10 and 20.
	 */
	private Map<BigDecimal, BigDecimal> availableCapacities;
	
	/**
	 * Initializes the capacities for values of strength between 10 and 20.
	 * 
	 * @post Puts the values with corresponding weights in availableCapacities.
	 * 		| for I in 1...10
	 * 	    | 	 availableCapacities.put(BigDecimal.valueOf(value), 
	 * 		|		BigDecimal.valueOf(corresponding));
	 * 
	 */
	@Model
	private void initializeAvailableCapacities(){
		availableCapacities = new HashMap<BigDecimal, BigDecimal>();
		availableCapacities.put(BigDecimal.valueOf(11), BigDecimal.valueOf(115));
		availableCapacities.put(BigDecimal.valueOf(12), BigDecimal.valueOf(130));
		availableCapacities.put(BigDecimal.valueOf(13), BigDecimal.valueOf(150));
		availableCapacities.put(BigDecimal.valueOf(14), BigDecimal.valueOf(175));
		availableCapacities.put(BigDecimal.valueOf(15), BigDecimal.valueOf(200));
		availableCapacities.put(BigDecimal.valueOf(16), BigDecimal.valueOf(230));
		availableCapacities.put(BigDecimal.valueOf(17), BigDecimal.valueOf(260));
		availableCapacities.put(BigDecimal.valueOf(18), BigDecimal.valueOf(300));
		availableCapacities.put(BigDecimal.valueOf(19), BigDecimal.valueOf(350));
		availableCapacities.put(BigDecimal.valueOf(20), BigDecimal.valueOf(400));
	}
	
	/**
	 * Gets the capacities if strength is between 10 than 20.
	 * 
	 * @param strength
	 * 	     The strength.
	 * @return The capactity with the corresponding value of availableCapacities.
	 * 		   | let capacity = BigDecimal.ZERO
	 * 		   | 			for (Map.Entry<BigDecimal, BigDecimal> entry : availableCapacities.entrySet()){
	 *		   |				if (strength.compareTo(entry.getKey()) == -1)
	 *		   |					capacity = entry.getValue();
	 *		   | result == capacity.
	 */
	private BigDecimal getCapacityByStrengthBetweenTenAndTwenty(BigDecimal strength){
		BigDecimal capacity = BigDecimal.ZERO;
		for (Map.Entry<BigDecimal, BigDecimal> entry : availableCapacities.entrySet()){
			if (strength.compareTo(entry.getKey()) == -1)
				capacity = entry.getValue();
		}
		return capacity;
	}
	
	/**
	 * Gets the capacities if strength is higher than 20.
	 * 
	 * @param strength
	 * 		  The strength to get the capacity from.
	 * @return the capacity of ten units less multiplied with four.
	 * 		  | let pow = strength.min(BigDecimal.TEN).divideToIntegralValue(BigDecimal.TEN)
	 * 		  | let strengthModTen = strength.remainder(BigDecimal.TEN).add(BigDecimal.TEN)
	 * 		  | let baseResult = getCapacityByStrengthBetweenTenAndTwenty(strengthModTen)
	 * 		  | let result = powerOfBigDecimal(BigDecimal.valueOf(4),pow)
	 * 		  | result == result.multiply(baseResult)
	 */
	private BigDecimal getCapacityByStrengthAboveTwenty(BigDecimal strength){
		BigDecimal pow = strength.min(BigDecimal.TEN).divideToIntegralValue(BigDecimal.TEN);
		BigDecimal strengthModTen = strength.remainder(BigDecimal.TEN).add(BigDecimal.TEN); 
		BigDecimal baseResult = getCapacityByStrengthBetweenTenAndTwenty(strengthModTen);
		BigDecimal result = powerOfBigDecimal(BigDecimal.valueOf(4),pow);
		return result.multiply(baseResult);
	}
	
	/**
	 * Return the power of a BigDecimal by iterating the multiplication.
	 * 
	 * @param bigdecimal
	 * 	      The bigdecimal you want the power of.
	 * @param power
	 * 		  The power.
	 * @return The bigdecimal^pow
	 * 	      | let result = BigDecimal.ONE;
	 * 		  | let i = BigDecimal.ZERO; 
	 * 		  |	while (power.compareTo(i) == 1)
	 *		  | 	result = result.multiply(bigdecimal);
	 *		  | 	i.add(BigDecimal.ONE);
	 * 		  | result == result
	 */
	private BigDecimal powerOfBigDecimal(BigDecimal bigdecimal,BigDecimal power){
		BigDecimal result = BigDecimal.ONE;
		BigDecimal i = BigDecimal.ZERO; 
		while (power.compareTo(i) == 1){
			result = result.multiply(bigdecimal);
			i.add(BigDecimal.ONE);
		}
		return result;
	}
	
	/************************************************
	 * Protection
	 ************************************************/
	
	/**
	 * Return the total protection of the hero.
	 * 
	 * @return The total protection.
	 * 	       | let result = getRawProtection()
	 * 		   | if (getItemAt(Anchorpoint.BODY) != null)
	 *		   |	result = result + ((Armor)getItemAt(Anchorpoint.BELT)).getCurrentProtection()
	 * 		   | result == result
	 */
	public long getTotalProtection(){
		int result = getRawProtection();
		if (getItemAt(Anchorpoint.BODY) != null){
			result = result + ((Armor)getItemAt(Anchorpoint.BODY)).getCurrentProtection();
		}
		return result;
	}

	/************************************************
	 * Hit
	 ************************************************/
	
	/**
	 * Return the total strength.
	 * @return The total strength.
	 * 	       | let result = this.getRawStrength()
	 *         | if (getItemAt(Anchorpoint.LEFT) instanceof Weapon)
	 *		   |	result += ((Weapon)getItemAt(Anchorpoint.LEFT)).getStrength();
	 *		   | if (getItemAt(Anchorpoint.RIGHT) instanceof Weapon)
	 *		   |	result += ((Weapon)getItemAt(Anchorpoint.RIGHT)).getStrength();
	 * 		   | result == result
	 */	   
	protected int getTotalStrength(){
		int result = this.getRawStrength().intValue();
		if (getItemAt(Anchorpoint.LEFT) instanceof Weapon)
			result += ((Weapon)getItemAt(Anchorpoint.LEFT)).getDamage();
		if (getItemAt(Anchorpoint.RIGHT) instanceof Weapon)
			result += ((Weapon)getItemAt(Anchorpoint.RIGHT)).getDamage();
		return result;
	}
	
	/**
	 * Calculates the total damage of the hero.
	 * 
	 * @return The total damage.
	 * 		   | result.equals((getTotalStrength()-10)/2)
	 */
	@Override
	public int calculateDamage(){
		int damage = (int)(getTotalStrength()-10)/2;
<<<<<<< HEAD
		if (damage>=0)
			return damage;
=======
		if (damage>=0){
			return damage;
		}
>>>>>>> branch 'master' of https://github.com/ogpteam2/Project.git
		return 0;
	}
	
	/**
	 * Heals the hero for a given amount.
	 * 
	 * @effect Heals the hero if heal amount is not greater than current Hitpoints.
	 * 	      | let random = randomZeroToHundred()/100	
	 * 		  | difference = this.getMaximumHitpoints() - this.getCurrentHitpoints()
	 *        | newHitpoints = (int)(this.getCurrentHitpoints() + (int)(difference*random)
	 * 	      |  if (newHitpoints<this.getCurrentHitpoints())
	 * 		  | 		setCurrentHitpoints()
	 * 
	 * 
	 */
	@Override
	protected void heal(){
		double random = randomZeroToHundred()/100;
		long difference = this.getMaximumHitpoints() - this.getCurrentHitpoints();
		int newHitpoints = (int)(this.getCurrentHitpoints() + (int)(difference*random));
		if (newHitpoints<this.getCurrentHitpoints()){
			setCurrentHitpoints(newHitpoints);
		}	
	}
	
	/**
	 * Collects treasures from a monster if the monster is dead.
	 * 
	 * @param other
	 * 		  The other mobile that is killed.
	 * @effect The hero collects a random number of treasures and puts them
	 * 		   in his backpack.
	 * 		   | let random = ThreadLocalRandom.current().
	 * 		   |	nextInt(0, other.getNbAnchorpoints() + 1)
	 * 		   | let otherAnchorpoints = other.getAnchorpoints()
	 * 		   | let I = 0
	 * 		   | for entries in otherAnchorpoints
	 * 		   | 	if (i>random)
	 * 		   |		break
	 * 		   | 	if (this.getItemAt(Anchorpoint.BACK) instanceof Backpack)
	 * 		   | 		try
	 * 		   |    		((Backpack)this.getItemAt(Anchorpoint.BACK)).addToContents(entry.getValue())
	 * 		   |        catch 	(InvalidContentException) print("Contens cannot be added to backpack.")
	 */
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
			if (this.getItemAt(Anchorpoint.BACK) instanceof Backpack)
				try{
					((Backpack)this.getItemAt(Anchorpoint.BACK)).addToContents(entry.getValue());
				}
				catch (InvalidContentException ex){
					System.out.println("Contens cannot be added to backpack.");
				}
			i++;
		}
	}
	
	
}