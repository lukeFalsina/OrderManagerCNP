package model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionWithSupplementTest {

	static Food food;
	static Supplement firstSupplement, secondSupplement;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		food = new Food("TestFood", 4f, "Test", 0);
		firstSupplement = new Supplement("More stupid Test", 3.5f, 0);
		secondSupplement = new Supplement("Even more stupid Test", 3.5f, 0);
	}

	@Test
	public void testNotEqualsObject1() {
		Transaction firstTransactionWithSupplement = new TransactionWithSupplement(food, 1, firstSupplement);
		Transaction secondTransactionWithSupplement = new TransactionWithSupplement(food, 1, secondSupplement);
	
		assertFalse("The two transactions must be different.",
				firstTransactionWithSupplement.equals(secondTransactionWithSupplement));
	}

	@Test
	public void testNotEqualsObject2() {
		Transaction firstTransaction = new Transaction(food, 1);
		Transaction firstTransactionWithSupplement = new TransactionWithSupplement(food, 1, firstSupplement);
	
		assertFalse("The two transactions must be different.",
					firstTransaction.equals(firstTransactionWithSupplement));
	}
	
	@Test
	public void getTransactionCost() {
		Transaction transaction = new Transaction(food, 5);
		Transaction transactionWithSupplement = new TransactionWithSupplement(food, 3, firstSupplement);
	
		assertTrue("The cost of this transaction is wrong.",
					transaction.getTransactionCost() == 20f);
		assertTrue("The cost of this transaction with supplement is wrong.",
				transactionWithSupplement.getTransactionCost() == 22.5f);
		
	}
}
