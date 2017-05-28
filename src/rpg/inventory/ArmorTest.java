package rpg.inventory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import rpg.value.DucatAmount;
import rpg.value.Unit;
import rpg.value.Weight;

public class ArmorTest {

	private Armor normal;
	
	@Before
	public void setUp() {
		normal = new Armor(new Weight(100,Unit.kg), new DucatAmount(100), 100);
		normal.setCurrentProtection(50);
	}

	@Test
	public void procentualProtectionTest() {
		assertEquals((double) 1/2, normal.getProcentualProtection(),.0000000001);
	}
	
	@Test
	public void valueTest() {
		assertTrue(new DucatAmount(50).equals(normal.getValue()));
	}

}
