package model.components;

import model.gym.Activity;
import model.management.DataBase;
import model.management.GraphicalUserInterface;
import model.management.ManagementSystem;
import model.user.Manager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ManagerMainPanel {
    private JFrame frame;
    private ManagementSystem managementSystem;
    private DataBase db;
    private Manager manager;
    private JTextField quoteTextField;
    private JButton updateQuoteButton;
    private List<Activity> activities;

    public ManagerMainPanel(DataBase db, Manager manager) {
        this.db = db;
        this.manager = manager;
    }

    public void createAndShowGUI() {
        frame = new JFrame("Panel Główny");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GraphicalUserInterface.FRAME_WIDTH, GraphicalUserInterface.FRAME_HEIGHT);
        frame.setLayout(new GridBagLayout());



        JLabel welcomeLabel = new JLabel("Witaj, " + manager.getFirstName() + " " + manager.getLastName() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton activityListButton = new JButton("Lista aktywności");
        activityListButton.addActionListener(e -> {
            ActivityList activityList = new ActivityList(db, null, true);
            activityList.setVisible(true);
        });


        JButton addClubMemberButton = new JButton("Dodaj członka klubu");
        addClubMemberButton.addActionListener(e -> {
            RegisterForm registerForm = new RegisterForm(false, true, true);
            registerForm.createAndShowGUI();
        });

        JButton addWorkerButton = new JButton("Dodaj pracownika");
        addWorkerButton.addActionListener(e -> {
            RegisterForm registerForm = new RegisterForm(false, true, false);
            registerForm.createAndShowGUI();
        });


        JButton viewStatisticButton = new JButton("Statystki");
        viewStatisticButton.addActionListener(e -> {
            ManagerStatistics managerStatistics = new ManagerStatistics(db);
            managerStatistics.createAndShowGUI();
        });

        JButton userList = new JButton("Lista użytkowników");
        userList.addActionListener(e -> {
            ClubMembersAndWorkersList clubMembersAndWorkersList = new ClubMembersAndWorkersList(db);
            clubMembersAndWorkersList.createAndShowGUI();
        });


        quoteTextField = new JTextField(20);
        updateQuoteButton = new JButton("Aktualizuj cytat");
        updateQuoteButton.addActionListener(e -> {
            String newQuote = quoteTextField.getText();
            manager.setMessage(newQuote);
            db.serialize(); // Zapisz zmiany w bazie danych
            JOptionPane.showMessageDialog(frame, "Cytat został zaktualizowany.");
        });

        // Dodaj przycisk wylogowania jako rozwijane menu
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

        // Dodaj elementy do panelu
        frame.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        frame.getContentPane().add(welcomeLabel, gbc);

        gbc.gridy = 3;
        frame.getContentPane().add(activityListButton, gbc);

        gbc.gridy = 4;
        frame.getContentPane().add(addClubMemberButton, gbc);

        gbc.gridy = 5;
        frame.getContentPane().add(addWorkerButton, gbc);

        gbc.gridy = 6;
        frame.getContentPane().add(viewStatisticButton, gbc);

        gbc.gridy = 7;
        frame.getContentPane().add(userList, gbc);

        gbc.gridy = 9;
        frame.getContentPane().add(quoteTextField, gbc);

        gbc.gridy = 10;
        frame.getContentPane().add(updateQuoteButton, gbc);

        // Wyświetl ramkę
        frame.setVisible(true);
    }
}
