package event;

import java.util.EventObject;

public class ReceivedOperationStateEvent extends EventObject {

	private static final long serialVersionUID = 1326105287705439460L;
	private boolean operationSuccessful;
	private long IDSuccessfulOrder;
	
	public ReceivedOperationStateEvent(Object source, boolean operationSuccessful, long IDSuccessfulOrder) {
		super(source);
		this.operationSuccessful = operationSuccessful;
		this.IDSuccessfulOrder = IDSuccessfulOrder;
	}

	public boolean isOperationSuccessfull() {
		return operationSuccessful;
	}

	public long getIDSuccessfullOrder() {
		return IDSuccessfulOrder;
	}

}
