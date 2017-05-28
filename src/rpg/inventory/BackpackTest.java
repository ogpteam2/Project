package rpg.inventory;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import rpg.value.Weight;

public class BackpackTest {
	
	public Backpack backpack1;
	public Backpack backpack2;
	
	
	
	public Weight standardWeight = new Weight(BigDecimal.ONE);
	public Weight standardCap = new Weight(new BigDecimal(100));
	
	@Before
	public void setUp() {
		backpack1 = new Backpack(standardWeight,standardCap);
		backpack2 = new Backpack(standardWeight,standardCap);
	}
	
	@Test
	public void idTest(){
		assertFalse(backpack1.getID() == backpack2.getID());
	}
	
	/**
	 * Check if the idgenerator successfully resets upon encountering the last
	 * element in the iterator. Testing of the iterator has shown it can generate
	 * 64 unique id's, so id no 65 should be identical to id no 1.
	 */
	@Test
	public void idGeneratorTest(){
		for(int i = 0; i<62; i++) backpack2 = new Backpack(standardWeight,standardCap);
		assertTrue(backpack1.getID() == backpack2.getID());
	}
	
	@Test
	public void 
}
