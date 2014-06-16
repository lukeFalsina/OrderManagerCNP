package main;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.ImageIcon;

import database.DataBaseHandlerImpl;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Main {

    private static JFrame chooseModeFrame;
    private static JFrame chooseSettingsFrame;
    private static JTextField txtNomeDB;
    private static JTextField txtNomePrinter;

    /**
     * @param args
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {

                    initialize();
                    chooseModeFrame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    static void instantiateComponents(String modeChoice, String IPServerDB, String printerPath) {

        chooseModeFrame.dispose();

        DataBaseHandlerImpl DBManager = new DataBaseHandlerImpl(IPServerDB);

        @SuppressWarnings("unused")
        Manager appManager = new Manager(DBManager, modeChoice, printerPath);

    }

    /**
     * Initialize the contents of the frame.
     */
    private static void initialize() {

        chooseModeFrame = new JFrame();
        chooseModeFrame.setBounds(440, 270, 480, 255);
        chooseModeFrame.getContentPane().setLayout(new BoxLayout(chooseModeFrame.getContentPane(), BoxLayout.X_AXIS));
        chooseModeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chooseModeFrame.setUndecorated(true);
        chooseModeFrame.setLocationRelativeTo(null);

        JPanel panel_1 = new JPanel();
        panel_1.setForeground(SystemColor.textInactiveText);
        panel_1.setBackground(SystemColor.controlHighlight);
        panel_1.setBorder(UIManager.getBorder("RootPane.informationDialogBorder"));
        chooseModeFrame.getContentPane().add(panel_1);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[]{390, 0};
        gbl_panel_1.rowHeights = new int[]{72, 44, 43, 35, 0};
        gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel_1.setLayout(gbl_panel_1);

        JPanel panel_2 = new JPanel();
        panel_2.setBackground(Color.WHITE);
        panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 5));
        GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.fill = GridBagConstraints.BOTH;
        gbc_panel_2.insets = new Insets(0, 0, 5, 0);
        gbc_panel_2.gridx = 0;
        gbc_panel_2.gridy = 0;
        panel_1.add(panel_2, gbc_panel_2);

        ImageIcon icon2 = new ImageIcon(Main.class.getResource("/CNPLogo.png"));
        JLabel lblNewLabel_2 = new JLabel(icon2);
        lblNewLabel_2.setVerticalAlignment(SwingConstants.TOP);
        panel_2.add(lblNewLabel_2);

        ImageIcon icon3 = new ImageIcon(Main.class.getResource("/CNPLogoFesta.png"));
        JLabel lblNewLabel_3 = new JLabel(icon3);
        lblNewLabel_3.setVerticalAlignment(SwingConstants.TOP);
        panel_2.add(lblNewLabel_3);

        JLabel lblNewLabel = new JLabel("Scelta della modalità d'uso:");
        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 1;
        panel_1.add(lblNewLabel, gbc_lblNewLabel);

        JPanel panel_3 = new JPanel();
        panel_3.setBackground(SystemColor.controlHighlight);
        panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        GridBagConstraints gbc_panel_3 = new GridBagConstraints();
        gbc_panel_3.insets = new Insets(0, 0, 5, 0);
        gbc_panel_3.gridx = 0;
        gbc_panel_3.gridy = 2;
        panel_1.add(panel_3, gbc_panel_3);

        JButton btnCassaBar = new JButton("Cassa Bar");
        btnCassaBar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooseModeFrame.setVisible(false);
                configureSettings("DRINK");
//				instantiateComponents("DRINK");
            }
        });
        panel_3.add(btnCassaBar);

        JButton btnCassaRistorante = new JButton("Cassa Ristorante");
        btnCassaRistorante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooseModeFrame.setVisible(false);
                configureSettings("FOOD");
//				instantiateComponents("FOOD");
            }
        });
        panel_3.add(btnCassaRistorante);

        JLabel lblNewLabel_1 = new JLabel("Powered by Luca and Ale  ");
        lblNewLabel_1.setFont(new Font("Dialog", Font.ITALIC, 10));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_1.setVerticalAlignment(SwingConstants.BOTTOM);
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.anchor = GridBagConstraints.SOUTHEAST;
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 3;
        panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);
    }

    private static void configureSettings(final String modeChoice) {

        chooseSettingsFrame = new JFrame();
        chooseSettingsFrame.setBounds(440, 270, 350, 200);
        chooseSettingsFrame.getContentPane().setLayout(new BorderLayout(5, 5));
        chooseSettingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chooseSettingsFrame.setUndecorated(true);
        chooseSettingsFrame.setLocationRelativeTo(null);

        JPanel titlePanel = new JPanel();
        chooseSettingsFrame.getContentPane().add(titlePanel, BorderLayout.NORTH);

        JLabel lblConfigurazioneSettings = new JLabel("Configurazione settings");
        lblConfigurazioneSettings.setFont(new Font("Dialog", Font.BOLD, 17));
        titlePanel.add(lblConfigurazioneSettings);

        JPanel settingPanel = new JPanel();
        chooseSettingsFrame.getContentPane().add(settingPanel);
        settingPanel.setLayout(new BoxLayout(settingPanel, BoxLayout.Y_AXIS));

        JPanel urlDBPanel = new JPanel();
        settingPanel.add(urlDBPanel);
        urlDBPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel lblNomeDBLabel = new JLabel("Nome computer che ospita il Data Base: ");
        urlDBPanel.add(lblNomeDBLabel);

        // This is the default IP address which hosts both the DB and the printer
        String standardIPAddress = "127.0.0.1";

        txtNomeDB = new JTextField();
        txtNomeDB.setText(standardIPAddress);
        urlDBPanel.add(txtNomeDB);
        txtNomeDB.setColumns(25);

        JPanel urlPrinterPanel = new JPanel();
        settingPanel.add(urlPrinterPanel);
        urlPrinterPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel lblNomePrinterLabel = new JLabel("Nome computer che condivide la stampante: ");
        urlPrinterPanel.add(lblNomePrinterLabel);

        txtNomePrinter = new JTextField();
        txtNomePrinter.setText("\\\\" + standardIPAddress + "\\EPSONL90");
        urlPrinterPanel.add(txtNomePrinter);
        txtNomePrinter.setColumns(25);

        JPanel buttonPanel = new JPanel();
        chooseSettingsFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setLayout(new BorderLayout(5, 5));

        JPanel panel = new JPanel();
        buttonPanel.add(panel, BorderLayout.CENTER);

        JButton btnConfermaButton = new JButton("Conferma");
        btnConfermaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooseSettingsFrame.setVisible(false);
                instantiateComponents(modeChoice, txtNomeDB.getText(), txtNomePrinter.getText());
            }
        });
        btnConfermaButton.setHorizontalAlignment(SwingConstants.TRAILING);
        buttonPanel.add(btnConfermaButton, BorderLayout.EAST);

        chooseSettingsFrame.setVisible(true);

    }
}
