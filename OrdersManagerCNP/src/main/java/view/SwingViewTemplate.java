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

public class SwingViewTemplate extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingViewTemplate frame = new SwingViewTemplate();
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
	public SwingViewTemplate() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 800, 600);
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		setUndecorated(true);
//		setResizable(false);
		setExtendedState(MAXIMIZED_BOTH);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel titlePanel = new JPanel();
		contentPane.add(titlePanel, BorderLayout.NORTH);
		
		JLabel lblTitleLabel = new JLabel("Gestore prenotazione ordini Codeghì 'N del Pà");
		titlePanel.add(lblTitleLabel);
		lblTitleLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		lblTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JSplitPane splitPanel = new JSplitPane();
		splitPanel.setResizeWeight(1.0);
		contentPane.add(splitPanel, BorderLayout.CENTER);
		
		JPanel elementListPanel = new JPanel();
		splitPanel.setLeftComponent(elementListPanel);
		elementListPanel.setLayout(new GridLayout(4, 1, 0, 0));
		
		JPanel subtitlePanel = new JPanel();
		elementListPanel.add(subtitlePanel);
		
		JLabel titleElementListlabel = new JLabel("Lista degli elementi fra cui scegliere:");
		subtitlePanel.add(titleElementListlabel);
		titleElementListlabel.setFont(new Font("Dialog", Font.ITALIC, 12));
		titleElementListlabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Ripetere in ciclo for per ogni elemento presente da qui..
		
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
		btnAddNormalButton.setHorizontalAlignment(SwingConstants.TRAILING);
		elementButtonPanel.add(btnAddNormalButton);
		
		JButton btnSpecialButton = new JButton("Variazione..");
		btnSpecialButton.setHorizontalAlignment(SwingConstants.TRAILING);
		elementButtonPanel.add(btnSpecialButton);
		
		JPanel elementDetailspanel = new JPanel();
		elementDetailspanel.setBackground(Color.ORANGE);
		oneButtonSplitPanel.setLeftComponent(elementDetailspanel);
		elementDetailspanel.setLayout(new BoxLayout(elementDetailspanel, BoxLayout.Y_AXIS));
		
		JLabel lblElementNameLabel = new JLabel("Nome Elemento");
		elementDetailspanel.add(lblElementNameLabel);
		
		JLabel lblElementDescrLabel = new JLabel("Descrizione Elemento");
		elementDetailspanel.add(lblElementDescrLabel);
		
		// Ripetere in ciclo for per ogni elemento presente fino a qui
		
		JPanel actualOrderPanel = new JPanel();
		splitPanel.setRightComponent(actualOrderPanel);
		actualOrderPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel sumUpTitlePanel = new JPanel();
		actualOrderPanel.add(sumUpTitlePanel, BorderLayout.NORTH);
		
		JLabel label = new JLabel("Riassunto ordine corrente:");
		sumUpTitlePanel.add(label);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Dialog", Font.ITALIC, 12));
		
		// Da qui AGGIORNAMENTO MIDDLE PANEL TRANSAZIONI
		JPanel middlePanel = new JPanel();
		actualOrderPanel.add(middlePanel, BorderLayout.CENTER);
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
		
		JPanel orderTrackPanel = new JPanel();
		middlePanel.add(orderTrackPanel);
		orderTrackPanel.setLayout(new GridLayout(8, 1, 0, 0));
		
		JPanel linePanel = new JPanel();
		orderTrackPanel.add(linePanel);
		linePanel.setLayout(new BoxLayout(linePanel, BoxLayout.X_AXIS));
		
		JPanel buttonLinePanel = new JPanel();
		linePanel.add(buttonLinePanel);
		buttonLinePanel.setLayout(new BoxLayout(buttonLinePanel, BoxLayout.X_AXIS));
		
		JButton btnRemoveLineButton = new JButton("Rimuovi linea..");
		buttonLinePanel.add(btnRemoveLineButton);
		
		JPanel transactionPanel = new JPanel();
		linePanel.add(transactionPanel);
		transactionPanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JLabel lblNameTransLabel = new JLabel("New label");
		transactionPanel.add(lblNameTransLabel);
		
		JLabel lblQuaCostLabel_1 = new JLabel("New label");
		lblQuaCostLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		transactionPanel.add(lblQuaCostLabel_1);
		
		JPanel totalPanel = new JPanel();
		middlePanel.add(totalPanel);
		totalPanel.setLayout(new BorderLayout(0, 15));
		
		JLabel lblTotalOrderLabel = new JLabel("Totale: €  ");
		lblTotalOrderLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		lblTotalOrderLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		totalPanel.add(lblTotalOrderLabel, BorderLayout.NORTH);
		
		JPanel controlOrderPanel = new JPanel();
		actualOrderPanel.add(controlOrderPanel, BorderLayout.SOUTH);
		
		// Fino a qui AGGIORNAMENTO MIDDLE PANEL TRANSAZIONI ORDINE
				
		JButton btnCancelOrderButton = new JButton("Cancella ordine corrente..");
		controlOrderPanel.add(btnCancelOrderButton);
				
		JButton btnFinalizeButton = new JButton("Finalizza ordine corrente..");
		controlOrderPanel.add(btnFinalizeButton);
		
		JPanel generalButtonPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) generalButtonPanel.getLayout();
		flowLayout.setVgap(20);
		flowLayout.setAlignment(FlowLayout.TRAILING);
		contentPane.add(generalButtonPanel, BorderLayout.SOUTH);
		
		JButton btnSearchButton = new JButton("Ricerca altro ordine per ID..");
		generalButtonPanel.add(btnSearchButton);
		
		JButton btnExitButton = new JButton("Abbandona..");
		generalButtonPanel.add(btnExitButton);
	}

}
