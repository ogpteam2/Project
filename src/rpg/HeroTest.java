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
	
	@Before
	public void setUp() {
		// genericHero
		genericHero = new Hero("Jim");
		
		// normalHero
		EnumMap<Anchorpoint,Item> itemsNormal = new EnumMap<Anchorpoint,Item>(Anchorpoint.class);
		Weight weight = new Weight(BigDecimal.TEN,Unit.kg);
		DucatAmount value = new DucatAmount();
		armor = new Armor(weight,value,10,ArmorType.STANDARD);
		itemsNormal.put(Anchorpoint.BODY, armor);
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
	
	@Ignore @Test
	public void getAttributes(){
		genericHero.getAnchorpoints();
		genericHero.getCapacity();
		genericHero.getCapacityOfAnchorpoints();
		genericHero.getCurrentHitpoints();
		genericHero.getIsDead();
		genericHero.getItemAt(Anchorpoint.BODY);
		genericHero.getMaximumHitpoints();
		genericHero.getName();
		genericHero.getNbAnchorpoints();
		genericHero.getNbValidAnchorpoints();
		genericHero.getRawProtection();
		genericHero.getRawStrength();
		genericHero.getTotalProtection();
		genericHero.getTotalStrength();
	}
	
	@Ignore @Test
	public void replaceItem(){
		genericHero.removeItemAt(Anchorpoint.BODY);
		genericHero.setItemAt(armor, Anchorpoint.BODY);
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
	

	
	
	
	
	
	
	
	
}
