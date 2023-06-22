package model.components;

import model.management.DataBase;
import model.user.ClubMember;

import javax.swing.*;
import java.awt.*;

public class EditProfileDialog extends JDialog {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton saveButton;
    private ClubMember clubMember;
    private DataBase db;

    public EditProfileDialog(Frame owner, String title, boolean modal, DataBase db, ClubMember clubMember) {
        super(owner, title, modal);
        this.db = db;
        this.clubMember = clubMember;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        add(new JLabel("Imię: "), gbc);
        firstNameField = new JTextField(20);
        firstNameField.setText(clubMember.getFirstName());
        gbc.gridx = 1;
        add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Nazwisko: "), gbc);
        lastNameField = new JTextField(20);
        lastNameField.setText(clubMember.getLastName());
        gbc.gridx = 1;
        add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Login: "), gbc);
        usernameField = new JTextField(20);
        usernameField.setText(clubMember.getLogin());
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Hasło: "), gbc);
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(passwordField, gbc);

        saveButton = new JButton("Zapisz");
        saveButton.addActionListener(e -> saveChanges());
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(saveButton, gbc);

        pack();
        setLocationRelativeTo(null); // Wyśrodkowanie okna
    }

    private void saveChanges() {
        String newFirstName = firstNameField.getText();
        String newLastName = lastNameField.getText();
        String newUsername = usernameField.getText();
        String newPassword = new String(passwordField.getPassword());

        if (newUsername.isEmpty() || newPassword.isEmpty() || newFirstName.isEmpty() || newLastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Żadne z pól nie może być puste.", "Błąd", JOptionPane.ERROR_MESSAGE);
        } else {
            clubMember.setFirstName(newFirstName);
            clubMember.setLastName(newLastName);
            clubMember.setLogin(newUsername);
            clubMember.setPassword(newPassword);
            db.serialize(); // Aktualizacja danych na dysku
            JOptionPane.showMessageDialog(this, "Dane zostały zapisane.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }
}
