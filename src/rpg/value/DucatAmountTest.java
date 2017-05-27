package rpg.value;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class DucatAmountTest {

	private DucatAmount normalAmount;
	
	@Before
	public void setUp() {
		normalAmount = new DucatAmount(42);
	}

	@Test
	public void testWeight(){
		assertEquals(new Weight(2100,Unit.g), normalAmount.getWeight());
	}

}
