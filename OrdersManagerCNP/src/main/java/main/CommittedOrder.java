package main;

import model.Order;

public class CommittedOrder {

	private Order AssociatedOrder;
	private long OrderID;
	private String commitTime;
	
	public CommittedOrder(Order associatedOrder, long orderID, String commitTime) {
		AssociatedOrder = associatedOrder;
		OrderID = orderID;
		this.commitTime = commitTime;
	}

	public Order getAssociatedOrder() {
		return AssociatedOrder;
	}

	public long getOrderID() {
		return OrderID;
	}

	public String getCommitTime() {
		return commitTime;
	}

}
