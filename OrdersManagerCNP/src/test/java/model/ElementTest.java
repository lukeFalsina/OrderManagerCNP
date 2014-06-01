package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ElementTest {

	@Test
	public void testEqualsObject() {
		Element firstFood = new Food("testFood", 1, "test1", 0);
		Element firstEqualFood = new Food("testFood", 1, "test2", 0);
		
		assertTrue("Since only description is different, the two food must be equal.",
					firstFood.equals(firstEqualFood));
	}

	@Test
	public void testNotEqualsObject1() {
		Element firstFood = new Food("testFood", 1, "test1", 0);
		Element firstDrink = new Drink("testFood", 1, "test2", 0);
		
		assertFalse("These elements must not be equal(different dynamic class).",
					firstFood.equals(firstDrink));
	}
	
	@Test
	public void testNotEqualsObject2() {
		Element firstFood = new Food("FirsttestFood", 1, "test", 0);
		Element secondFood = new Food("SecondtestFood", 1, "test", 0);
		
		assertFalse("These elements must not be equal(different name).",
					firstFood.equals(secondFood));
	}
}
