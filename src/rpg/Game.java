package rpg;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.regex.Pattern;

import rpg.*;
import rpg.inventory.Anchorpoint;
import rpg.inventory.Armor;
import rpg.inventory.ArmorType;
import rpg.inventory.Item;
import rpg.inventory.Weapon;
import rpg.value.DucatAmount;
import rpg.value.Unit;
import rpg.value.Weight;

public class Game {

	
	public static void main(String[] args){
		Hero Jimmie;
		Armor armor;
		Weapon weapon;
		
		// enumMap with armor
		EnumMap<Anchorpoint,Item> itemsNormal = new EnumMap<Anchorpoint,Item>(Anchorpoint.class);
		Weight weight = new Weight(BigDecimal.TEN,Unit.kg);
		DucatAmount value = new DucatAmount(BigDecimal.TEN);
		armor = new Armor(weight,value,10,ArmorType.STANDARD);
		armor.setCurrentProtection(50);
		itemsNormal.put(Anchorpoint.BODY, armor);
		
		Jimmie = new Hero("Jimmie",10L,BigDecimal.TEN,itemsNormal);
		
		DucatAmount valueOfAnchor = new DucatAmount(BigDecimal.ZERO);
		
		for (EnumMap.Entry<Anchorpoint, Item> entry : Jimmie.getAnchorpoints().entrySet()){
				Item item = entry.getValue();
				DucatAmount amount = item.getValue();
				System.out.println(amount);
				valueOfAnchor = valueOfAnchor.add(amount);
				System.out.println(valueOfAnchor);
				
				 
		}
		System.out.println(valueOfAnchor);
	}


}
