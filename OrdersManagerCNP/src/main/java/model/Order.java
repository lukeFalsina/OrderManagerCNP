package model;

import java.util.ArrayList;
import java.util.Iterator;
//import java.util.HashSet;
import java.util.List;
//import java.util.Set;

/**
 * The Order class is the key model class. Each order will contain a list of transactions
 * and further useful information about the single order (such as associated table/seat..).
 * 
 * @author luca
 */
public class Order {

	// Since each transaction is unique it is not allowed for two transactions of
	// the same class to have the same Element object.
	// private Set<Transaction> transactions;
	private List<Transaction> transactions;
	
	// 	Now it's quite useless..
	//	private int associatedTableNumber;
	public Order(Transaction initialTransaction) {		
//		transactions = new HashSet<Transaction>();
		transactions = new ArrayList<Transaction>();
		transactions.add(initialTransaction);
	}
	
	/**
	 * This method checks into the model if an equal transaction is already present in the order.
	 * If so, it increases its quantity by the quantity of the new incoming transaction.
	 * Otherwise it simply append newTransaction to the order.
	 * 
	 * @param newTransaction
	 */
	public void addFurtherTransaction(Transaction newTransaction) {
		
		// I set an auxiliary variable for check purposes..
		boolean alreadyPresentTransaction = false;
		
		for (Transaction checkedTransaction : transactions) {
			
			// When an equal transaction is found the flag of presence is raised
			// and the quantity of checkedTransaction is increased.
			if(checkedTransaction.equals(newTransaction)) {
				
				alreadyPresentTransaction = true;
				checkedTransaction.increaseQuantity(newTransaction.getQuantity());
			}
		}
		
		if(!alreadyPresentTransaction) transactions.add(newTransaction);

	}
	
	/**
	 * This method will compare at the incoming Transaction. If it is 
	 * found equal to an other transaction inside the order, that 
	 * transaction will be removed.
	 * Finally the new updated order is returned.
	 * Please note that if the order contains only a transaction, 
	 * which is removed, this method returns null.
	 * 
	 * @param newTransaction
	 * @return order
	 */
	public Order removeSpecificTransaction(Transaction compareTransaction) {
		
		Iterator<Transaction> iterator = transactions.iterator();
		
		while (iterator.hasNext()) {
			
			Transaction checkedTransaction = iterator.next();
			
			if(checkedTransaction.equals(compareTransaction)) {
				
				// No valid transaction would remain after the remove
				// operation, so also the order is cancelled
				if(transactions.size() == 1) return null;
				else {
					// The transaction is removed and the new order
					// returned to the caller;
					iterator.remove();
					return this;
				}
			}
		}
		
		// If we arrive here, compareTransaction was not included into
		// this order so nothing is changed..
		return this;
	}
	
	/**
	 * This methods returns an arrayList containing all the transactions of the current order.
	 * 
	 * @return the list of transactions
	 */
	public ArrayList<Transaction> getTransactions() {
		
		ArrayList<Transaction> returnList = new ArrayList<Transaction>();
		returnList.addAll(transactions);
		return returnList;
	}
	
	/**
	 * @return the total cost of the order
	 */
	public float getTotalCost() {
		float totalCost = 0f;
		int totalCost2 = 0;
		
		for (Transaction checkedTransaction : transactions) {
			totalCost += checkedTransaction.getTransactionCost();
		}
		//totalCost=(float)((int)(totalCost*100))/100;
		totalCost2=(int)(totalCost*100);
		if (totalCost2%10>5)
			return ((float)(totalCost2-totalCost2%10+10))/100;
		else
			return ((float)totalCost2)/100;
	}
	
}
