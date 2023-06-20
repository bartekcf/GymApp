package model.components;

import model.gym.Activity;
import model.management.DataBase;
import model.management.GraphicalUserInterface;
import model.management.ManagementSystem;
import model.user.Worker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class WorkerMainPanel {
    private JFrame frame;
    private ManagementSystem managementSystem;
    private DataBase db;
    private Worker worker;

    public WorkerMainPanel(DataBase db, Worker worker) {
        this.db = db;
        this.worker = worker;
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

        JLabel welcomeLabel = new JLabel("Witaj, " + worker.getFirstName() + " " + worker.getLastName() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton showActivitiesButton = new JButton("Pokaż moje zajęcia");
        showActivitiesButton.addActionListener(e -> {
            List<Activity> activities = worker.getActivities();
            // Implementuj logikę wyświetlania listy zajęć
        });

        JButton activityListButton = new JButton("Lista aktywności");
        activityListButton.addActionListener(e -> {
            ActivityList activityList = new ActivityList(db, worker);
            activityList.setVisible(true);
            //            frame.dispose(); nie zamykam tutaj bo robie przycisk powrót który tutaj wraca
        });

        // Dodaj elementy do panelu
        frame.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        frame.getContentPane().add(welcomeLabel, gbc);

        gbc.gridy = 2;
        frame.getContentPane().add(showActivitiesButton, gbc);

        gbc.gridy = 3;
        frame.getContentPane().add(activityListButton, gbc);

        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Menu");
        JMenuItem logoutMenuItem = new JMenuItem("Wyloguj");
        logoutMenuItem.addActionListener(e -> {
            GraphicalUserInterface gui = new GraphicalUserInterface(managementSystem);
            gui.createAndShowGUI();
            frame.dispose();
        });

        optionsMenu.add(logoutMenuItem);
        menuBar.add(optionsMenu);
        frame.setJMenuBar(menuBar);

        // Wyświetl ramkę
        frame.setVisible(true);
    }
}
