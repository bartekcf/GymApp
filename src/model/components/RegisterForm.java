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
    private boolean isRegister = false;

    public RegisterForm(boolean isRegister) {
        this.db = DataBase.deserialize();
        this.isRegister = isRegister;
    }

    public void createAndShowGUI() {
        // Utwórz ramkę dla nowego okna
        JFrame frame = new JFrame("Rejestracja");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(Color.GRAY);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.GRAY);

        // Dodaj etykiety i pola tekstowe do formularza
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.anchor = GridBagConstraints.EAST;
        labelConstraints.insets = new Insets(10, 10, 10, 30);

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = 1;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.insets = new Insets(10, 0, 10, 10);

        JLabel nameLabel = new JLabel("Imię:");
        JTextField nameField = new JTextField(20);
        formPanel.add(nameLabel, labelConstraints);
        formPanel.add(nameField, fieldConstraints);

        JLabel surnameLabel = new JLabel("Nazwisko:");
        JTextField surnameField = new JTextField(20);
        formPanel.add(surnameLabel, labelConstraints);
        formPanel.add(surnameField, fieldConstraints);

        JLabel birthdayLabel = new JLabel("Data urodzenia:");
        JTextField birthdayField = new JTextField(20);
        formPanel.add(birthdayLabel, labelConstraints);
        formPanel.add(birthdayField, fieldConstraints);

        JLabel loginLabel = new JLabel("Login:");
        JTextField loginField = new JTextField(20);
        formPanel.add(loginLabel, labelConstraints);
        formPanel.add(loginField, fieldConstraints);

        JLabel passwordLabel = new JLabel("Hasło:");
        JPasswordField passwordField = new JPasswordField(20);
        formPanel.add(passwordLabel, labelConstraints);
        formPanel.add(passwordField, fieldConstraints);

        JComboBox<String> roleComboBox;
        JTextField salaryField;
        JTextField managementStyleField;

        if (isRegister) {
            JLabel roleLabel = new JLabel("Rola:");
            String[] roles = {User.ROLE_CLUB_MEMBER, User.ROLE_WORKER, User.ROLE_MANAGER};
            roleComboBox = new JComboBox<>(roles);
            formPanel.add(roleLabel, labelConstraints);
            formPanel.add(roleComboBox, fieldConstraints);

            JLabel salaryLabel = new JLabel("Pensja:");
            salaryField = new JTextField(20);
            formPanel.add(salaryLabel, labelConstraints);
            formPanel.add(salaryField, fieldConstraints);

            JLabel managementStyleLabel = new JLabel("Styl zarządzania:");
            managementStyleField = new JTextField(20);
            formPanel.add(managementStyleLabel, labelConstraints);
            formPanel.add(managementStyleField, fieldConstraints);

            // Ukryj pola pensji i stylu zarządzania na początku
            salaryLabel.setVisible(false);
            salaryField.setVisible(false);
            managementStyleLabel.setVisible(false);
            managementStyleField.setVisible(false);

            // Nasłuchuj zmiany wyboru roli
            roleComboBox.addActionListener(e -> {
                String selectedRole = (String) roleComboBox.getSelectedItem();
                if (selectedRole != null) {
                    if (selectedRole.equalsIgnoreCase(User.ROLE_WORKER)) {
                        salaryLabel.setVisible(true);
                        salaryField.setVisible(true);
                    } else if (selectedRole.equalsIgnoreCase(User.ROLE_MANAGER)) {
                        salaryLabel.setVisible(true);
                        salaryField.setVisible(true);
                        managementStyleLabel.setVisible(true);
                        managementStyleField.setVisible(true);
                    } else {
                        salaryLabel.setVisible(false);
                        salaryField.setVisible(false);
                        managementStyleLabel.setVisible(false);
                        managementStyleField.setVisible(false);
                    }
                    formPanel.revalidate();
                    formPanel.repaint();
                }
            });
        } else {
            roleComboBox = null;
            managementStyleField = null;
            salaryField = null;
        }

        // Dodaj przycisk "Zarejestruj się" lub "Dodaj"
        JButton submitButton;
        if (isRegister) {
            submitButton = new JButton("Zarejestruj się");
        } else {
            submitButton = new JButton("Dodaj");
        }
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 1;
        buttonConstraints.gridy = 10;
        buttonConstraints.anchor = GridBagConstraints.CENTER;
        buttonConstraints.insets = new Insets(30, 0, 0, 10);
        formPanel.add(submitButton, buttonConstraints);

        JButton backButton = new JButton("Cofnij");
        GridBagConstraints buttonConstraintsBack = new GridBagConstraints();
        buttonConstraintsBack.gridx = 1;
        buttonConstraintsBack.gridy = 11;
        buttonConstraintsBack.anchor = GridBagConstraints.CENTER;
        buttonConstraintsBack.insets = new Insets(30, 0, 0, 10);
        formPanel.add(backButton, buttonConstraintsBack);

        frame.add(formPanel);
        frame.setVisible(true);

        submitButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String surname = surnameField.getText();
                LocalDate birthday = LocalDate.parse(birthdayField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String login = loginField.getText();
                String password = new String(passwordField.getPassword());
                String role;
                if (isRegister) {
                    assert roleComboBox != null;
                    role = (String) roleComboBox.getSelectedItem();
                } else {
                    role = "ClubMember";
                }

                // Sprawdź, czy login jest unikalny
                boolean isUnique = db.getUsers().stream().noneMatch(u -> u.getLogin().equals(login));
                if (!isUnique) {
                    JOptionPane.showMessageDialog(frame, "Podany login jest już zajęty. Wybierz inny.");
                    return;
                }

                LocalDate eighteenYearsAgo = LocalDate.now().minusYears(18);
                if (birthday.isAfter(eighteenYearsAgo)) {
                    JOptionPane.showMessageDialog(frame, "Użytkownik musi mieć co najmniej 18 lat.");
                    return;
                }

                // Sprawdź, czy długość hasła wynosi co najmniej 5 znaków
                if (password.length() < 5) {
                    JOptionPane.showMessageDialog(frame, "Hasło musi zawierać co najmniej 5 znaków.");
                    return;
                }

                UserFactoryInterface factory;
                User newUser = null;

                assert role != null;
                if (role.equalsIgnoreCase(User.ROLE_CLUB_MEMBER)) {
                    factory = new ClubMemberFactory();
                    newUser = factory.create(name, surname, login, password, birthday);
                } else if (role.equalsIgnoreCase(User.ROLE_WORKER)) {
                    factory = new WorkerFactory();
                    newUser = factory.create(name, surname, login, password, birthday);
                    if (newUser instanceof Worker) {
                        if (isRegister) {
                            ((Worker) newUser).setSalary(Double.parseDouble(salaryField.getText()));
                        }
                    }
                } else if (role.equalsIgnoreCase(User.ROLE_MANAGER)) {
                    factory = new ManagerFactory();
                    newUser = factory.create(name, surname, login, password, birthday);
                    if (newUser instanceof Manager) {
                        if (isRegister) {
                            ((Manager) newUser).setSalary(Double.parseDouble(salaryField.getText()));
                            ((Manager) newUser).setManagementStyle(managementStyleField.getText());
                        }
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

        backButton.addActionListener(e -> {
            GraphicalUserInterface gui = new GraphicalUserInterface(new ManagementSystem());
            gui.createAndShowGUI();
            frame.dispose();
        });
    }
}
