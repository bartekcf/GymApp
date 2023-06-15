package model.components;

import model.management.DataBase;
import model.management.ManagementSystem;

import javax.swing.*;
import java.awt.*;

public class LoginForm {

    private DataBase db;
    private JFrame frame;
    private ManagementSystem managementSystem;

        public LoginForm(DataBase db, JFrame frame,ManagementSystem managementSystem) {
            this.db = db;
            this.frame = frame;
            this.managementSystem = managementSystem;
            createAndShowGUI();
        }

    private void createAndShowGUI() {
        // Utwórz ramkę dla nowego okna
        JFrame frame = new JFrame("Logowanie");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(Color.GRAY);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.GRAY);

        // Dodaj nagłówek
        JLabel headerLabel = new JLabel("<html><h3 style='font-size: 24px;'>Logowanie</h3></html>", SwingConstants.CENTER);
        GridBagConstraints headerConstraints = new GridBagConstraints();
        headerConstraints.gridx = 0;
        headerConstraints.gridy = 0;
        headerConstraints.gridwidth = 2;
        headerConstraints.insets = new Insets(20, 10, 30, 10);
        formPanel.add(headerLabel, headerConstraints);

        // Dodaj etykiety i pola tekstowe do formularza
        JLabel loginLabel = new JLabel("Login:");
        JTextField loginField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Hasło:");
        JPasswordField passwordField = new JPasswordField(20);

        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.anchor = GridBagConstraints.EAST;
        labelConstraints.insets = new Insets(10, 10, 10, 30);

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = 1;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.insets = new Insets(10, 0, 10, 10);

        formPanel.add(loginLabel, labelConstraints);
        formPanel.add(loginField, fieldConstraints);
        formPanel.add(passwordLabel, labelConstraints);
        formPanel.add(passwordField, fieldConstraints);

        // Utwórz przycisk "Zapisz" do zatwierdzania formularza
        JButton saveButton = new JButton("Zaloguj");
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.addActionListener(e -> {
            String username = loginField.getText();
            String password = new String(passwordField.getPassword());
            boolean loginSuccessful = db.loadLoginData(username, password);
            if (loginSuccessful) {
                frame.dispose();  // zamknij bieżące okno
                EventQueue.invokeLater(() -> {
                    frame.dispose();
                    if(loguje sie ClubMember){
                    MainPanel mainPanel = new MainPanel(db,managementSystem);
                    mainPanel.createAndShowGUI();}

                    if(loguje sie pracownik){
                        MainPanelWorker = new MainPanelWorker()
                                // reeszta
                    }

                    if(loguje sie manager){
                        MainPanelManager= new MainPanelManager()
                        // reeszta
                    }

                });
            } else {
                // Można wyświetlić komunikat o błędzie logowania
                JOptionPane.showMessageDialog(frame, "Niepoprawne dane logowania!", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 6;
        buttonConstraints.gridwidth = 2;
        buttonConstraints.anchor = GridBagConstraints.CENTER;
        buttonConstraints.insets = new Insets(30, 10, 10, 10);
        formPanel.add(saveButton, buttonConstraints);

        frame.add(formPanel);

        // Ustawienie widoczności
        frame.setVisible(true);
    }
}



