package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JFrame;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.JRadioButton;

import model.Element;
import model.Food;
import model.Supplement;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ChooseSupplementDialog extends JDialog {

	private static final long serialVersionUID = -3313154175228251187L;

	private final JPanel contentPanel = new JPanel();
	private Element incomingElement;
	private int radioChoice;
	private List<Supplement> possibleSupplementList;
	private JSpinner quantitySpinner;
	private ReturnSwingView returnSwingView;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			
			List<Supplement> possibleSupplementList = new ArrayList<Supplement>();
			possibleSupplementList.add(new Supplement("Senza formaggio", 0f, 0));
			possibleSupplementList.add(new Supplement("Pi� sugo", 1.5f, 0));
			possibleSupplementList.add(new Supplement("Pane incluso", 1f, 0));
			
			JFrame exampleFrame = new JFrame();
			exampleFrame.setBounds(0, 0, 700, 500);
			exampleFrame.setVisible(true);
			exampleFrame.setLocationRelativeTo(null);
			exampleFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			
			@SuppressWarnings("unused")
			ChooseSupplementDialog dialog = new ChooseSupplementDialog(exampleFrame, new Food("Pasta al rag�", 5f, "", 0), possibleSupplementList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ChooseSupplementDialog(	Frame frame,
									ReturnSwingView returnSwingView,
									List<Supplement> possibleSupplementList) {
		
		super(frame, "Scelta Variazione Elemento", true);
		
		this.returnSwingView = returnSwingView;
//		this.incomingElement = incomingElement;
		this.possibleSupplementList = possibleSupplementList;
		radioChoice = 0;
		
		if(possibleSupplementList == null || possibleSupplementList.size() < 1) {
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			
			@SuppressWarnings("unused")
			MessageStateDialog errorDialog = new MessageStateDialog(this, true, "Errore! Impossibile caricare le possibili variazioni.");
		}
//		else {
//			initialize();
//		}
	}
	
	/**
	 * @param incomingElement the incomingElement to set
	 */
	public void setIncomingElement(Element incomingElement) {
		this.incomingElement = incomingElement;
	}

	/**
	 * Create the dialog.
	 */
	public ChooseSupplementDialog(	Frame frame,
									Element incomingElement, 
									List<Supplement> possibleSupplementList) {
		
		super(frame, "Scelta Variazione Elemento", true);
		
		this.returnSwingView = null;
		this.incomingElement = incomingElement;
		this.possibleSupplementList = possibleSupplementList;
		radioChoice = 0;
		
		if(incomingElement == null || possibleSupplementList == null || possibleSupplementList.size() < 1) {
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			
			@SuppressWarnings("unused")
			MessageStateDialog errorDialog = new MessageStateDialog(this, true, "Errore! Impossibile caricare le possibili variazioni.");
		}
		else {
			initialize();
		}
	}
		
	public void initialize() {

		setBounds(0, 0, 600, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.NORTH);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			{
				JLabel lblElementNameLabel = new JLabel("Elemento selezionato: " + incomingElement.getName());
				lblElementNameLabel.setFont(new Font("Dialog", Font.BOLD, 18));
				panel.add(lblElementNameLabel);
				lblElementNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				JLabel lblInstructionLabel = new JLabel("Si selezioni una possibile variazione all'elemento scelto fra le sottostanti:");
				lblInstructionLabel.setFont(new Font("Dialog", Font.ITALIC, 11));
				panel.add(lblInstructionLabel);
				lblInstructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.SOUTH);
			panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JLabel lblTextExplanationLabel = new JLabel("Inserire la quantità per l'elemento con variazione:");
				lblTextExplanationLabel.setHorizontalAlignment(SwingConstants.TRAILING);
				lblTextExplanationLabel.setFont(new Font("Dialog", Font.ITALIC, 11));
				panel.add(lblTextExplanationLabel);
			}
			{				
				SpinnerModel quantityModel = new SpinnerNumberModel(	1,	//initial value
				                               					1,	//min
				                               					20, //max
				                               					1);	//step
				
				quantitySpinner = new JSpinner(quantityModel);
				panel.add(quantitySpinner);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new GridLayout(0, 2));
			
			ButtonGroup group = new ButtonGroup();
			
			for(int buttonIndex = 0; buttonIndex < possibleSupplementList.size(); buttonIndex++) {
				
				Supplement currSupplement = possibleSupplementList.get(buttonIndex);
				
				//Create the radio button
				JRadioButton newButton = new JRadioButton(currSupplement.getName() + " ( +" + currSupplement.getExtraCharge() + " € )");
			    newButton.setActionCommand(String.valueOf(buttonIndex));
			    
			    //The first button is selected as the default one.
			    if(buttonIndex == 0) newButton.setSelected(true);
			    
			    //Group the radio buttons.
			    group.add(newButton);
			    
			    //Register a listener for the radio buttons.
				newButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						radioChoice = Integer.parseInt(e.getActionCommand());
					}
				});
				
				//Attach the radio button to the panel
				panel.add(newButton);
			}
		}
		{
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPanel, BorderLayout.SOUTH);
			{
				JButton addButton = new JButton("Aggiungi Elemento");
				addButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						
						try {
							quantitySpinner.commitEdit();
						}
						catch (ParseException pe) {
							// Edited value is invalid, spinner.getValue() will return
							// the last valid value, you could revert the spinner to show that:
							JComponent editor = quantitySpinner.getEditor();
							if (editor instanceof DefaultEditor) {
								((DefaultEditor)editor).getTextField().setValue(quantitySpinner.getValue());
							};
							// reset the value to some known value:
							quantitySpinner.setValue(1);
							// or treat the last valid value as the current, in which
							// case you don't need to do anything.
						};
						
						// Chiamata al metodo della superclasse tramite radioChoice e Spinner.getValue()
						if (returnSwingView != null)
							returnSwingView.obtainNewTransactionWithSupplement(	incomingElement, 
																				possibleSupplementList.get(radioChoice), 
																				(Integer) quantitySpinner.getValue());
						else
							System.out.println(	incomingElement.toString() +"; " + possibleSupplementList.get(radioChoice).getName() + "; " 
												+ (Integer) quantitySpinner.getValue());
					}
				});
				buttonPanel.add(addButton);
				getRootPane().setDefaultButton(addButton);
			}
			{
				JButton cancelButton = new JButton("Annulla");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				buttonPanel.add(cancelButton);
			}
		}
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
