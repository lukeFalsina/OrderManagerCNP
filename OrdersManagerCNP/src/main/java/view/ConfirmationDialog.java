package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;

public abstract class ConfirmationDialog extends JDialog {

	private static final long serialVersionUID = -5733407555808104120L;
	
	protected final JPanel contentPanel = new JPanel();
	protected final JPanel envCentralPanel = new JPanel();;
	protected ReturnSwingView returnSwingView;
	protected boolean acceptInputText;

	protected JButton okButton;
	protected JTextField textField;
	
	/**
	 * Create the dialog.
	 */
	public ConfirmationDialog(Frame frame, ReturnSwingView returnSwingView, boolean acceptInputText) {
		
		super(frame, "Conferma Scelta", true);
		this.returnSwingView = returnSwingView;
		this.acceptInputText = acceptInputText;
		
	}
	
	public void centerResize(int width, int height) {
		setBounds(0, 0, width, height);
		setLocationRelativeTo(null);
	}
	
	public void initialize(String textToBevisualized) {
		
		setBounds(0, 0, 300, 210);
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		envCentralPanel.setLayout(new BoxLayout(envCentralPanel, BoxLayout.Y_AXIS));
		envCentralPanel.add(contentPanel);
		getContentPane().add(envCentralPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JTextArea displayedText = new JTextArea();
			displayedText.setWrapStyleWord(true);
			displayedText.setText(textToBevisualized);
			displayedText.setRows(3);
			displayedText.setLineWrap(true);
			//JTextField displayedText = new JTextField(textToBevisualized);
			displayedText.setEditable(false);
			contentPanel.add(displayedText, BorderLayout.CENTER);
		}
		if (acceptInputText) {
		{
			JPanel userInputPanel = new JPanel();
			contentPanel.add(userInputPanel, BorderLayout.SOUTH);
			userInputPanel.setLayout(new BorderLayout(0, 0));
			{
				JLabel lblTitleInputlabel = new JLabel("ID Ordine: ");
				userInputPanel.add(lblTitleInputlabel, BorderLayout.WEST);
			}
			{
				textField = new JTextField();
				userInputPanel.add(textField);
				textField.setColumns(10);
			}
		}
		
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Annulla");
				cancelButton.setActionCommand("Annulla");
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
	
	/**
	 * This method will be used by every expanded button to set their behavior 
	 * when the ok button is clicked.
	 */
	protected abstract void setOKButton();

	public void setReturnSwingView(ReturnSwingView returnSwingView) {
		this.returnSwingView = returnSwingView;
	}

}
