package main;

import java.util.EventListener;

import event.ReceivedOperationStateEvent;
import event.ReceivedOrderEvent;

public interface ViewListener extends EventListener {

	void receiveOrderState(ReceivedOperationStateEvent receivedOperationStateEvent);
	
	void receivePreviouslyStoredOrder(ReceivedOrderEvent receiveOrderEvent);
	
}
