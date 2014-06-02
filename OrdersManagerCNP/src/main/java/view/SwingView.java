package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import java.util.ArrayList;
import java.util.List;

import event.AskForOrderEvent;
import event.NewGeneratedOrderEvent;
import event.ReceivedOperationStateEvent;
import event.ReceivedOrderEvent;

import main.CommittedOrder;
import main.ControllerListener;
import model.Element;
import model.Food;
//import model.Food;
import model.Order;
import model.Supplement;
import model.Transaction;
import model.TransactionWithSupplement;

public class SwingView extends AbstractView implements ReturnSwingView {

	private static JFrame frame;
	private JPanel contentPane;
	
	//private List<JButton> removeButtons;
	
	// This element is kept aside because it's the parent panel of the dynamic
	// elements generated by the private method createEntriesForElementList();
	private JPanel elementListPanel;
	
	// Also this panel must be visible everywhere inside the class since its 
	// descendant middlePanel must be kept updated and link to this panel.
	private JPanel actualOrderPanel;
	
	private Order editableOrder;
	
	private ExitDialog exitDialog;
	private FinalizeDialog finalizeDialog;
	private CleanDialog cleanDialog;
	
	private ChooseSupplementDialog chooseSupplementDialog;
	private SearchWithIDDialog searchWithIdDialog;
	
