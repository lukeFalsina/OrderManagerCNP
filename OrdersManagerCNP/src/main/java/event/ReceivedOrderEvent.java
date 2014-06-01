package event;

import java.util.EventObject;

import main.CommittedOrder;

public class ReceivedOrderEvent extends EventObject {

	private static final long serialVersionUID = 6946357180582546232L;
	private boolean researchSuccessful;
	private CommittedOrder foundCommittedOrder;
	
	public ReceivedOrderEvent(Object source, boolean researchSuccessful, CommittedOrder foundCommittedOrder) {
		super(source);
		this.researchSuccessful = researchSuccessful;
		this.foundCommittedOrder = foundCommittedOrder;
	}

	public CommittedOrder getFoundCommittedOrder() {
		return foundCommittedOrder;
	}

	public boolean isResearchSuccessfull() {
		return researchSuccessful;
	}

}
