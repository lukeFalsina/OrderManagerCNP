package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class ChooseSupplementTemplate extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private String radioChoice;
	private JSpinner quantitySpinner;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ChooseSupplementTemplate dialog = new ChooseSupplementTemplate();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ChooseSupplementTemplate() {
		radioChoice = "Bird";
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.NORTH);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			{
				JLabel lblElementNameLabel = new JLabel("Elemento selezionato: ");
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
			//Create the radio buttons
			JRadioButton birdButton = new JRadioButton("Bird");
		    birdButton.setActionCommand("Bird");
		    birdButton.setSelected(true);
			
			JRadioButton pigButton = new JRadioButton("Pig");
		    pigButton.setActionCommand("Pig");
			
			//Group the radio buttons.
		    ButtonGroup group = new ButtonGroup();
		    group.add(birdButton);
		    group.add(pigButton);
		    
		  //Register a listener for the radio buttons.
		    birdButton.addActionListener((ActionListener) this);
		    pigButton.addActionListener((ActionListener) this);
		    
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton addButton = new JButton("Aggiungi Elemento");
				addButton.setActionCommand("Add");
				buttonPane.add(addButton);
				getRootPane().setDefaultButton(addButton);
			}
			{
				JButton cancelButton = new JButton("Annulla");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		
		// Depending on the ActionCommand different actions will be performed
		switch(e.getActionCommand()) {
		
			case "Cancel": 	setVisible(false);
							break;
			
			case "Add":	setVisible(false);
			
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
						quantitySpinner.getValue();
						
						break;
			
			default:	radioChoice = e.getActionCommand();

		}
	}
}