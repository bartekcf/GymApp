package model.management;

import model.components.LoginForm;
import model.components.RegisterForm;

import javax.swing.*;
import java.awt.*;

public class GraphicalUserInterface {

    public static final int FRAME_WIDTH = 1000;
    public static final int FRAME_HEIGHT = 600;
    private final String MAIN_IMG = "src/files/images/gym_background.jpg";
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

        // Ustawienie tła na obrazek
        BackgroundImage startPanel = new BackgroundImage(MAIN_IMG);
        startPanel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setContentPane(startPanel);

        // Zwiększenie rozmiaru przycisku
        loginButton = new JButton("Zaloguj się");
        loginButton.setPreferredSize(new Dimension(200, 40));
        registerButton = new JButton("Zarejestruj się");
        registerButton.setPreferredSize(new Dimension(200, 40));

        registerButton.addActionListener(e -> {
            if (registerForm == null) {
                registerForm = new RegisterForm();
            }
            frame.dispose();
        });

        loginButton.addActionListener(e -> {
            if (loginForm == null) {
                loginForm = new LoginForm(frame, managementSystem);
            }
//            frame.dispose();
        });

        frame.add(loginButton);
        frame.add(registerButton);

        frame.setVisible(true);
    }

}
