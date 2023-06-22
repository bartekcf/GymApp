package model.components;

import model.gym.Activity;
import model.management.BackgroundImage;
import model.management.DataBase;
import model.management.GraphicalUserInterface;
import model.management.ManagementSystem;
import model.user.ClubMember;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import static model.management.GraphicalUserInterface.FRAME_HEIGHT;
import static model.management.GraphicalUserInterface.FRAME_WIDTH;

public class MainPanel {
    private static String IMG = "src/files/images/cm.jpg";
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
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLayout(new GridBagLayout());

        BackgroundImage CMPanel = new BackgroundImage(IMG);
        CMPanel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel welcomeLabel = new JLabel("Witaj, " + clubMember.getFirstName() + " " + clubMember.getLastName() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton showCurrentActivitiesButton = new JButton("Pokaż dostępne zajęcia");
        showCurrentActivitiesButton.addActionListener(e -> {
                UserAllActivity userAllActivity = new UserAllActivity(db, clubMember);
                userAllActivity.createAndShowGUI();
        });

        JButton showActivitiesButton = new JButton("Pokaż moje zajęcia");
        showActivitiesButton.addActionListener(e -> {
            ClubMemberActivityList clubMemberActivityList = new ClubMemberActivityList(db,clubMember);
            clubMemberActivityList.createAndShowGUI();
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
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false); // Ustawienie tła panelu na przezroczyste
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(welcomeLabel, gbc);


        gbc.gridy = 1;
        contentPanel.add(showCurrentActivitiesButton,gbc);

        gbc.gridy = 2;
        contentPanel.add(showActivitiesButton, gbc);

        gbc.gridy = 3;
        contentPanel.add(updateProfileButton, gbc);

        gbc.gridy = 4;
        contentPanel.add(checkGymPass, gbc);

        frame.setContentPane(CMPanel);
        frame.getContentPane().add(contentPanel);

        // Wyświetl ramkę
        frame.setVisible(true);
    }
}
