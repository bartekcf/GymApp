package model.components;

import model.management.DataBase;
import model.management.ManagementSystem;
import model.user.ClubMember;
import model.user.Manager;
import model.user.User;
import model.user.Worker;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LoginForm {

    private DataBase db;
    private JFrame frame;
    private ManagementSystem managementSystem;

    public LoginForm(DataBase db, JFrame frame, ManagementSystem managementSystem) {
        this.db = db;
        this.frame = frame;
        this.managementSystem = managementSystem;
        createAndShowGUI();
    }

    public JFrame getFrame() {
        return frame;
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

            JLabel roleLabel = new JLabel("Rola:");
            String[] roles = {User.ROLE_CLUB_MEMBER, User.ROLE_WORKER, User.ROLE_MANAGER};
            JComboBox<String> roleComboBox = new JComboBox<>(roles);

            GridBagConstraints roleLabelConstraints = new GridBagConstraints();
            roleLabelConstraints.gridx = 0;
            roleLabelConstraints.gridy = 1; // Changed to 1
            roleLabelConstraints.anchor = GridBagConstraints.EAST;
            roleLabelConstraints.insets = new Insets(10, 10, 10, 30);
            formPanel.add(roleLabel, roleLabelConstraints);

            GridBagConstraints roleFieldConstraints = new GridBagConstraints();
            roleFieldConstraints.gridx = 1;
            roleFieldConstraints.gridy = 1; // Changed to 1
            roleFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
            roleFieldConstraints.insets = new Insets(10, 0, 10, 10);
            formPanel.add(roleComboBox, roleFieldConstraints);

            // Dodaj etykiety i pola tekstowe do formularza
            JLabel loginLabel = new JLabel("Login:");
            JTextField loginField = new JTextField(20);
            JLabel passwordLabel = new JLabel("Hasło:");
            JPasswordField passwordField = new JPasswordField(20);

            GridBagConstraints labelConstraints = new GridBagConstraints();
            labelConstraints.gridx = 0;
            labelConstraints.gridy = 2; // Changed to 2
            labelConstraints.anchor = GridBagConstraints.EAST;
            labelConstraints.insets = new Insets(10, 10, 10, 30);

            GridBagConstraints fieldConstraints = new GridBagConstraints();
            fieldConstraints.gridx = 1;
            fieldConstraints.gridy = 2; // Changed to 2
            fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
            fieldConstraints.insets = new Insets(10, 0, 10, 10);

            formPanel.add(loginLabel, labelConstraints);
            formPanel.add(loginField, fieldConstraints);

            labelConstraints.gridy = 3; // Changed to 3
            fieldConstraints.gridy = 3; // Changed to 3
            formPanel.add(passwordLabel, labelConstraints);
            formPanel.add(passwordField, fieldConstraints);

        // Utwórz przycisk "Zaloguj" do zatwierdzania formularza
        JButton saveButton = new JButton("Zaloguj");
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.addActionListener(e -> {
            String login = loginField.getText();
            String password = new String(passwordField.getPassword());
            String selectedRole = (String) roleComboBox.getSelectedItem();

            Map<String, ObjectInputStream> fileStreams = db.loadSerializedFiles();

            User user = null;
            try {
                switch (Objects.requireNonNull(selectedRole)) {
                    case User.ROLE_CLUB_MEMBER -> {
                        List<ClubMember> clubMembers = (List<ClubMember>) fileStreams.get(DataBase.CLUB_MEMBERS_FILE).readObject();
//                        for(ClubMember clubMember : clubMembers){
//                            System.out.println(clubMember);
//                        }
                        user = clubMembers.stream()
                                .filter(cm -> cm.getClubMemberLogin().equals(login) && cm.getPassword().equals(password))
                                .findFirst()
                                .orElse(null);
                    }
                    case User.ROLE_WORKER -> {
                        List<Worker> workers = (List<Worker>) fileStreams.get(DataBase.WORKERS_FILE).readObject();
                        user = workers.stream()
                                .filter(w -> w.getWorkerLogin().equals(login) && w.getPassword().equals(password))
                                .findFirst()
                                .orElse(null);
                    }
                    case User.ROLE_MANAGER -> {
                        List<Manager> managers = (List<Manager>) fileStreams.get(DataBase.MANAGERS_FILE).readObject();
                        user = managers.stream()
                                .filter(m -> m.getManagerLogin().equals(login) && m.getPassword().equals(password))
                                .findFirst()
                                .orElse(null);
                    }
                }
            } catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }

            if (user != null) {
                frame.dispose();

                User finalUser = user;
                EventQueue.invokeLater(() -> {
                    frame.dispose();
                    if (finalUser instanceof ClubMember) {
                        MainPanel mainPanel = new MainPanel(db, (ClubMember) finalUser);
                        mainPanel.createAndShowGUI();
                    }
                    else if (finalUser instanceof Worker) {
                        WorkerMainPanel workerMainPanel = new WorkerMainPanel(db, (Worker) finalUser);
                        workerMainPanel.createAndShowGUI();
                    }
                });
            } else {
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