	public SwingView(ControllerListener controllerListener, List<Element> elementList) {
		super(controllerListener, elementList);
		
		// In the initial situation the editable order is empty..
		editableOrder = null;
		
		createPanels();
		
		// Here the main frame is launched..
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					inializeMainFrame();
					frame.setVisible(true);
										
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SwingView(ControllerListener controllerListener, List<Element> elementList, List<Supplement> supplementList) {
		super(controllerListener, elementList);
		enableSupplementUse = true;
		this.supplementList = supplementList;
/*		this.supplementList = new ArrayList<Supplement>();
		this.supplementList.add(new Supplement("Pi� Salse", 0.3f, 0l));
		this.supplementList.add(new Supplement("Pi� Condimento", 0.5f, 0l));
		this.supplementList.add(new Supplement("Senza formaggio", 0f, 0l));
*/		
		// In the initial situation the editable order is empty..
		editableOrder = null;
		//editableOrder = new Order(new TransactionWithSupplement((Food) this.elementList.get(0), 3, this.supplementList.get(0)));
		
		//removeButtons = new ArrayList<JButton>();
		
		createPanels();

		// Here the main frame is launched..
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					inializeMainFrame();
					frame.setVisible(true);
										
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}
	
	private void createPanels() {
		
		frame = new JFrame();
		
		exitDialog = new ExitDialog(frame, this);
		finalizeDialog = new FinalizeDialog(frame, this);
		cleanDialog = new CleanDialog(frame, this);
		
		if(enableSupplementUse) {
			chooseSupplementDialog = new ChooseSupplementDialog(frame, this, supplementList);
		}
		searchWithIdDialog = new SearchWithIDDialog(frame, this);
		
	}
	
	@Override
	public void receiveOrderState(
			ReceivedOperationStateEvent receivedOperationStateEvent) {
		
		if (receivedOperationStateEvent.isOperationSuccessfull()) {
			Long orderIndex = receivedOperationStateEvent.getIDSuccessfullOrder();
			@SuppressWarnings("unused")
			MessageStateDialog message = new MessageStateDialog(frame, false, "Ordine con ID "+ orderIndex + " salvato con successo");
		
			// A new order is started
			editableOrder = null;
			updateMiddlePanelAsTransactionsList();
			
			actualOrderPanel.revalidate();
			
		} else {
			@SuppressWarnings("unused")
			MessageStateDialog message = new MessageStateDialog(frame, true, "Impossibile salvare l'ordine! Riprovare a finalizzarlo.");
		}
		
	}

	@Override
	public void receivePreviouslyStoredOrder(
			ReceivedOrderEvent receiveOrderEvent) {
		if(receiveOrderEvent.isResearchSuccessfull()) {
			// an order was found so the pop up for revision is opened..
			@SuppressWarnings("unused")
			OrderRevisionDialog orderRevision = new OrderRevisionDialog(frame, receiveOrderEvent.getFoundCommittedOrder(), this);
		}
		else {
			// no order was found..
			@SuppressWarnings("unused")
			MessageStateDialog message = new MessageStateDialog(frame, true, "Non è stato trovato nessun ordine con l'ID scelto!");
		}
		
	}

	@Override
	public void retrieveOrderToBeModified(CommittedOrder orderToBeModified) {
		editableOrder = orderToBeModified.getAssociatedOrder();
		
		@SuppressWarnings("unused")
		MessageStateDialog message = new MessageStateDialog(frame, false, "L'ordine è stato importato con successo!");
		
		updateMiddlePanelAsTransactionsList();
		actualOrderPanel.revalidate();
		
	}

	@Override
	public void obtainNewTransactionWithSupplement(Element previousElement,
			Supplement chosenSupplement, int quantity) {
		// A new transaction was added so all the frame must be repainted
		if(editableOrder != null)
			editableOrder.addFurtherTransaction(new TransactionWithSupplement((Food) previousElement, quantity, chosenSupplement));
		else
			editableOrder = new Order(new TransactionWithSupplement((Food) previousElement, quantity, chosenSupplement));
			
		updateMiddlePanelAsTransactionsList();
		actualOrderPanel.revalidate();
	}
	
	@Override
	public void sendFinalizedOrder() {
		controllerListener.receiveNewOrder(new NewGeneratedOrderEvent(this, editableOrder));
		
	}
	
	@Override
	public void confirmedExit() {
//		frame.setVisible(false);
		frame.dispose();
		frame = null;
		System.exit(0);
	}
	
	@Override
	public void confirmedClearOrder() {
		
		// The old order is thrown away so the view must be repainted
		editableOrder = null;
		updateMiddlePanelAsTransactionsList();
		
		actualOrderPanel.revalidate();
		
	}
	
	@Override
	public void sendRequestForOrderByID(Long orderID) {
		controllerListener.askForSpecificOrder(new AskForOrderEvent(this, orderID));
		
	}

	private void inializeMainFrame() {
		
		// if the elementList is null or empty an error message is visualized..
		if		(elementList == null || elementList.size() < 1 || 
				(enableSupplementUse && (supplementList == null || supplementList.size() < 1))) {
			
			@SuppressWarnings("unused")
			MessageStateDialog errorDialog = new MessageStateDialog(frame, true, "Errore! Impossibile caricare correttamente i dati dal database.");
			
			frame.dispose();
			frame = null;
		}
		else {
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(0, 0, 800, 600);
		frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
//		frame.setResizable(false);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);
		
		JPanel titlePanel = new JPanel();
		contentPane.add(titlePanel, BorderLayout.NORTH);
		
		JLabel lblTitleLabel = new JLabel("Gestore prenotazione ordini Codeghì 'N del Pà - Giugno 2014");
		titlePanel.add(lblTitleLabel);
		lblTitleLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		lblTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JSplitPane splitPanel = new JSplitPane();
		splitPanel.setResizeWeight(0.0);
		contentPane.add(splitPanel, BorderLayout.CENTER);
		
		elementListPanel = new JPanel();
		splitPanel.setLeftComponent(elementListPanel);
		// Number of rows must be equal to the number of actual element + 1 (used for title panel..)
		elementListPanel.setLayout(new GridLayout(elementList.size() + 1, 1, 0, 0));
		
		JPanel subtitlePanel = new JPanel();
		elementListPanel.add(subtitlePanel);
		
		JLabel titleElementListlabel = new JLabel("Lista degli elementi fra cui scegliere:");
		subtitlePanel.add(titleElementListlabel);
		titleElementListlabel.setFont(new Font("Dialog", Font.ITALIC, 12));
		titleElementListlabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Ripetere in ciclo for per ogni elemento presente da qui..
		createEntriesForElementList();
		
		actualOrderPanel = new JPanel();
		splitPanel.setRightComponent(actualOrderPanel);
		actualOrderPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel sumUpTitlePanel = new JPanel();
		actualOrderPanel.add(sumUpTitlePanel, BorderLayout.NORTH);
		
		JLabel label = new JLabel("Riassunto ordine corrente:");
		sumUpTitlePanel.add(label);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Dialog", Font.ITALIC, 17));
		
		// Da qui AGGIORNAMENTO MIDDLE PANEL TRANSAZIONI
		// This method will be used to keep updated the right side of the main frame
		// element, which represents the current state of the order which is now being built.
		updateMiddlePanelAsTransactionsList();
		
		JPanel controlOrderPanel = new JPanel();
		actualOrderPanel.add(controlOrderPanel, BorderLayout.SOUTH);
				
		JButton btnCancelOrderButton = new JButton("Cancella ordine corrente..");
		btnCancelOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Rilancio a finestra di conferma.. poi settare editableOrder = null.
				if(editableOrder != null) {
					cleanDialog.initiailze("Cliccando su Ok l'intero ordine sarà cancellato! Desideri continuare?");
					cleanDialog.setOKButton();
				}
			}
		});
		controlOrderPanel.add(btnCancelOrderButton);
				
