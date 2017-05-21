package rpg.utility;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

public class PrimeIteratorTest {
	
	PrimeGenerator it;
	long[] littlePrimeList = {2,3,5,7,11,13,17,19};
	long[] generatedPrimeList;
	
	@Before
	public void setUp() {
		it = new PrimeGenerator();
		generatedPrimeList = new long[littlePrimeList.length];
	}

	@Test
	public void firstElementsCheck() {
		for(int i = 0; i < littlePrimeList.length; i++){
			generatedPrimeList[i] = it.nextID();
		}
		assertArrayEquals(littlePrimeList,generatedPrimeList);
	}
}
