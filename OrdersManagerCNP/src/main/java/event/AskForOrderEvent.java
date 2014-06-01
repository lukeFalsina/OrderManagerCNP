package event;

import java.util.EventObject;

public class AskForOrderEvent extends EventObject {

	private static final long serialVersionUID = -4137701785152333737L;
	private long orderID;
	
	public AskForOrderEvent(Object source, long orderID) {
		super(source);
		this.orderID = orderID;
	}

	public long getOrderID() {
		return orderID;
	}
	
}