		JButton btnFinalizeButton = new JButton("Finalizza ordine corrente..");
		btnFinalizeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Rilancio a finestra di conferma.. poi richiamo evento per mandare evento al 
				// controller e poi bisognerebbe bloccare gli input.
				if(editableOrder != null ) {
					finalizeDialog.initiailze("Vuoi davvero confermare questo ordine?");
					finalizeDialog.setOKButton();
				}
			}
		});
		controlOrderPanel.add(btnFinalizeButton);
		
		JPanel generalButtonPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) generalButtonPanel.getLayout();
		flowLayout.setVgap(20);
		flowLayout.setAlignment(FlowLayout.TRAILING);
		contentPane.add(generalButtonPanel, BorderLayout.SOUTH);
		
		JButton btnSearchButton = new JButton("Ricerca altro ordine per ID..");
		btnSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Rilancio a finestra di conferma.. poi richiamare finestra per inserimento ID.
				searchWithIdDialog.initiailze("Inserendo un ID di un ordine, leggibile dallo scontrino stampato, è possibile recuperare modificare un'ordine già finalizzato.");
				searchWithIdDialog.setOKButton();
			}
		});
//		generalButtonPanel.add(btnSearchButton);
		
		JButton btnExitButton = new JButton("Abbandona..");
		btnExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				exitDialog.initiailze("Vuoi davvero uscire?");
				exitDialog.setOKButton();
			}
		});
		
		generalButtonPanel.add(btnExitButton);
		
		}
	}

	// This methods initializes all the elements(text + buttons that will be shown into the 
	// left part of the interface) by looking at the objects which are represented into elementList.
	private void createEntriesForElementList() {
		
		for(int elementIndex = 0; elementIndex < elementList.size(); elementIndex++) {
			
			JPanel singleEntryPanel = new JPanel();
			singleEntryPanel.setBackground(Color.ORANGE);
			FlowLayout fl_singleEntryPanel = (FlowLayout) singleEntryPanel.getLayout();
			fl_singleEntryPanel.setVgap(0);
			fl_singleEntryPanel.setHgap(0);
			singleEntryPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
			elementListPanel.add(singleEntryPanel);
		
			JSplitPane oneButtonSplitPanel = new JSplitPane();
			oneButtonSplitPanel.setBackground(Color.ORANGE);
			singleEntryPanel.add(oneButtonSplitPanel);
			oneButtonSplitPanel.setResizeWeight(1.0);
		
			JPanel elementButtonPanel = new JPanel();
			elementButtonPanel.setBackground(Color.ORANGE);
			oneButtonSplitPanel.setRightComponent(elementButtonPanel);
			elementButtonPanel.setLayout(new BoxLayout(elementButtonPanel, BoxLayout.Y_AXIS));
			
			JButton btnAddNormalButton = new JButton("Aggiungi..");
			btnAddNormalButton.setActionCommand(String.valueOf(elementIndex));
			btnAddNormalButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					// Retrieve the correct element associated to the button
					int correctElementIndex = Integer.parseInt(e.getActionCommand());
					
					// Create a new transaction and associate it to the object editableOrder.
					if(editableOrder == null) 
						editableOrder = new Order(new Transaction(elementList.get(correctElementIndex), 1));
					else
						editableOrder.addFurtherTransaction(new Transaction(elementList.get(correctElementIndex), 1));
					
					// Redraw the middlePanel..
					updateMiddlePanelAsTransactionsList();
					
					actualOrderPanel.revalidate();
					
				}
			});
			btnAddNormalButton.setHorizontalAlignment(SwingConstants.TRAILING);
			elementButtonPanel.add(btnAddNormalButton);
		
			if (enableSupplementUse) {
				// We have to create this special button if and only if the Supplement use is allowed..
				JButton btnSpecialButton = new JButton("Variazione..");
				btnSpecialButton.setActionCommand(String.valueOf(elementIndex));
				btnSpecialButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						// Retrieve the correct element associated to the button
						int correctElementIndex = Integer.parseInt(e.getActionCommand());
						
						// Generate a new ChooseSupplementDialog window to handle this request.
					//	@SuppressWarnings("unused")
						chooseSupplementDialog.setIncomingElement(elementList.get(correctElementIndex));
						chooseSupplementDialog.initialize();
					//	ChooseSupplementDialog dialog = new ChooseSupplementDialog(frame, e.getSource(), elementList.get(correctElementIndex), supplementList);
					}
				});
				btnSpecialButton.setHorizontalAlignment(SwingConstants.TRAILING);
				elementButtonPanel.add(btnSpecialButton);
			}
		
			JPanel elementDetailspanel = new JPanel();
			elementDetailspanel.setBackground(Color.ORANGE);
			oneButtonSplitPanel.setLeftComponent(elementDetailspanel);
			elementDetailspanel.setLayout(new BoxLayout(elementDetailspanel, BoxLayout.Y_AXIS));
		
			JLabel lblElementNameLabel = new JLabel(elementList.get(elementIndex).getName());
			elementDetailspanel.add(lblElementNameLabel);
		
