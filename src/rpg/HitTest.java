package rpg;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.EnumMap;

import org.junit.Before;
import org.junit.Test;

import rpg.exception.InvalidContentException;
import rpg.inventory.Anchorpoint;
import rpg.inventory.Armor;
import rpg.inventory.ArmorType;
import rpg.inventory.Item;
import rpg.inventory.Weapon;
import rpg.value.DucatAmount;
import rpg.value.Unit;
import rpg.value.Weight;

public class HitTest {
	
	private Hero Jimmie;
	private Armor chestplate, chainmail;
	private Monster HillGiant;
	private Weapon claws, dagger, longSword;
	private Armor skin;
	
	@Before
	public void setUp() {

		EnumMap<Anchorpoint, Item> itemsNormal = new EnumMap<Anchorpoint, Item>(Anchorpoint.class);
		Weight weight = new Weight(BigDecimal.TEN, Unit.kg);
		DucatAmount value = new DucatAmount(BigDecimal.TEN);
		chestplate = new Armor(weight, value, 10, ArmorType.STANDARD);
		chestplate.setCurrentProtection(8);
		itemsNormal.put(Anchorpoint.BODY, chestplate);
		chainmail = new Armor(weight, value, 10, ArmorType.STANDARD);
		chainmail.setCurrentProtection(7);
		
		
		
		itemsNormal.put(Anchorpoint.LEFT, chainmail);
		Jimmie = new Hero("Jimmie", 10L, BigDecimal.valueOf(150), itemsNormal);
		skin = new Armor(weight, value, 10, ArmorType.STANDARD);
		skin.setCurrentProtection(7);
		claws = new Weapon(weight, 21);
		EnumMap<Anchorpoint, Item> monsterItems = new EnumMap<Anchorpoint, Item>(Anchorpoint.class);
		dagger = new Weapon(weight, 14);
		longSword = new Weapon(weight, 21);
		monsterItems.put(Anchorpoint.LEFT, dagger);
		monsterItems.put(Anchorpoint.RIGHT, longSword);
		HillGiant = new Monster("Bimmy", 100L, BigDecimal.TEN, claws, skin, monsterItems);
	}
	
	
	@Test
	public void HeroHitsMonster() throws InvalidContentException {
		System.out.println(Jimmie.getAnchorpoints());
		while (HillGiant.getCurrentHitpoints()>0){
			Jimmie.hit(HillGiant);
		}
		assertTrue(HillGiant.getCurrentHitpoints()<=0);
		System.out.println(Jimmie.getAnchorpoints());
	}
	
	@Test
	public void MonsterHitsHero() throws InvalidContentException
	{
		System.out.println(HillGiant.getAnchorpoints());
		while (Jimmie.getCurrentHitpoints()>0){
			HillGiant.hit(Jimmie);
		}
		assertTrue(Jimmie.getCurrentHitpoints()<=0);
		System.out.println(HillGiant.getAnchorpoints());

	}
	
	
	
	
	
	
	
	
	

}
