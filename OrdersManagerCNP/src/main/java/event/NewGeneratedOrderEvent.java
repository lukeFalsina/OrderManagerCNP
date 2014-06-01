package event;

import java.util.EventObject;

import model.Order;

public class NewGeneratedOrderEvent extends EventObject {

	private static final long serialVersionUID = -3226430527021420880L;
	private Order newGeneratedOrder;
	
	public NewGeneratedOrderEvent(Object source, Order newGeneratedOrder) {
		super(source);
		this.newGeneratedOrder = newGeneratedOrder;		
	}

	public Order getNewGeneratedOrder() {
		return newGeneratedOrder;
	}
	
}