//			JLabel lblElementDescrLabel = new JLabel(elementList.get(elementIndex).getDescription());
//			elementDetailspanel.add(lblElementDescrLabel);
		}
	}
	
	// This method allows to keep the right side of the view updated. In fact every time than the
	// object editableOrder is modified, this method will be invoked to repaint the middlePanel.
	private void updateMiddlePanelAsTransactionsList() {
		
		JPanel middlePanel = new JPanel();
		actualOrderPanel.add(middlePanel, BorderLayout.CENTER);
		
		// if the order is null, nothing should be printed..
		if (editableOrder != null) {
			
	//		middlePanel.setLayout(new BorderLayout(0, 0));
			middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
		
			JPanel orderTrackPanel = new JPanel();
	//		middlePanel.add(orderTrackPanel, BorderLayout.CENTER);
			middlePanel.add(orderTrackPanel);
			orderTrackPanel.setLayout(new GridLayout(editableOrder.getTransactions().size(), 1, 0, 0));
			
			// I will create a number of line which is equal to the number of transitions 
			// inside the current order..
			for(int buttonIndex = 0; buttonIndex < editableOrder.getTransactions().size(); buttonIndex++) {
				
				JPanel linePanel = new JPanel();
				orderTrackPanel.add(linePanel);
				linePanel.setLayout(new BorderLayout(0, 0));
			
				JPanel buttonLinePanel = new JPanel();
				linePanel.add(buttonLinePanel, BorderLayout.WEST);
				buttonLinePanel.setLayout(new BoxLayout(buttonLinePanel, BoxLayout.X_AXIS));
		
				JButton btnRemoveLineButton = new JButton("Rimuovi linea..");
				btnRemoveLineButton.setActionCommand(String.valueOf(buttonIndex));
				btnRemoveLineButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						// Retrieve the correct element associated to the button
						int correctElementIndex = Integer.parseInt(e.getActionCommand());
						Transaction transactionToBeRemoved = editableOrder.getTransactions().get(correctElementIndex);
						
						// Remove a new transaction and associate it to the object editableOrder.						
						editableOrder = editableOrder.removeSpecificTransaction(transactionToBeRemoved);
						
						// Redraw the middlePanel..
						updateMiddlePanelAsTransactionsList();
						
						actualOrderPanel.revalidate();
						
//						for(JButton removeButton : removeButtons) {
//							removeButton.revalidate();
//						}
					}
				});
