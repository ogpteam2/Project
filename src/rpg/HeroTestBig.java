package rpg;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.EnumMap;

import org.junit.Before;
import org.junit.Test;

import rpg.inventory.Anchorpoint;
import rpg.inventory.Armor;
import rpg.inventory.ArmorType;
import rpg.inventory.Backpack;
import rpg.inventory.Item;
import rpg.inventory.Weapon;
import rpg.value.DucatAmount;
import rpg.value.Unit;
import rpg.value.Weight;

public class HeroTestBig {
	Hero Jimmie;
	Armor chestplate, chainmail;
	
	@Before
	public void setUp(){

		
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
		
	}
	
	@Test
	public void testIsValidNameValidCase() {
		assertTrue(Jimmie.isValidName("Complex: name'within'limits of spec"));
	}
	
	@Test
	public void testIsValidNameInvalidCase() {
		assertFalse(Jimmie.isValidName("complex:name'blatantly'violating'all'rules"));
	}
	
	@Test
	public void testIsValidNameBasicValid(){
		assertTrue(Jimmie.isValidName("Jimothy"));
	}
	
	@Test
	public void getAnchorpoints(){
	    assertNotNull(Jimmie.getAnchorpoints());
	}
	@Test
	public void getCapacity(){
	    assertEquals(new Weight(BigDecimal.valueOf(0),Unit.kg), Jimmie.getCapacity());
	}
	@Test
	public void getCapacityOfAnchorpoints(){
	    assertEquals(new Weight(BigDecimal.valueOf(0),Unit.kg), Jimmie.getCapacityOfAnchorpoints());
	}
	@Test
	public void getCurrentHitpoints(){
	    assertEquals(100, Jimmie.getCurrentHitpoints());
	}
	@Test
	public void getIsDead(){
	    assertEquals(false, Jimmie.getIsDead());
	}
	@Test
	public void getItemAt(){
	    assertNotNull(Jimmie.getItemAt(Anchorpoint.BODY));
	}
	@Test
	public void getMaximumHitpoints(){
	    assertEquals(100, Jimmie.getMaximumHitpoints());
	}
	@Test
	public void getName(){
	    assertEquals("Jimmie", Jimmie.getName());
	}
	@Test
	public void getNbAnchorpoints(){
	    assertEquals(2, Jimmie.getNbAnchorpoints());
	}
	@Test
	public void getNbValidAnchorpoints(){
	    assertEquals(5, Jimmie.getNbValidAnchorpoints());
	}
	@Test
	public void getRawProtection(){
	    assertEquals(10, Jimmie.getRawProtection());
	}
	@Test
	public void getRawStrength(){
	    assertEquals(BigDecimal.valueOf(100).setScale(2), Jimmie.getRawStrength());
	}
	@Test
	public void getTotalProtection(){
	    assertEquals(18, Jimmie.getTotalProtection());
	}
	@Test
	public void getTotalStrength(){
	    assertEquals(100, Jimmie.getTotalStrength());
	}
	
	@Test
	public void replaceItem(){
		Weight weight = new Weight(BigDecimal.TEN);
		DucatAmount value = new DucatAmount(BigDecimal.valueOf(13));
		Armor armor = new Armor(weight, value, 10, ArmorType.STANDARD);
		armor.setCurrentProtection(8);
		Jimmie.removeItemAt(Anchorpoint.BODY);
		Jimmie.setItemAt(armor, Anchorpoint.BODY);
		assertEquals(Jimmie.getItemAt(Anchorpoint.BODY),armor);
	}

	@Test (expected = IllegalArgumentException.class)
	public void setNameInvalid(){
		Jimmie.setName("fklfsljsqkljfdjklfkljlfdkjqmlkfsmjkfsqmlkfsqml");
	}
	
	@Test
	public void setNameValid(){
		Jimmie.setName("BobTheBuilder");
		assertEquals(Jimmie.getName(),"BobTheBuilder");
	}
	
	@Test
	public void setDead(){
		Jimmie.setIsDead(true);
		assertEquals(true,Jimmie.getIsDead());
	}
	
	@Test
	public void totalDamage(){
		assertEquals(45,Jimmie.calculateDamage());
	}
	
	@Test 
	public void totalProtection(){
		// implement armor current protection
		assertEquals(18,Jimmie.getTotalProtection());
	}
	
	@Test 
	public void getCapacityBetweenZeroToTen(){
		Jimmie.setRawStrength(BigDecimal.valueOf(8));
		assertEquals(new Weight(BigDecimal.valueOf(80).setScale(2),Unit.kg),Jimmie.getCapacity());	
	}
	

}
