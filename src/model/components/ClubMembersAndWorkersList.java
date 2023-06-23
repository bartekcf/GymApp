package model.components;

import model.management.DataBase;
import model.user.ClubMember;
import model.user.User;
import model.user.Worker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClubMembersAndWorkersList extends JFrame {
    private List<User> users;
    private DefaultTableModel model;

    public ClubMembersAndWorkersList(DataBase db) {
        this.users = db.getUsers();

        setTitle("Lista pracowników i członków klubu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        createAndShowGUI();
    }

    public void createAndShowGUI() {
        String[] columnNames = {"Rola", "Imię", "Nazwisko"};
        model = new DefaultTableModel(columnNames, 0);

        for (User user : users) {
            if (user instanceof Worker) {
                user.setUserRole(User.ROLE_WORKER);
            } else if (user instanceof ClubMember) {
                user.setUserRole(User.ROLE_CLUB_MEMBER);
            }
            Object[] row = {user.getUserRole(), user.getFirstName(), user.getLastName()};
            model.addRow(row);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton goBackButton = new JButton("Cofnij");
        goBackButton.addActionListener(e -> {
            setVisible(false);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(goBackButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

}
