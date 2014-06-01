package main;

import java.util.List;

import model.Order;
import model.Element;
import model.Supplement;


public interface DataBaseHandler {

	/**
	 * This method will send a new order that must be saved into the DB.
	 * When the order is saved a new instance of Committed Order is returned, 
	 * which contains the Order object, its ID and date of creation of the 
	 * relative entry in the DB.
	 * 
	 * @param incomingOrder
	 * @return a saved instance of the order
	 */
	CommittedOrder saveOrder(Order incomingOrder);
	
	/**
	 * By providing a valid ID a previously saved order will be 
	 * returned to the caller.
	 * 
	 * @param ID
	 * @return a saved instance of the order
	 */
	CommittedOrder searchOrderByID(long ID);
	
	/**
	 * By providing a correct String (either "FOOD" or "DRINK"), this method
	 * returns the complete list of either Food or Drink objects store in the DB.
	 * 
	 * @param elementChoice
	 * @return list of proper current saved Element objects.
	 */
	List<Element> getCurrentElementList(String elementChoice);
	
	/**
	 * This methods returns the full list of already stored Supplement objects.
	 * 
	 * @return list of all the saved Supplement objects
	 */
	List<Supplement> getCurrentSupplementList();
}
