package model.components;

import model.management.BackgroundImage;
import model.management.DataBase;
import model.management.GraphicalUserInterface;
import model.management.ManagementSystem;
import model.user.ClubMember;
import model.user.Manager;
import model.user.User;
import model.user.Worker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginForm {
    private final String MAIN_IMG = "src/files/images/login.jpg";
    private DataBase db;
    private JFrame frame;
    private ManagementSystem managementSystem;

    public LoginForm(ManagementSystem managementSystem) {
        this.db = DataBase.deserialize();
        this.frame = new JFrame("Logowanie");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(1000, 600);
        this.managementSystem = managementSystem;
//        createAndShowGUI();
    }

    public JFrame getFrame() {
        return frame;
    }

    public void createAndShowGUI() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new GridBagLayout());

        // Tło
        BackgroundImage backgroundImage = new BackgroundImage(MAIN_IMG);
        backgroundImage.setLayout(new GridBagLayout());
        frame.setContentPane(backgroundImage);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setOpaque(false);

        JLabel roleLabel = new JLabel("Rola:");
        roleLabel.setForeground(Color.WHITE);
        String[] roles = {User.ROLE_CLUB_MEMBER, User.ROLE_WORKER, User.ROLE_MANAGER};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);

        GridBagConstraints roleLabelConstraints = new GridBagConstraints();
        roleLabelConstraints.gridx = 0;
        roleLabelConstraints.gridy = 1;
        roleLabelConstraints.anchor = GridBagConstraints.EAST;
        roleLabelConstraints.insets = new Insets(10, 10, 10, 30);
        formPanel.add(roleLabel, roleLabelConstraints);

        GridBagConstraints roleFieldConstraints = new GridBagConstraints();
        roleFieldConstraints.gridx = 1;
        roleFieldConstraints.gridy = 1;
        roleFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        roleFieldConstraints.insets = new Insets(10, 0, 10, 10);
        formPanel.add(roleComboBox, roleFieldConstraints);

        JLabel loginLabel = new JLabel("Login:");
        loginLabel.setForeground(Color.WHITE);
        JTextField loginField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Hasło:");
        passwordLabel.setForeground(Color.WHITE);
        JPasswordField passwordField = new JPasswordField(20);

        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = 2;
        labelConstraints.anchor = GridBagConstraints.EAST;
        labelConstraints.insets = new Insets(10, 10, 10, 30);

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = 1;
        fieldConstraints.gridy = 2;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.insets = new Insets(10, 0, 10, 10);

        formPanel.add(loginLabel, labelConstraints);
        formPanel.add(loginField, fieldConstraints);

        labelConstraints.gridy = 3;
        fieldConstraints.gridy = 3;
        formPanel.add(passwordLabel, labelConstraints);
        formPanel.add(passwordField, fieldConstraints);

        JButton saveButton = new JButton("Zaloguj");
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String login = loginField.getText();
                String password = new String(passwordField.getPassword());
                String selectedRole = (String) roleComboBox.getSelectedItem();

                List<User> users = db.getUsers();
                User user = users.stream()
                        .filter(u -> u.getLogin().equals(login) && u.getPassword().equals(password))
                        .filter(u -> (selectedRole.equals(User.ROLE_CLUB_MEMBER) && u instanceof ClubMember)
                                || (selectedRole.equals(User.ROLE_WORKER) && u instanceof Worker)
                                || (selectedRole.equals(User.ROLE_MANAGER) && u instanceof Manager))
                        .findFirst()
                        .orElse(null);

                if (user != null) {
                    frame.dispose();

                    User finalUser = user;
                    EventQueue.invokeLater(() -> {
                        frame.dispose();
                        if (finalUser instanceof ClubMember) {
                            MainPanel mainPanel = new MainPanel(db, (ClubMember) finalUser);
                            mainPanel.createAndShowGUI();
                        } else if (finalUser instanceof Worker) {
                            WorkerMainPanel workerMainPanel = new WorkerMainPanel(db, (Worker) finalUser);
                            workerMainPanel.createAndShowGUI();
                        } else if (finalUser instanceof Manager) {
                            ManagerMainPanel managerMainPanel = new ManagerMainPanel(db, (Manager) finalUser);
                            managerMainPanel.createAndShowGUI();
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(frame, "Niepoprawne dane logowania!", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton backButton = new JButton("Cofnij");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GraphicalUserInterface gui = new GraphicalUserInterface(managementSystem);
                gui.createAndShowGUI();
                frame.dispose();
            }
        });

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 6;
        buttonConstraints.gridwidth = 2;
        buttonConstraints.anchor = GridBagConstraints.CENTER;
        buttonConstraints.insets = new Insets(30, 10, 10, 10);
        formPanel.add(saveButton, buttonConstraints);

        GridBagConstraints buttonConstraintsBack = new GridBagConstraints();
        buttonConstraintsBack.gridx = 0;
        buttonConstraintsBack.gridy = 7;
        buttonConstraintsBack.gridwidth = 2;
        buttonConstraintsBack.anchor = GridBagConstraints.CENTER;
        buttonConstraintsBack.insets = new Insets(10, 10, 10, 10);
        formPanel.add(backButton, buttonConstraintsBack);

        frame.add(formPanel);

        frame.setVisible(true);
    }
}
