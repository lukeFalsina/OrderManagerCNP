package view;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JTextArea;

public class MessageTemplate extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MessageTemplate frame = new MessageTemplate();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MessageTemplate() {
		setBounds(100, 100, 250, 150);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		
//		ImageIcon icon = new ImageIcon("src/main/resources/success_button.png");
		panel.setLayout(new GridLayout(1, 2, 0, 5));
		JLabel lblNewLabel = new JLabel("NewLabelAndStop");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 20));
		
		JLabel label = new JLabel("");
		panel_2.add(label, BorderLayout.NORTH);
		
		JTextArea txtrVeryButVery = new JTextArea();
		panel_2.add(txtrVeryButVery);
		txtrVeryButVery.setWrapStyleWord(true);
		txtrVeryButVery.setEditable(false);
		txtrVeryButVery.setLineWrap(true);
		txtrVeryButVery.setText("Very but very real important");
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setVgap(10);
		flowLayout.setAlignOnBaseline(true);
		getContentPane().add(panel_1);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setVerticalAlignment(SwingConstants.BOTTOM);
		panel_1.add(btnNewButton);

	}

}
