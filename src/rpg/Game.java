package rpg;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import rpg.*;
import rpg.exception.InvalidContentException;
import rpg.inventory.Anchorpoint;
import rpg.inventory.Armor;
import rpg.inventory.ArmorType;
import rpg.inventory.Backpack;
import rpg.inventory.Item;
import rpg.inventory.Weapon;
import rpg.value.DucatAmount;
import rpg.value.Unit;
import rpg.value.Weight;

public class Game {

	public static void main(String[] args) throws InvalidContentException {
		Hero Jimmie;
		Armor chestplate, chainmail;
		Monster HillGiant;
		Weapon claws, dagger, longSword;
		Armor skin;
		Mobile winner;
		DucatAmount winnerValue;
		Backpack myBackpack;
		
		EnumMap<Anchorpoint, Item> itemsNormal = new EnumMap<Anchorpoint, Item>(Anchorpoint.class);
		Weight weight = new Weight(BigDecimal.TEN, Unit.kg);
		DucatAmount value = new DucatAmount(BigDecimal.TEN);
		chestplate = new Armor(weight, value, 10, ArmorType.STANDARD);
		chestplate.setCurrentProtection(8);
		itemsNormal.put(Anchorpoint.BODY, chestplate);
		chainmail = new Armor(weight, value, 10, ArmorType.STANDARD);
		chainmail.setCurrentProtection(7);
		itemsNormal.put(Anchorpoint.LEFT, chainmail);
		Jimmie = new Hero("Jimmie", 100L, BigDecimal.valueOf(100), itemsNormal);
		DucatAmount valueOfHero = new DucatAmount(BigDecimal.ZERO);
		for (EnumMap.Entry<Anchorpoint, Item> entry : Jimmie.getAnchorpoints().entrySet()) {
			Item item = entry.getValue();
			DucatAmount amount = item.getValue();
			valueOfHero = valueOfHero.add(amount);
		}
		System.out.println(valueOfHero + " value of Hero");

		skin = new Armor(weight, value, 10, ArmorType.STANDARD);
		skin.setCurrentProtection(7);
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
		System.out.println(valueOfMonster + " value of the monster.");

		// Battle

		int random = ThreadLocalRandom.current().nextInt(0, 2);
		while (Jimmie.getCurrentHitpoints() > 0 && HillGiant.getCurrentHitpoints() > 0) {
			System.out.println(Jimmie.getCurrentHitpoints() + " Jimmies Hitpoints");
			System.out.println(HillGiant.getCurrentHitpoints() + " HillGiant's Hitpoints");
			if (random == 0) {
				HillGiant.hit(Jimmie);
			} else {

				Jimmie.hit(HillGiant);
			}
		}
		if (Jimmie.getCurrentHitpoints() <= 0) {
			System.out.println("HillGiant won");
			winner = HillGiant;
		}
		else{
			System.out.println("Jimmie won");
			winner = Jimmie;
		}
		
		
		
		winnerValue = new DucatAmount(BigDecimal.ZERO);
		for (EnumMap.Entry<Anchorpoint, Item> entry : winner.getAnchorpoints().entrySet()) {
			Item item = entry.getValue();
			DucatAmount amount = item.getValue();
			winnerValue = winnerValue.add(amount);
		}
		System.out.println(winnerValue + " : the total value of the winner.");


	}

}
