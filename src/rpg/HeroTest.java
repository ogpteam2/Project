package rpg;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

public class HeroTest {
	
	Hero genericHero;
	Hero nametestHero;
	Pattern validNamePattern;
	
	@Before
	public void setUp() {
		genericHero = new Hero("Jim");
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
	
}
