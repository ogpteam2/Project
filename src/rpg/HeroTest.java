package rpg;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import rpg.*;
import rpg.inventory.*;
import rpg.utility.*;
import rpg.value.*;
import rpg.exception.*;
public class HeroTest {
	
	Hero genericHero;
	Hero nametestHero;
	Hero normal;
	Pattern validNamePattern;
	Armor armor;
	Weapon weapon;
	
	@Before
	public void setUp() {
		// genericHero
		genericHero = new Hero("Jim");
		
		// gear
		EnumMap<Anchorpoint,Item> itemsNormal = new EnumMap<Anchorpoint,Item>(Anchorpoint.class);
		Weight weight = new Weight(BigDecimal.TEN,Unit.kg);
		DucatAmount value = new DucatAmount();
		armor = new Armor(weight,value,10,ArmorType.STANDARD);
		itemsNormal.put(Anchorpoint.BODY, armor);
		
		//weapon
		
		
		// normalHero
		normal = new Hero("Jimmie",10L,BigDecimal.TEN,itemsNormal);
	}
	
	@Test
	public void testIsValidNameValidCase() {
		assertTrue(genericHero.isValidName("Complex: name'within'limits of spec"));
	}
	
	@Test
	public void testIsValidNameInvalidCase() {
		assertFalse(genericHero.isValidName("complex:name'blatantly'violating'all'rules"));
	}
	
	@Test
	public void testIsValidNameBasicValid(){
		assertTrue(genericHero.isValidName("Jimothy"));
	}
	
	@Test
	public void getAnchorpoints(){
	    assertNotNull(genericHero.getAnchorpoints());
	}
	@Test
	public void getCapacity(){
	    assertEquals(new Weight(BigDecimal.valueOf(0),Unit.kg), genericHero.getCapacity());
	}
	@Test
	public void getCapacityOfAnchorpoints(){
	    assertEquals(new Weight(BigDecimal.valueOf(0),Unit.kg), genericHero.getCapacityOfAnchorpoints());
	}
	@Test
	public void getCurrentHitpoints(){
	    assertEquals(2, genericHero.getCurrentHitpoints());
	}
	@Test
	public void getIsDead(){
	    assertEquals(false, genericHero.getIsDead());
	}
	@Test
	public void getItemAt(){
	    assertNotNull(genericHero.getItemAt(Anchorpoint.BODY));
	}
	@Test
	public void getMaximumHitpoints(){
	    assertEquals(2, genericHero.getMaximumHitpoints());
	}
	@Test
	public void getName(){
	    assertEquals("Jim", genericHero.getName());
	}
	@Test
	public void getNbAnchorpoints(){
	    assertEquals(1, genericHero.getNbAnchorpoints());
	}
	@Test
	public void getNbValidAnchorpoints(){
	    assertEquals(5, genericHero.getNbValidAnchorpoints());
	}
	@Test
	public void getRawProtection(){
	    assertEquals(10, genericHero.getRawProtection());
	}
	@Test
	public void getRawStrength(){
	    assertEquals(BigDecimal.ZERO.setScale(2), genericHero.getRawStrength());
	}
	@Test
	public void getTotalProtection(){
	    assertEquals(10, genericHero.getTotalProtection());
	}
	@Test
	public void getTotalStrength(){
	    assertEquals(0, genericHero.getTotalStrength());
	}
	
	@Ignore @Test
	public void replaceItem(){
		genericHero.removeItemAt(Anchorpoint.BODY);
		genericHero.setItemAt(armor, Anchorpoint.BODY);
		assertEquals(genericHero.getItemAt(Anchorpoint.BODY),armor);
	}

	@Test (expected = IllegalArgumentException.class)
	public void setNameInvalid(){
		genericHero.setName("fklfsljsqkljfdjklfkljlfdkjqmlkfsmjkfsqmlkfsqml");
	}
	
	@Test
	public void setNameValid(){
		genericHero.setName("BobTheBuilder");
		assertEquals(genericHero.getName(),"BobTheBuilder");
	}
	
	@Test
	public void setDead(){
		genericHero.setIsDead(true);
		assertEquals(true,genericHero.getIsDead());
	}
	
	@Test
	public void totalDamage(){
		assertEquals(0,genericHero.calculateDamage());
	}
	
	@Test 
	public void totalProtection(){
		// implement armor current protection
		assertEquals(11,genericHero.getTotalProtection());
	}
	
	@Test 
	public void getCapacityBetweenZeroToTen(){
		genericHero.setRawStrength(BigDecimal.valueOf(8));
		assertEquals(new Weight(BigDecimal.valueOf(80).setScale(2),Unit.kg),genericHero.getCapacity());	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
