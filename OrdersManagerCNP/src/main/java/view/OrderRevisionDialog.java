package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
//import javax.swing.JScrollBar;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import main.CommittedOrder;
import model.Drink;
import model.Food;
import model.Order;
import model.Supplement;
import model.Transaction;
import model.TransactionWithSupplement;

import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class OrderRevisionDialog extends JDialog {

	private static final long serialVersionUID = 7478275119966943320L;
	private final JPanel contentPanel = new JPanel();
	private CommittedOrder incomingOrder;
	private ReturnSwingView returnSwingView;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			
			Order temporarayOrder = new Order(new Transaction(new Food("Pasta al pesto", 5f, "", 0), 4));
			temporarayOrder.addFurtherTransaction(new TransactionWithSupplement(new Food("Pasta al rag�", 5f, "", 0), 2, new Supplement("Pi� Sugo", 1.0f, 0)));
			temporarayOrder.addFurtherTransaction(new TransactionWithSupplement(new Food("Pasta al pesto", 5f, "", 0), 2, new Supplement("Senza Formaggio", 0f, 0)));
			temporarayOrder.addFurtherTransaction(new Transaction(new Food("Costine", 3f, "", 0), 3));
			temporarayOrder.addFurtherTransaction(new Transaction(new Drink("Acqua", 2f, "", 0), 2));
			
			CommittedOrder tempCommitOrder = new CommittedOrder(temporarayOrder, 1425638255l, "23/12/2014 h 8:32");
			
			JFrame exampleFrame = new JFrame();
			exampleFrame.setBounds(0, 0, 700, 500);
			exampleFrame.setVisible(true);
			exampleFrame.setLocationRelativeTo(null);
			exampleFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			
			@SuppressWarnings("unused")
			OrderRevisionDialog dialog = new OrderRevisionDialog(exampleFrame, tempCommitOrder);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public OrderRevisionDialog(Frame frame, CommittedOrder incomingOrder) {
		
		super(frame, "Visualizzazione Ordine Salvato", true);
		this.incomingOrder = incomingOrder;
		this.returnSwingView = null;
		
		if(incomingOrder != null) initialize();
		else setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
		
	/**
	 * Create the dialog.
	 */
	public OrderRevisionDialog(Frame frame, CommittedOrder incomingOrder, ReturnSwingView returnSwingView) {
		
		super(frame, "Visualizzazione Ordine Salvato", true);
		this.incomingOrder = incomingOrder;
		this.returnSwingView = returnSwingView;
		
		if(incomingOrder != null) initialize();
		else setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	private void initialize() {
		
		setBounds(0, 0, 600, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		BorderLayout borderLayout = new BorderLayout();
		getContentPane().setLayout(borderLayout);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.NORTH);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JLabel lblOrderIDLabel = new JLabel("ID Ordine: " + incomingOrder.getOrderID());
				panel.add(lblOrderIDLabel, BorderLayout.NORTH);
				lblOrderIDLabel.setFont(new Font("Dialog", Font.BOLD, 20));
				lblOrderIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				JLabel lblOrderTimeLabel = new JLabel("Creato in data: " + incomingOrder.getCommitTime());
				panel.add(lblOrderTimeLabel, BorderLayout.SOUTH);
				lblOrderTimeLabel.setFont(new Font("Dialog", Font.ITALIC, 10));
				lblOrderTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
			}		
		}
/*		{
			JScrollBar scrollBar = new JScrollBar();
			contentPanel.add(scrollBar, BorderLayout.EAST);
		} */
		{
			JPanel transaction_panel = new JPanel();
			contentPanel.add(transaction_panel, BorderLayout.CENTER);
			transaction_panel.setLayout(new GridLayout(0, 2, 0, 0));
			{
				List<Transaction> transactions = incomingOrder.getAssociatedOrder().getTransactions();
				
				for (Transaction currentTransaction : transactions) {

					if (currentTransaction.getClass().equals(TransactionWithSupplement.class)) {
						// Transactions with Supplement
						TransactionWithSupplement currTransWithSupp = (TransactionWithSupplement) currentTransaction;
						
						JLabel lblNewLabelName = new JLabel(currTransWithSupp.getInvolvedElement().getName() + " " + currTransWithSupp.getSupplement().getName());
						lblNewLabelName.setHorizontalAlignment(SwingConstants.LEADING);
						transaction_panel.add(lblNewLabelName);
						
						JLabel lblNewLabelQuaCost;
						
						if (currTransWithSupp.getSupplement().getExtraCharge() == 0f) {
							// Supplement has not an additional cost.
							lblNewLabelQuaCost = new JLabel(currTransWithSupp.getQuantity() + " X " + currTransWithSupp.getInvolvedElement().getCost() + " €  ");
						}
						else {
							// Supplement has an additional cost.
							lblNewLabelQuaCost = new JLabel(currTransWithSupp.getQuantity() + " X (" + 
							currTransWithSupp.getInvolvedElement().getCost() + " + " +currTransWithSupp.getSupplement().getExtraCharge() + ") €  ");
						}
						
						lblNewLabelQuaCost.setHorizontalAlignment(SwingConstants.TRAILING);
						transaction_panel.add(lblNewLabelQuaCost);
						
					}
					else { 
						
						// Simple Transactions
						JLabel lblNewLabelName = new JLabel(currentTransaction.getInvolvedElement().getName());
						lblNewLabelName.setHorizontalAlignment(SwingConstants.LEADING);
						transaction_panel.add(lblNewLabelName);
						
						JLabel lblNewLabelQuaCost = new JLabel(currentTransaction.getQuantity() + " X " + currentTransaction.getInvolvedElement().getCost() + " €  ");
						lblNewLabelQuaCost.setHorizontalAlignment(SwingConstants.TRAILING);
						transaction_panel.add(lblNewLabelQuaCost);
					}
					
				}

			}
		}
		{
			JLabel lblOrderCostLabel = new JLabel("Costo Totale: " + incomingOrder.getAssociatedOrder().getTotalCost() + " €  ");
			lblOrderCostLabel.setFont(new Font("Dialog", Font.BOLD, 17));
			lblOrderCostLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			contentPanel.add(lblOrderCostLabel, BorderLayout.SOUTH);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton modifyButton = new JButton("Modifica Ordine");
//				modifyButton.setActionCommand("OK");
				modifyButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						
						if (returnSwingView != null)
							returnSwingView.retrieveOrderToBeModified(incomingOrder);
					}
				});
				buttonPane.add(modifyButton);
				getRootPane().setDefaultButton(modifyButton);
			}
			{
				JButton cancelButton = new JButton("Annulla");
//				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				buttonPane.add(cancelButton);
			}
		}
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
