package model.management;

import model.components.LoginForm;
import model.components.RegisterForm;

import javax.swing.*;
import java.awt.*;

public class GraphicalUserInterface {

    public static final int FRAME_WIDTH = 1000;
    public static final int FRAME_HEIGHT = 600;
    private final String MAIN_IMG = "src/files/images/gg.jpg";
    private ManagementSystem managementSystem;
    private RegisterForm registerForm;
    private LoginForm loginForm;
    private JFrame frame;
    private JButton loginButton;
    private JButton registerButton;
    private DataBase db;

    public GraphicalUserInterface(ManagementSystem managementSystem) {
        this.managementSystem = managementSystem;
        this.db = new DataBase();
//        createAndShowGUI();
    }

    public void createAndShowGUI() {
        // Utwórz ramkę
        frame = new JFrame("GloboGym");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLayout(new GridBagLayout());

        BackgroundImage startPanel = new BackgroundImage(MAIN_IMG);
        startPanel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // Zwiększenie rozmiaru przycisku
        loginButton = new JButton("Zaloguj się");
        loginButton.setPreferredSize(new Dimension(200, 40));
        registerButton = new JButton("Zarejestruj się");
        registerButton.setPreferredSize(new Dimension(200, 40));

        // Dodanie przycisków do panelu
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Ustawienie tła panelu na przezroczyste
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        buttonPanel.add(loginButton, gbc);
        gbc.gridy = 1;
        buttonPanel.add(registerButton, gbc);

        // Dodanie panelu z przyciskami do ramki
        frame.setContentPane(startPanel);
        frame.getContentPane().add(buttonPanel);

        // Dodanie akcji dla przycisków
        loginButton.addActionListener(e -> {
            if (loginForm == null) {
                loginForm = new LoginForm(managementSystem);
            }
            frame.dispose();
        });

        registerButton.addActionListener(e -> {
            if (registerForm == null) {
                registerForm = new RegisterForm();
            }
            frame.dispose();
        });

        frame.setVisible(true);
    }
}
