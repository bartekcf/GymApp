package model.components;

import model.gym.Activity;
import model.management.DataBase;
import model.management.GraphicalUserInterface;
import model.management.ManagementSystem;
import model.user.Manager;
import model.user.Worker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManagerMainPanel {
    private JFrame frame;
    private ManagementSystem managementSystem;
    private DataBase db;
    private Manager manager;

    public ManagerMainPanel(DataBase db, Manager manager) {
        this.db = db;
        this.manager = manager;
    }

    public void createAndShowGUI() {
        frame = new JFrame("Panel Główny");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GraphicalUserInterface.FRAME_WIDTH, GraphicalUserInterface.FRAME_HEIGHT);
        frame.setLayout(new GridBagLayout());

        // Dodanie napisu "Typ konta: członek siłowni" w prawym górnym rogu
        JLabel accountTypeLabel = new JLabel("Typ konta: Pracownik siłowni");
        accountTypeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        GridBagConstraints gbcAccountTypeLabel = new GridBagConstraints();
        gbcAccountTypeLabel.anchor = GridBagConstraints.NORTHEAST;
        gbcAccountTypeLabel.insets = new Insets(10, 10, 0, 10);
        gbcAccountTypeLabel.gridx = 1;
        gbcAccountTypeLabel.gridy = 0;
        gbcAccountTypeLabel.weightx = 1.0;
        gbcAccountTypeLabel.weighty = 0.0;
        gbcAccountTypeLabel.gridwidth = GridBagConstraints.REMAINDER;
        frame.getContentPane().add(accountTypeLabel, gbcAccountTypeLabel);

        JLabel welcomeLabel = new JLabel("Witaj, " + manager.getFirstName() + " " + manager.getLastName() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton updateProfileButton = new JButton("Aktualizuj mój profil");
        updateProfileButton.addActionListener(e -> {
            // Implementuj logikę aktualizacji profilu
        });

        // Dodaj przycisk wylogowania jako rozwijane menu
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Menu");

        JMenuItem logoutMenuItem = new JMenuItem("Wyloguj");
        logoutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Dodaj kod obsługujący wylogowanie
            }
        });
        optionsMenu.add(logoutMenuItem);
        menuBar.add(optionsMenu);
        frame.setJMenuBar(menuBar);

        // Dodaj elementy do panelu
        frame.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        frame.getContentPane().add(welcomeLabel, gbc);

        gbc.gridy = 3;
        frame.getContentPane().add(updateProfileButton, gbc);

        // Wyświetl ramkę
        frame.setVisible(true);
    }
}
