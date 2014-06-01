package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.JScrollBar;

public class OrderRevisionTemplate extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			OrderRevisionTemplate dialog = new OrderRevisionTemplate();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public OrderRevisionTemplate() {
		setBounds(0, 0, 400, 300);
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
				JLabel lblOrderIDLabel = new JLabel("OrderID: 1096745");
				panel.add(lblOrderIDLabel, BorderLayout.NORTH);
				lblOrderIDLabel.setFont(new Font("Dialog", Font.BOLD, 20));
				lblOrderIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				JLabel lblOrderTimeLabel = new JLabel("Creato in data: ");
				panel.add(lblOrderTimeLabel, BorderLayout.SOUTH);
				lblOrderTimeLabel.setFont(new Font("Dialog", Font.ITALIC, 12));
				lblOrderTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
			}
		}
		{
			JScrollBar scrollBar = new JScrollBar();
			contentPanel.add(scrollBar, BorderLayout.EAST);
		}
		{
			JPanel transaction_panel = new JPanel();
			contentPanel.add(transaction_panel, BorderLayout.CENTER);
			transaction_panel.setLayout(new GridLayout(0, 2, 0, 0));
			{
//				for (int x=0; x<10; x++) {
					
					JLabel lblNewEmptyLabel = new JLabel("New label Title 2");
					lblNewEmptyLabel.setHorizontalAlignment(SwingConstants.LEADING);
					transaction_panel.add(lblNewEmptyLabel);
				
					JLabel lblNewEmptyLabel_2 = new JLabel("New label Quantity Cost 2");
					lblNewEmptyLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);
					transaction_panel.add(lblNewEmptyLabel_2);
				
					JLabel lblNewLabel = new JLabel("New label Title 1");
					lblNewLabel.setHorizontalAlignment(SwingConstants.LEADING);
					transaction_panel.add(lblNewLabel);
					
					JLabel lblNewLabel_2 = new JLabel("New label Quantity Cost 1");
					lblNewLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);
					transaction_panel.add(lblNewLabel_2);
//				}

			}
		}
		{
			JLabel lblOrderCostLabel = new JLabel("Total Cost: 35 €  ");
			lblOrderCostLabel.setFont(new Font("Dialog", Font.BOLD, 17));
			lblOrderCostLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			contentPanel.add(lblOrderCostLabel, BorderLayout.SOUTH);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Modifica Ordine");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
