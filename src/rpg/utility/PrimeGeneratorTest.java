package rpg.utility;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

public class PrimeGeneratorTest {
	
	PrimeGenerator gen;
	long[] littlePrimeList = {2,3,5,7,11,13,17,19};
	long[] generatedPrimeList;
	
	@Before
	public void setUp() {
		gen = new PrimeGenerator();
		generatedPrimeList = new long[littlePrimeList.length];
	}

	@Test
	public void firstElementsCheck() {
		for(int i = 0; i < littlePrimeList.length; i++){
			generatedPrimeList[i] = gen.nextID();
		}
		assertArrayEquals(littlePrimeList,generatedPrimeList);
	}
	
	@Test
	public void closestPrimeTest(){
		assertEquals(7L, gen.closestPrime(9L));
	}
}
