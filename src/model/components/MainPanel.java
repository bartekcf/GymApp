package model.components;

import model.gym.Activity;
import model.management.DataBase;
import model.management.GraphicalUserInterface;
import model.management.ManagementSystem;
import model.user.ClubMember;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class MainPanel {
    private JFrame frame;
    private ManagementSystem managementSystem;
    private DataBase db;
    private ClubMember clubMember;

    public MainPanel(DataBase db, ClubMember clubMember) {
        this.db = db;
        this.db.updateUserMembershipStatus();
        this.clubMember = clubMember;
    }

    public void createAndShowGUI() {
        frame = new JFrame("Panel Główny");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GraphicalUserInterface.FRAME_WIDTH, GraphicalUserInterface.FRAME_HEIGHT);
        frame.setLayout(new GridBagLayout());

        // Dodanie napisu "Typ konta: członek siłowni" w prawym górnym rogu
        JLabel accountTypeLabel = new JLabel("Typ konta: członek siłowni");
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

        JLabel welcomeLabel = new JLabel("Witaj, " + clubMember.getFirstName() + " " + clubMember.getLastName() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton showCurrentActivitiesButton = new JButton("Pokaż dostępne zajęcia");
        showCurrentActivitiesButton.addActionListener(e -> {
            List<Activity> activities = clubMember.getActivities();
            // Implementuj logikę wyświetlania listy zajęć
        });

        JButton showActivitiesButton = new JButton("Pokaż moje zajęcia");
        showActivitiesButton.addActionListener(e -> {
            List<Activity> activities = clubMember.getActivities();
            // Implementuj logikę wyświetlania listy zajęć
        });

        JButton updateProfileButton = new JButton("Aktualizuj mój profil");
        updateProfileButton.addActionListener(e -> {
            // Implementuj logikę aktualizacji profilu
        });

        JButton checkGymPass = new JButton("Sprawdź stan karnetu");
        checkGymPass.addActionListener(e -> {
            JDialog gymPassDialog = new JDialog(frame, "Stan Karnetu", true);
            gymPassDialog.setSize(300, 200);
            gymPassDialog.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            boolean isPaid = db.getMembershipStatus().getOrDefault(clubMember.getId(), false);
            JLabel statusLabel = new JLabel(isPaid ? "Karnet jest opłacony." : "Karnet nie jest opłacony.");
            gbc.gridy = 0;
            gymPassDialog.add(statusLabel, gbc);

            if (!clubMember.isPaid()) {
                Random rand = new Random();
                double amount = 50 + rand.nextInt(150); // losowo generuje kwotę od 50 do 200
                JLabel amountLabel = new JLabel("Kwota do zapłaty: " + amount + " ZL");
                gbc.gridy = 1;
                gymPassDialog.add(amountLabel, gbc);

                JButton payButton = new JButton("Opłać");
                payButton.addActionListener(event -> {
                    clubMember.setPaid(true);
                    db.updateUserStatus(clubMember);
                    JOptionPane.showMessageDialog(gymPassDialog, "Karnet został opłacony.");
                    gymPassDialog.dispose();
                });
                gbc.gridy = 2;
                gymPassDialog.add(payButton, gbc);
            }

            JButton returnButton = new JButton("Powrót");
            returnButton.addActionListener(event -> gymPassDialog.dispose());
            gbc.gridy = 3;
            gymPassDialog.add(returnButton, gbc);

            gymPassDialog.setVisible(true);
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

        gbc.gridy = 2;
        frame.getContentPane().add(showActivitiesButton, gbc);

        gbc.gridy = 3;
        frame.getContentPane().add(updateProfileButton, gbc);

        gbc.gridy = 4;
        frame.getContentPane().add(checkGymPass, gbc);

        // Wyświetl ramkę
        frame.setVisible(true);
    }
}
