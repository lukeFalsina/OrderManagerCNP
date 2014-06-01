package model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionTest {

	static Element firstElement, firstSimilarElement, secondElement;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		firstElement = new Food("TestFood1", 1, "Test", 0);
		firstSimilarElement = new Food("TestFood1", 2, "Test", 0);
		secondElement = new Food("TestFood2", 2, "Test", 0);
	}

	@Test
	public void testIncreaseQuantity() {
		Transaction testTransaction = new Transaction(firstElement, 1);
		testTransaction.increaseQuantity(3);
		
		assertTrue("Error while adding transaction quantities.", testTransaction.getQuantity() == 4);
	}

	@Test
	public void testEqualsObject() {
		
		Transaction testTransaction1 = new Transaction(firstElement, 1);
		Transaction testTransaction2 = new Transaction(firstElement, 2);
		assertTrue("Even if the quantity is different, these transactions must be considered equal",
					testTransaction1.equals(testTransaction2));
		
		Transaction testTransaction3 = new Transaction(firstSimilarElement, 1);
		assertFalse("Since firstElement and firstSimilarElement are different, also the respective transactions are different",
					testTransaction1.equals(testTransaction3));
	}

}
