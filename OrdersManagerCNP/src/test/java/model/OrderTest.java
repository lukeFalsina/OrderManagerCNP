package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

public class OrderTest {

	static Food firstFood, secondFood;
	static Drink firstDrink;
	static Supplement supplement;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		firstFood = new Food("firstTestFood", 4.5f, "I'm really tasty!", 0);
		secondFood = new Food("secondTestFood", 6.5f, "I'm even more!", 0);
		firstDrink = new Drink("firstDrinkTest", 2.5f, "Refreshing!", 0);
		supplement = new Supplement("supplementTest", 2f, 0);
	}

	@Test
	public void testGetTransactions() {
		Order testOrder = new Order(new Transaction(firstDrink, 2));
		ArrayList<Transaction> transactionsInOrder = testOrder.getTransactions();
		
		assertTrue("Only a transaction is present.", transactionsInOrder.size() == 1);
		assertTrue("Returned transaction is valid", transactionsInOrder.get(0).getInvolvedElement().equals(firstDrink)
					&& transactionsInOrder.get(0).getQuantity() == 2);
	}

	@Test
	public void testAddFurtherTransaction1() {
		Order testOrder = new Order(new Transaction(firstDrink, 2));
		testOrder.addFurtherTransaction(new TransactionWithSupplement(secondFood, 3, supplement));
		ArrayList<Transaction> transactionsInOrder = testOrder.getTransactions();
		
		assertTrue("Two transaction are present.", transactionsInOrder.size() == 2);
	}

	@Test
	public void testAddFurtherTransaction2() {
		Order testOrder = new Order(new TransactionWithSupplement(secondFood, 3, supplement));
		testOrder.addFurtherTransaction(new TransactionWithSupplement(secondFood, 7, supplement));
		ArrayList<Transaction> transactionsInOrder = testOrder.getTransactions();
		
		assertTrue("Only a transaction is present.", transactionsInOrder.size() == 1);
		assertTrue("Returned transaction is valid", transactionsInOrder.get(0).getInvolvedElement().equals(secondFood)
					&& transactionsInOrder.get(0).getQuantity() == 10);
	}

	@Test
	public void testRemoveSpecificTransaction() {
		Order testOrder = new Order(new TransactionWithSupplement(firstFood, 3, supplement));
		testOrder.addFurtherTransaction(new Transaction(firstDrink, 2));
		
		Order firstResultTransaction = testOrder.removeSpecificTransaction(new TransactionWithSupplement(secondFood, 7, supplement));
		assertTrue("The first remove do not affect testOrder.", testOrder.equals(firstResultTransaction));

		Order secondResultTransaction = testOrder.removeSpecificTransaction(new TransactionWithSupplement(firstFood, 3, supplement));
		assertTrue("The second remove affect testOrder.", secondResultTransaction.getTransactions().size() == 1);
		assertTrue("The second remove affect testOrder.", 
					secondResultTransaction.getTransactions().get(0).equals(new Transaction(firstDrink, 2)));
		
		Order thirdResultTransaction = testOrder.removeSpecificTransaction(new Transaction(firstDrink, 2));
		assertTrue("The last remove returns a null object.", thirdResultTransaction == null);
	}
	
	@Test
	public void testGetTotalCost() {
		Order testFullOrder = new Order(new TransactionWithSupplement(firstFood, 2, supplement));
		testFullOrder.addFurtherTransaction(new Transaction(secondFood, 2));
		testFullOrder.addFurtherTransaction(new Transaction(firstDrink, 3));
		ArrayList<Transaction> transactionsInOrder = testFullOrder.getTransactions();
		
		assertTrue("Three transactions were stored.", transactionsInOrder.size() == 3);
		assertTrue("Returned transaction cost is valid", testFullOrder.getTotalCost() == 33.5f);
	}

}
