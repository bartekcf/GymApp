package model.components;

import interfaces.UserFactoryInterface;
import model.management.DataBase;
import model.management.GraphicalUserInterface;
import model.management.ManagementSystem;
import model.user.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RegisterForm {

    private DataBase db;
    private boolean isRegister;
    private boolean isMenago;
    private boolean isCM;

    public RegisterForm(boolean isRegister, boolean isMenago, boolean isCM) {
        this.db = DataBase.deserialize();
        this.isRegister = isRegister;
        this.isMenago = isMenago;
        this.isCM = isCM;
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Rejestracja");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(Color.GRAY);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.GRAY);

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

        JTextField salaryField = new JTextField(20);
        JTextField managementStyleField = new JTextField(20);

        JLabel salaryLabel = new JLabel("Pensja:");
        formPanel.add(salaryLabel, labelConstraints);
        formPanel.add(salaryField, fieldConstraints);

        JLabel managementStyleLabel = new JLabel("Styl zarządzania:");
        formPanel.add(managementStyleLabel, labelConstraints);
        formPanel.add(managementStyleField, fieldConstraints);

        JComboBox<String> roleComboBox = new JComboBox<>();
        roleComboBox.addItem(User.ROLE_CLUB_MEMBER);
        roleComboBox.addItem(User.ROLE_WORKER);
        roleComboBox.addItem(User.ROLE_MANAGER);

        // Ustawiamy początkową widoczność pól
        if (isMenago && !isCM) {
            salaryLabel.setVisible(true);
            salaryField.setVisible(true);
        } else {
            salaryLabel.setVisible(false);
            salaryField.setVisible(false);
        }
        managementStyleLabel.setVisible(false);
        managementStyleField.setVisible(false);

        // Dodajemy nasłuchiwacz zmian w roli
        roleComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedRole = (String) roleComboBox.getSelectedItem();
                boolean isManager = User.ROLE_MANAGER.equals(selectedRole);

                salaryLabel.setVisible(isManager || (isRegister && isMenago));
                salaryField.setVisible(isManager || (isRegister && isMenago));
                managementStyleLabel.setVisible(isManager || (isRegister && isMenago));
                managementStyleField.setVisible(isManager || (isRegister && isMenago));
            }
        });

        // Dodajemy pole roli do formularza tylko jeśli isRegister jest true
        if (isRegister) {
            JLabel roleLabel = new JLabel("Rola:");
            formPanel.add(roleLabel, labelConstraints);
            formPanel.add(roleComboBox, fieldConstraints);
        }

        JButton submitButton = new JButton("Zarejestruj");
        formPanel.add(submitButton, fieldConstraints);


        JButton backButtonForManager = new JButton("Zamknij");
        JButton backButton = new JButton("Powrót");
        if (isMenago || !isRegister) {
            formPanel.add(backButtonForManager, fieldConstraints);
        } else {
            formPanel.add(backButton, fieldConstraints);
        }

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
                if (isCM && isMenago) {
                    role = "ClubMember";
                }
                else if (!isCM && isMenago) {
                    role = "Worker";
                } else {
                    role = (String) roleComboBox.getSelectedItem();
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
                        if (isRegister && !isMenago) {
                            ((Worker) newUser).setSalary(0);
                        } else {
                            ((Worker) newUser).setSalary(Double.parseDouble(salaryField.getText()));
                        }
                    }
                } else if (role.equalsIgnoreCase(User.ROLE_MANAGER)) {
                    factory = new ManagerFactory();
                    newUser = factory.create(name, surname, login, password, birthday);
                    if (newUser instanceof Manager) {
                        // Sprawdź, czy istnieje już manager w bazie danych
                        if (db.getUsers().stream().anyMatch(user -> user instanceof Manager)) {
                            JOptionPane.showMessageDialog(frame, "Manager może być tylko jeden.");
                            return;
                        }
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

        backButtonForManager.addActionListener(e -> {
            frame.dispose();
        });

        backButton.addActionListener(e -> {
            GraphicalUserInterface gui = new GraphicalUserInterface(new ManagementSystem());
            gui.createAndShowGUI();
            frame.dispose();
        });
    }
}
