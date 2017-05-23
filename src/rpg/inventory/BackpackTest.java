package rpg.inventory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BackpackTest {
	
	public Backpack backpack1;
	public Backpack backpack2;
	
	@Before
	public void setUp() {
		backpack1 = new Backpack();
		backpack2 = new Backpack();
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
		for(int i = 0; i<62; i++) backpack2 = new Backpack();
		assertTrue(backpack1.getID() == backpack2.getID());
	}
}
