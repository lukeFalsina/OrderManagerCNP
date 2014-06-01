package main;

import print.PrinterElement;
import print.ThermalPrinter;
import view.SwingView;
import event.AskForOrderEvent;
import event.NewGeneratedOrderEvent;
import event.ReceivedOperationStateEvent;
import event.ReceivedOrderEvent;

public class Manager implements ControllerListener {

	private DataBaseHandler dBManager;
	private ViewListener associatedView;
	private PrinterElement printingDevice;
	
	
	public Manager(DataBaseHandler dBManager, String modeChoice, String PrinterPath) {
		this.dBManager = dBManager;
		
		switch(modeChoice) {
		
			case "FOOD":
				associatedView = new SwingView(	this, this.dBManager.getCurrentElementList("FOOD"), 
												this.dBManager.getCurrentSupplementList());
				break;
		
			case "DRINK":
				associatedView = new SwingView(	this, this.dBManager.getCurrentElementList("DRINK"));
				break;
			
			default:
				System.out.println("An invalid choice was taken!");
		}
		
		printingDevice = new ThermalPrinter(PrinterPath);
	}
	
	@Override
	public void receiveNewOrder(NewGeneratedOrderEvent newGeneratedOrderEvent) {
		
		CommittedOrder justStoredOrder = dBManager.saveOrder(newGeneratedOrderEvent.getNewGeneratedOrder());
		
		if(justStoredOrder != null) {
			// The new order was successfully saved in the DB..
			// So now it will be printed..
			printingDevice.printOrder(justStoredOrder);
			
			// And a notification of successful operation will be sent.
			associatedView.receiveOrderState(new ReceivedOperationStateEvent(this, true, justStoredOrder.getOrderID()));
		}
		else {
			// An error notification will be sent.
			associatedView.receiveOrderState(new ReceivedOperationStateEvent(this, false, 0));
		}
			
		
	}
	
	@Override
	public void askForSpecificOrder(AskForOrderEvent askForOrderEvent) {
		
		CommittedOrder foundCommittedOrder = dBManager.searchOrderByID(askForOrderEvent.getOrderID());
		
		if(foundCommittedOrder != null)
			associatedView.receivePreviouslyStoredOrder(new ReceivedOrderEvent(this, true, foundCommittedOrder));
		else
			associatedView.receivePreviouslyStoredOrder(new ReceivedOrderEvent(this, false, null));
		
	}

}
