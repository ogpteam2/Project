package rpg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import be.kuleuven.cs.som.annotate.*;
import rpg.exception.InvalidContentException;
import rpg.inventory.*;
import rpg.value.DucatAmount;
import rpg.value.Unit;
import rpg.value.Weight;
import java.math.*;
/**
 * 
 * @author Robbe, Elias
 *
 */
public class Hero extends Mobile {

	/************************************************
	 * Constructors
	 ************************************************/
	
	public Hero(String name,long hitpoints, BigDecimal strength,
			EnumMap<Anchorpoint,Item> items) 
			throws IllegalArgumentException 
	{
		//assert isValidEnumMap(items);
		super(name,hitpoints,strength);
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
		boolean nameIsValid = pattern.matcher(heroName).matches();
		int numberOfApos = getNumberOfMataches(patternApo,heroName);
		boolean colonNotSpace = patternColonSpace.matcher(heroName).matches();
		return nameIsValid && numberOfApos<=2 && !colonNotSpace;
	}
	
	/**
	 * 
	 * @param pattern
	 * @param string
	 * @return
	 */
	private int getNumberOfMataches(Pattern pattern, String string){
		int count = 0;
		Matcher matcher = pattern.matcher(string);
		while (matcher.find())
		    count++;
		return count;
	}
	
	/**
	 * 
	 */
	private final Pattern pattern = Pattern.compile("[A-Z][:'A-Za-z\\s]*");
	/**
	 * 
	 */
	private final Pattern patternApo = Pattern.compile("'");
	/**
	 * 
	 */
	private final Pattern patternColonSpace = Pattern.compile(":^\\s");
	
	
	/************************************************
	 * Capacity
	 ************************************************/
	
	/**
	 * Gives the capacity in kg.
	 */
	public Weight getCapacity(){
		BigDecimal calculatedCapacity;
		if (this.getRawStrength().compareTo(BigDecimal.ZERO) == -1 || 
				this.getRawStrength().compareTo(BigDecimal.ZERO) == 0)
			calculatedCapacity = BigDecimal.ZERO;
		if (this.getRawStrength().compareTo(BigDecimal.ONE) == 1 &&
				this.getRawStrength().compareTo(BigDecimal.TEN) == -1)
			calculatedCapacity = this.getRawStrength().multiply(BigDecimal.TEN);
		if (this.getRawStrength().compareTo(BigDecimal.TEN) == 1 &&
				this.getRawStrength().compareTo(BigDecimal.valueOf(20)) == -1)
			calculatedCapacity = getCapacityByStrengthBetweenTenAndTwenty(getRawStrength());
		else{
			calculatedCapacity =getCapacityByStrengthAboveTwenty(this.getRawStrength());
		}
		return new Weight(calculatedCapacity,Unit.kg);
	}
	
	
	private Map<BigDecimal, BigDecimal> availableCapacities;
	
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
	
	private BigDecimal getCapacityByStrengthBetweenTenAndTwenty(BigDecimal strength){
		BigDecimal capacity = BigDecimal.ZERO;
		for (Map.Entry<BigDecimal, BigDecimal> entry : availableCapacities.entrySet()){
			if (strength.compareTo(entry.getKey()) == -1)
				capacity = entry.getValue();
		}
		return capacity;
	}
	
	private BigDecimal getCapacityByStrengthAboveTwenty(BigDecimal strength){
		BigDecimal pow = strength.min(BigDecimal.TEN).divideToIntegralValue(BigDecimal.TEN);
		BigDecimal strengthModTen = strength.remainder(BigDecimal.TEN).add(BigDecimal.TEN); 
		BigDecimal baseResult = getCapacityByStrengthBetweenTenAndTwenty(strengthModTen);
		BigDecimal result = powerOfBigDecimal(BigDecimal.valueOf(4),pow);
		return result.multiply(baseResult);
	}
	
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
	
	public long getTotalProtection(){
		int result = getRawProtection();
		if (getItemAt(Anchorpoint.BODY) != null){
			result = result + ((Armor)getItemAt(Anchorpoint.BELT)).getCurrentProtection();
		}
		return result;
	}
	
	/************************************************
	 * Hit
	 ************************************************/
	
	protected int getTotalStrength(){
		int result = this.getRawStrength().intValueExact();
		if (getItemAt(Anchorpoint.LEFT) instanceof Weapon)
			result += ((Weapon)getItemAt(Anchorpoint.LEFT)).getStrength();
		if (getItemAt(Anchorpoint.RIGHT) instanceof Weapon)
			result += ((Weapon)getItemAt(Anchorpoint.RIGHT)).getStrength();
		return result;
	}
	
	
	@Override
	protected int calculateDamage(){
		return (int)(getTotalStrength()-10)/2;
	}
	
	@Override
	protected void heal(){
		double random = randomZeroToHundred()/100;
		long difference = this.getMaximumHitpoints() - this.getCurrentHitpoints();
		setCurrentHitpoints((int)(difference*random));
	}
	
	@Override
	protected void collectTreasures(Mobile other){
		int random = ThreadLocalRandom.current().nextInt(0, other.getNbAnchorpoints() + 1);
		EnumMap<Anchorpoint,Item> otherAnchorpoints = other.getAnchorpoints();
		int i = 0;
		for (EnumMap.Entry<Anchorpoint, Item> entry : otherAnchorpoints.entrySet()){
			if (random>i){
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