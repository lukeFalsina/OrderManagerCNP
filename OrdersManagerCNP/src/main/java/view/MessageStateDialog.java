package view;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
//import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class MessageStateDialog extends JDialog {

	private static final long serialVersionUID = -4390999778317093532L;
	private boolean errorState;
	private String visualizedText;

	// dialogExample = new MessageDialog(frame, false, "Testo di prova");
	public MessageStateDialog(Frame frame, boolean errorState, String visualizedText) {
		super(frame, "Notifica Esito Operazione", true);
		this.errorState = errorState;
		this.visualizedText = visualizedText;
		initialize();
	}
	
	// dialogExample = new MessageDialog(dialog, false, "Testo di prova");
	public MessageStateDialog(Dialog dialog, boolean errorState, String visualizedText) {
		super(dialog, "Notifica Esito Operazione", true);
		this.errorState = errorState;
		this.visualizedText = visualizedText;
		initialize();
	}

	private void initialize() {
		
		setBounds(0, 0, 400, 200);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		
		ImageIcon icon;
		if (errorState) {
			icon = new ImageIcon("src/main/resources/error_button.png");
			}
		else {
			icon = new ImageIcon("src/main/resources/success_button.png");
		}
		
		panel.setLayout(new GridLayout(1, 2, 0, 0));
		JLabel lblNewLabel = new JLabel(icon);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 50));
		
		JLabel northLabel = new JLabel("");
		panel_2.add(northLabel, BorderLayout.NORTH);
		
		JTextArea txtrVeryButVery = new JTextArea();
		txtrVeryButVery.setWrapStyleWord(true);
		txtrVeryButVery.setEditable(false);
		txtrVeryButVery.setLineWrap(true);
		txtrVeryButVery.setText(visualizedText);
		panel_2.add(txtrVeryButVery);
		
		JLabel southLabel = new JLabel("");
		panel_2.add(southLabel, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1);
		
/*		JButton btnConfirmButton = new JButton("Ok");
		btnConfirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnConfirmButton.setVerticalAlignment(SwingConstants.BOTTOM);
		panel_1.add(btnConfirmButton); */
		
		setVisible(true);
	}
}
