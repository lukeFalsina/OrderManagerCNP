package print;

import main.CommittedOrder;

public abstract class PrinterElement {

	protected String urlPath;
	
	public PrinterElement(String urlPath) {
		this.urlPath = urlPath;
	}
	
	public abstract void printOrder(CommittedOrder incomingCommittedOrder);
}
