package print;

import main.CommittedOrder;
import model.Transaction;
import model.TransactionWithSupplement;

public class ThermalPrinter extends PrinterElement {

	public ThermalPrinter(String urlPath) {
		super(urlPath);
	}

	@Override
	public void printOrder(CommittedOrder incomingCommittedOrder) {
		//\\\\ALE-LAPTOP\\epsontm-l90
		PrintTextPage escp = new PrintTextPage(urlPath, false);
		if (escp.initialize()) {
			escp.printLogo((char)48, (char)48);
			escp.setCharacterSet(PrintTextPage.USA);
			escp.print(incomingCommittedOrder.getCommitTime());
			escp.advanceVertical(1);
			escp.print("ORDINE NUMERO: "+incomingCommittedOrder.getOrderID());
			escp.advanceVertical(1);
			for(Transaction checkedTransaction : incomingCommittedOrder.getAssociatedOrder().getTransactions()) {
				if(checkedTransaction.getClass().equals(Transaction.class)){
					escp.print(String.format(" %2d %-36s %6.2f ", checkedTransaction.getQuantity(),
							checkedTransaction.getInvolvedElement().getName(), checkedTransaction.getTransactionCost()));
					escp.advanceVertical(1);
				}
					
				if(checkedTransaction.getClass().equals(TransactionWithSupplement.class)) {
					TransactionWithSupplement transaction = (TransactionWithSupplement) checkedTransaction;
					escp.print(String.format(" %2d %-36s        ",checkedTransaction.getQuantity(),
							 checkedTransaction.getInvolvedElement().getName()));
					escp.advanceVertical(1);
					escp.print(String.format("    %-36s %6.2f ",transaction.getSupplement().getName(), 
							checkedTransaction.getTransactionCost()));
					escp.advanceVertical(1);
				}
			}
			escp.print(String.format("    %-34s %8.2f ","TOTALE:",incomingCommittedOrder.getAssociatedOrder().getTotalCost()));
			escp.advanceVertical(1);
			escp.advanceVertical(1);
			escp.print("------------------------------------------------");
			escp.advanceVertical(1);
			escp.print("--              NUMERO   TAVOLO               --");
			escp.advanceVertical(1);
			escp.print("------------------------------------------------");
			escp.advanceVertical(1);
			escp.print("--                                            --");
			escp.advanceVertical(1);
			escp.print("--                                            --");
			escp.advanceVertical(1);
			escp.print("--                                            --");
			escp.advanceVertical(1);
			escp.print("------------------------------------------------");
			escp.cut();
			escp.close(); //close stream
			} else
				System.out.println("Couldn't open stream to printer");
	}

}