//				removeButtons.add(btnRemoveLineButton);
				buttonLinePanel.add(btnRemoveLineButton);
		
				JPanel transactionPanel = new JPanel();
				linePanel.add(transactionPanel, BorderLayout.CENTER);
				transactionPanel.setLayout(new GridLayout(1, 2, 0, 0));
		
				// I will store the temporary transaction
				Transaction currentTransaction = editableOrder.getTransactions().get(buttonIndex);
				
				if (currentTransaction.getClass().equals(TransactionWithSupplement.class)) {
					// Transactions with Supplement
					TransactionWithSupplement currTransWithSupp = (TransactionWithSupplement) currentTransaction;
					
					JLabel lblNameTransLabel = new JLabel(currTransWithSupp.getInvolvedElement().getName() + " " + currTransWithSupp.getSupplement().getName());
					lblNameTransLabel.setHorizontalAlignment(SwingConstants.LEADING);
					transactionPanel.add(lblNameTransLabel);
					
					JLabel lblQuaCostLabel;
					
					if (currTransWithSupp.getSupplement().getExtraCharge() == 0f) {
						// Supplement has not an additional cost.
						lblQuaCostLabel = new JLabel(currTransWithSupp.getQuantity() + " X " + currTransWithSupp.getInvolvedElement().getCost() + " €  ");
					}
					else {
						// Supplement has an additional cost.
						lblQuaCostLabel = new JLabel(currTransWithSupp.getQuantity() + " X (" + 
						currTransWithSupp.getInvolvedElement().getCost() + " + " +currTransWithSupp.getSupplement().getExtraCharge() + ") €  ");
					}
					
					lblQuaCostLabel.setHorizontalAlignment(SwingConstants.TRAILING);
					transactionPanel.add(lblQuaCostLabel);
					
				}
				else { 
					
					// Simple Transactions
					JLabel lblNameTransLabel = new JLabel(currentTransaction.getInvolvedElement().getName());
					lblNameTransLabel.setHorizontalAlignment(SwingConstants.LEADING);
					transactionPanel.add(lblNameTransLabel);
					
					JLabel lblQuaCostLabel = new JLabel(currentTransaction.getQuantity() + " X " + currentTransaction.getInvolvedElement().getCost() + " €  ");
					lblQuaCostLabel.setHorizontalAlignment(SwingConstants.TRAILING);
					transactionPanel.add(lblQuaCostLabel);
				}
						
			}
		
			JPanel totalPanel = new JPanel();
	//		middlePanel.add(totalPanel, BorderLayout.SOUTH);
			
			// Padding a caso...
			for (int paddingRow = 0; paddingRow < 5; paddingRow++) {
				middlePanel.add(new JPanel());
			}
			
			totalPanel.setLayout(new BorderLayout(0, 15));
			middlePanel.add(totalPanel);
			
			JLabel lblTotalOrderLabel = new JLabel("Totale: " + editableOrder.getTotalCost() + " €  ");
			lblTotalOrderLabel.setFont(new Font("Dialog", Font.BOLD, 18));
			lblTotalOrderLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			totalPanel.add(lblTotalOrderLabel, BorderLayout.NORTH);
		}
	}

}