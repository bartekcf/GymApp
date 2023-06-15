package model.components;

import model.gym.Activity;
import model.management.DataBase;
import model.management.GraphicalUserInterface;
import model.management.ManagementSystem;
import model.user.ClubMember;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainPanel {
    private JFrame frame;
    private ManagementSystem managementSystem;
    private DataBase db;
    private ClubMember clubMember;  // Dodane

    // Dodajemy do konstruktora ClubMember
    public MainPanel(DataBase db, ClubMember clubMember) {
        this.db = db;
        this.clubMember = clubMember;  // Dodane
    }

    public void createAndShowGUI() {
        // Utwórz ramkę
        frame = new JFrame("Panel Główny");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GraphicalUserInterface.FRAME_WIDTH, GraphicalUserInterface.FRAME_HEIGHT);
        frame.setLayout(new GridBagLayout());

        // Utwórz elementy interfejsu użytkownika dla widoku klubowicza
        JLabel welcomeLabel = new JLabel("Witaj, " + clubMember.getFirstName() + " " + clubMember.getLastName() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton showActivitiesButton = new JButton("Pokaż moje zajęcia");
        showActivitiesButton.addActionListener(e -> {
            List<Activity> activities = clubMember.getActivities();  // Zakładam, że ta metoda istnieje
            // Implementuj logikę wyświetlania listy zajęć
        });

        JButton updateProfileButton = new JButton("Aktualizuj mój profil");
        updateProfileButton.addActionListener(e -> {
            // Implementuj logikę aktualizacji profilu
        });

        // itd.

        // Dodaj elementy do panelu
        frame.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        frame.getContentPane().add(welcomeLabel, gbc);

        gbc.gridy = 1;
        frame.getContentPane().add(showActivitiesButton, gbc);

        gbc.gridy = 2;
        frame.getContentPane().add(updateProfileButton, gbc);

        // itd.

        // Wyświetl ramkę
        frame.setVisible(true);
    }
}
