package view;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FinalizeDialog extends ConfirmationDialog {

	private static final long serialVersionUID = -3215893639842856492L;
	
	protected final JPanel reminderPanel = new JPanel();
	protected JCheckBox reminderCheckBox;
	protected JTextField customerMoneyText;
	private JLabel totalAmountLabel, customerMoneyLabel;
	protected JLabel reminderLabel;
	protected Float totalCost;
	protected JButton reminderButton;
	private boolean firstTimeInit;

	public FinalizeDialog(Frame frame, ReturnSwingView returnSwingView) {
		super(frame, returnSwingView, false);
		firstTimeInit = true;
	}

	@Override
	protected void setOKButton() {
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				returnSwingView.sendFinalizedOrder();
			}
		});
		
	}

	/**
	 * Method used to initialize the reminder panel by adding on top
	 * the functionality to handle the reminder computation.
	 * 
	 * @param textToBeVisualized
	 */
	public void initializeWithReminder(String textToBeVisualized) {
		
		initialize(textToBeVisualized);
		
		if (firstTimeInit) {
			
			firstTimeInit = false;
	
			// A check box is added to (dis)enable the remainder functionality.
			reminderCheckBox = new JCheckBox("Attiva funzione resto", false);
				
			//getContentPane().add(reminderCheckBox, BorderLayout.CENTER);
			JPanel chkboxPanel = new JPanel();
			chkboxPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			chkboxPanel.add(reminderCheckBox);
			envCentralPanel.add(chkboxPanel);
		
			// The special panel for the remainder functionality and its subcomponents are drawn..
			envCentralPanel.add(reminderPanel);
			reminderPanel.setLayout(new BoxLayout(reminderPanel, BoxLayout.Y_AXIS));
			reminderPanel.setVisible(false);
			
			customerMoneyLabel = new JLabel("Versato dal cliente: ");
			customerMoneyText = new JTextField("0.0");
			customerMoneyText.setColumns(8);
		
			JPanel customerMoneyPanel = new JPanel();
			customerMoneyPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			customerMoneyPanel.add(customerMoneyLabel);
			customerMoneyPanel.add(customerMoneyText);
			reminderPanel.add(customerMoneyPanel);
		
			JPanel totalAmountPanel = new JPanel();
			totalAmountPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			totalAmountLabel = new JLabel("Totale dovuto: "); //+ String.valueOf(totalCost));
			totalAmountPanel.add(totalAmountLabel);
			reminderPanel.add(totalAmountPanel);
		
			JPanel dashLinePanel = new JPanel();
			dashLinePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			dashLinePanel.add(new JLabel("______________________________________"));
			reminderPanel.add(dashLinePanel);
		
			reminderButton = new JButton("Calcola Resto");
			reminderLabel = new JLabel("Resto: -------");
		
			JPanel finalSubPanel = new JPanel();
			finalSubPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			finalSubPanel.add(reminderButton);
			finalSubPanel.add(reminderLabel);
			reminderPanel.add(finalSubPanel);
		
		}
	}
	
	/**
	 * This method is used to update the total cost of the order
	 * accordingly to the current state of the revision board.
	 * 
	 * @param totalCost
	 */
	public void updateRemainder(float totalCost) {
	
		this.totalCost = Float.valueOf(totalCost);
		
		reminderCheckBox.setSelected(false);
		reminderCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				 	if (e.getStateChange()==1) {
				 		reminderPanel.setVisible(true);
				 	}
				 	else {
				 		reminderPanel.setVisible(false);
				 	}
			}           
		});
		
		totalAmountLabel.setText("Totale dovuto: " + String.valueOf(this.totalCost));
		customerMoneyText.setText("0.0");
		reminderLabel.setText("Resto: -------");
		
		reminderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
				
					if (reminderCheckBox.isSelected()) {
						
						// This snippet of code check if the provided amount of money
						// is greater then the required one and in such a case it 
						// eventually compute the reminder.
						float customerMoney = Float.valueOf(customerMoneyText.getText());
						float reminder = customerMoney - FinalizeDialog.this.totalCost;
						
						if (reminder >= 0) {
							//reminder = (float) (Math.floor(reminder * 1e2) / 1e2);
							
							int totalCost2=(int)(reminder*100);
							if (totalCost2%10>5)
								reminder = ((float)(totalCost2-totalCost2%10+10))/100;
							else
								reminder = ((float)totalCost2)/100;
							
							reminderLabel.setText("Resto: " + String.valueOf(reminder));
							}
						else
							reminderLabel.setText("Resto: -------");
					}
				}
				catch (NumberFormatException exception) {
					
					// If the user provides something which is not a valid
					// float, the program flow arrives here..
					reminderLabel.setText("Resto: -------");
				}
				
			}
		});
		
	}

}
