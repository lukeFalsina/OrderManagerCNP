package view;

import main.CommittedOrder;
import model.Element;
import model.Supplement;

public interface ReturnSwingView {

	void retrieveOrderToBeModified(CommittedOrder orderToBeModified);
	
	void obtainNewTransactionWithSupplement(Element previousElement, Supplement chosenSupplement, int quantity);

	void confirmedExit();

	void sendFinalizedOrder();

	void confirmedClearOrder();
	
	void sendRequestForOrderByID(Long ID);
}
