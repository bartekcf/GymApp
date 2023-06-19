package model.components;

import interfaces.UserFactoryInterface;
import model.management.DataBase;
import model.management.GraphicalUserInterface;
import model.management.ManagementSystem;
import model.user.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RegisterForm {

    private DataBase db;

    public RegisterForm(DataBase db) {
        this.db = db;
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

        JLabel roleLabel = new JLabel("Rola:");
        String[] roles = {User.ROLE_CLUB_MEMBER, User.ROLE_WORKER, User.ROLE_MANAGER};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);

        JLabel salaryLabel = new JLabel("Pensja:");
        JTextField salaryField = new JTextField(20);
        JLabel managementStyleLabel = new JLabel("Styl zarządzania:");
        JTextField managementStyleField = new JTextField(20);

        salaryLabel.setVisible(false);
        salaryField.setVisible(false);
        managementStyleLabel.setVisible(false);
        managementStyleField.setVisible(false);

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

        formPanel.add(roleLabel, labelConstraints);
        formPanel.add(roleComboBox, fieldConstraints);

        formPanel.add(salaryLabel, labelConstraints);
        formPanel.add(salaryField, fieldConstraints);
        formPanel.add(managementStyleLabel, labelConstraints);
        formPanel.add(managementStyleField, fieldConstraints);

        // Wyświetl pola "salary" i "managementStyle" w zależności od wybranej roli
        roleComboBox.addActionListener(e -> {
            String selectedRole = (String) roleComboBox.getSelectedItem();
            if (selectedRole != null) {
                switch (selectedRole) {
                    case User.ROLE_WORKER:
                        salaryLabel.setVisible(true);
                        salaryField.setVisible(true);
                        managementStyleLabel.setVisible(false);
                        managementStyleField.setVisible(false);
                        break;
                    case User.ROLE_MANAGER:
                        salaryLabel.setVisible(true);
                        salaryField.setVisible(true);
                        managementStyleLabel.setVisible(true);
                        managementStyleField.setVisible(true);
                        break;
                    default:
                        salaryLabel.setVisible(false);
                        salaryField.setVisible(false);
                        managementStyleLabel.setVisible(false);
                        managementStyleField.setVisible(false);
                        break;
                }
            }
        });

        // Dodaj przycisk "Zarejestruj się"
        JButton submitButton = new JButton("Zarejestruj się");
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 1;
        buttonConstraints.gridy = 10;
        buttonConstraints.anchor = GridBagConstraints.WEST;
        buttonConstraints.insets = new Insets(30, 0, 0, 10);

        submitButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String surname = surnameField.getText();
                LocalDate dob = LocalDate.parse(dobField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String login = loginField.getText();
                String password = new String(passwordField.getPassword());
                String role = (String) roleComboBox.getSelectedItem();

                UserFactoryInterface factory;
                User newUser = null;

                if (role.equalsIgnoreCase(User.ROLE_CLUB_MEMBER)) {
                    factory = new ClubMemberFactory();
                    newUser = factory.create(name, surname, login, password, dob);
                } else if (role.equalsIgnoreCase(User.ROLE_WORKER)) {
                    factory = new WorkerFactory();
                    newUser = factory.create(name, surname, login, password, dob);
                    if (newUser instanceof Worker) {
                        ((Worker) newUser).setSalary(Double.parseDouble(salaryField.getText()));
                    }
                } else if (role.equalsIgnoreCase(User.ROLE_MANAGER)) {
                    factory = new ManagerFactory();
                    newUser = factory.create(name, surname, login, password, dob);
                    if (newUser instanceof Manager) {
                        ((Manager) newUser).setSalary(Double.parseDouble(salaryField.getText()));
                        ((Manager) newUser).setManagementStyle(managementStyleField.getText());
                    }
                }

                if (newUser != null) {
                    db.addUser(newUser);
                    JOptionPane.showMessageDialog(frame, "Rejestracja powiodła się!");
                    GraphicalUserInterface gui = new GraphicalUserInterface(new ManagementSystem());
                    gui.createAndShowGUI();
                    frame.dispose();
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(frame, "Nieprawidłowy format daty. Proszę użyć formatu: RRRR-MM-DD");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Nieprawidłowy format pensji. Proszę wprowadzić liczbę.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, "Wprowadzono nieprawidłowe dane: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Wystąpił błąd podczas rejestracji: " + ex.getMessage());
            }
        });

        formPanel.add(submitButton, buttonConstraints);

        frame.add(formPanel);
        frame.setVisible(true);
    }
}
