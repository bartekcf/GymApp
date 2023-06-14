package model.components;

import model.management.DataBase;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterForm {

    public RegisterForm() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        // Utwórz ramkę dla nowego okna
        JFrame frame = new JFrame("Rejestracja");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(Color.GRAY);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.GRAY);

        // Dodaj nagłówek
        JLabel headerLabel = new JLabel("<html><h3 style='font-size: 24px;'>Rejestracja</h3></html>", SwingConstants.CENTER);
        GridBagConstraints headerConstraints = new GridBagConstraints();
        headerConstraints.gridx = 0;
        headerConstraints.gridy = 0;
        headerConstraints.gridwidth = 2;
        headerConstraints.insets = new Insets(20, 10, 30, 10);
        formPanel.add(headerLabel, headerConstraints);

        // Dodaj etykiety i pola tekstowe do formularza
        JLabel nameLabel = new JLabel("Imię:");
        JTextField nameField = new JTextField(20);
        JLabel surnameLabel = new JLabel("Nazwisko:");
        JTextField surnameField = new JTextField(20);
        JLabel dobLabel = new JLabel("Data urodzenia:");
        JTextField dobField = new JTextField(20);
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

        formPanel.add(nameLabel, labelConstraints);
        formPanel.add(nameField, fieldConstraints);
        formPanel.add(surnameLabel, labelConstraints);
        formPanel.add(surnameField, fieldConstraints);
        formPanel.add(dobLabel, labelConstraints);
        formPanel.add(dobField, fieldConstraints);
        formPanel.add(loginLabel, labelConstraints);
        formPanel.add(loginField, fieldConstraints);
        formPanel.add(passwordLabel, labelConstraints);
        formPanel.add(passwordField, fieldConstraints);

        // Utwórz przycisk "Zapisz" do zatwierdzania formularza
        JButton saveButton = new JButton("Zapisz");
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.addActionListener(e -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(DataBase.LOGIN_DATA, true))) {
                writer.write(loginField.getText() + "," + new String(passwordField.getPassword()) + ","
                        + nameField.getText() + "," + surnameField.getText() + "," + dobField.getText());
                writer.newLine();
            } catch (IOException ioException) {
                ioException.printStackTrace();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegisterForm::new);
    }
}
