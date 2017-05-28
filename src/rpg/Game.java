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

	public static void main(String[] args) {
		Hero Jimmie;
		Armor chestplate, chainmail;
		Monster HillGiant;
		Weapon claws, dagger, longSword;
		Armor skin;

		EnumMap<Anchorpoint, Item> itemsNormal = new EnumMap<Anchorpoint, Item>(Anchorpoint.class);
		Weight weight = new Weight(BigDecimal.TEN, Unit.kg);
		DucatAmount value = new DucatAmount(BigDecimal.TEN);
		chestplate = new Armor(weight, value, 10, ArmorType.STANDARD);
		chestplate.setCurrentProtection(50);
		itemsNormal.put(Anchorpoint.BODY, chestplate);
		chainmail = new Armor(weight, value, 10, ArmorType.STANDARD);
		chainmail.setCurrentProtection(50);
		itemsNormal.put(Anchorpoint.LEFT, chainmail);
		Jimmie = new Hero("Jimmie", 10L, BigDecimal.TEN, itemsNormal);
		DucatAmount valueOfAnchor = new DucatAmount(BigDecimal.ZERO);
		for (EnumMap.Entry<Anchorpoint, Item> entry : Jimmie.getAnchorpoints().entrySet()) {
			Item item = entry.getValue();
			DucatAmount amount = item.getValue();
			valueOfAnchor = valueOfAnchor.add(amount);
		}
		System.out.println(valueOfAnchor);

		skin = new Armor(weight, value, 10, ArmorType.STANDARD);
		skin.setCurrentProtection(150);
		claws = new Weapon(weight, 20);
		EnumMap<Anchorpoint, Item> monsterItems = new EnumMap<Anchorpoint, Item>(Anchorpoint.class);
		dagger = new Weapon(weight, 14);
		longSword = new Weapon(weight, 21);
		monsterItems.put(Anchorpoint.LEFT, dagger);
		monsterItems.put(Anchorpoint.RIGHT, longSword);

		HillGiant = new Monster("Bimmy", 100L, BigDecimal.TEN, claws, skin, monsterItems);
		
		DucatAmount valueOfMonster = new DucatAmount(BigDecimal.ZERO);
		for (EnumMap.Entry<Anchorpoint, Item> entry : HillGiant.getAnchorpoints().entrySet()) {
			Item item = entry.getValue();
			DucatAmount amount = item.getValue();
			valueOfMonster = valueOfMonster.add(amount);
		}
		System.out.println(valueOfMonster);
		
		// Battle
		
		
		
		
		
		
		
	}

}
