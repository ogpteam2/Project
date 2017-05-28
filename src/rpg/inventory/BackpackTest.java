package rpg.inventory;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
<<<<<<< HEAD
import org.junit.rules.ExpectedException;

import rpg.exception.InvalidContentException;
import rpg.value.DucatAmount;
=======

>>>>>>> branch 'master' of https://github.com/ogpteam2/Project.git
import rpg.value.Weight;

public class BackpackTest {
	
	public Backpack backpack;
	public DucatAmount money;
	public Weapon sword;
	public Purse moneyBag;
	public Armor chestPlate;
	public Armor heavyArmor;
	
	public Weight standardWeight = new Weight(BigDecimal.ONE);
	public Weight standardCap = new Weight(new BigDecimal(1000));
	public DucatAmount standardValue = new DucatAmount(100);
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();

	
	
	
	public Weight standardWeight = new Weight(BigDecimal.ONE);
	public Weight standardCap = new Weight(new BigDecimal(100));
	
	@Before
	public void setUp() {
<<<<<<< HEAD
		backpack = new Backpack(standardWeight,standardCap);
		money = new DucatAmount();
		sword = new Weapon(standardWeight, 21);
		moneyBag = new Purse(standardWeight);
		chestPlate = new Armor(standardWeight,standardValue,100);
		heavyArmor = new Armor(standardCap,standardValue,100);
	}
	
	public void testAddItems() throws InvalidContentException{
		backpack.addToContents(sword);
		backpack.addToContents(moneyBag);
=======
		backpack1 = new Backpack(standardWeight,standardCap);
		backpack2 = new Backpack(standardWeight,standardCap);
>>>>>>> branch 'master' of https://github.com/ogpteam2/Project.git
	}
	
	@Test
	public void addNullItem() throws InvalidContentException, NullPointerException{
		thrown.expect(NullPointerException.class);
		Item nullItem = null;
		backpack.addToContents(nullItem);
	}
	
	@Test
<<<<<<< HEAD
	public void addToHeavyItem() throws InvalidContentException{
		thrown.expect(InvalidContentException.class);
		backpack.addToContents(heavyArmor);
		System.out.println(backpack.getWeightOfContents());
=======
	public void idGeneratorTest(){
		for(int i = 0; i<62; i++) backpack2 = new Backpack(standardWeight,standardCap);
		assertTrue(backpack1.getID() == backpack2.getID());
>>>>>>> branch 'master' of https://github.com/ogpteam2/Project.git
	}
	
	@Test
	public void 
}
