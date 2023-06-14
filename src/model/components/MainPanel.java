package model.components;

import model.management.GraphicalUserInterface;
import model.management.ManagementSystem;

import javax.swing.*;
import java.awt.*;

public class MainPanel {
    private JFrame frame;
    private ManagementSystem managementSystem;
    public MainPanel(ManagementSystem managementSystem) {
        this.managementSystem = managementSystem;
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        // Utwórz ramkę
        frame = new JFrame("Drugi Widok");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GraphicalUserInterface.FRAME_WIDTH, GraphicalUserInterface.FRAME_HEIGHT);
        frame.setLayout(new GridBagLayout());

        // Utwórz elementy interfejsu użytkownika dla drugiego widoku
        JLabel label = new JLabel("Witaj w drugim widoku!");
        label.setFont(new Font("Arial", Font.BOLD, 24));

        JButton backButton = new JButton("Powrót");
        backButton.addActionListener(e -> {
            frame.dispose();
            GraphicalUserInterface graphicalUserInterface = new GraphicalUserInterface(managementSystem);
            graphicalUserInterface.createAndShowGUI();
        });

        // Dodaj elementy do panelu
        frame.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        frame.getContentPane().add(label, gbc);

        gbc.gridy = 1;
        frame.getContentPane().add(backButton, gbc);

        // Wyświetl ramkę
        frame.setVisible(true);
    }
}
