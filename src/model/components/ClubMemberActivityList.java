package model.components;

import model.gym.Activity;
import model.management.DataBase;
import model.user.ClubMember;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class ClubMemberActivityList extends JFrame {
    private List<Activity> activities;
    private List<Activity> selectedActivities;
    private ClubMember clubMember;
    private DataBase db;
    private DefaultTableModel model;

    public ClubMemberActivityList(DataBase db, ClubMember clubMember) {
        this.db = db;
        this.clubMember = clubMember;

        setTitle("Twoja lista zajęć");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        activities = DataBase.deserializeActivities();
        selectedActivities = activities.stream()
                .filter(activity -> activity.getClubMembers().stream().anyMatch(cm -> cm.getId() == clubMember.getId()))
                .collect(Collectors.toList());

        if (!selectedActivities.isEmpty()) {
            createAndShowGUI();
        } else {
            JOptionPane.showMessageDialog(null, "Nie masz żadnych zajęć leniu.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void createAndShowGUI() {

        // Wychodzi po alercie bez sensu dalej wchodzić
        if (selectedActivities.isEmpty()) {
            return;
        }

        String[] columnNames = {"ID", "Nazwa", "Data rozpoczęcia", "Data zakończenia", "Pracownik", "Pokój"};
        model = new DefaultTableModel(columnNames, 0);

        for (Activity activity : selectedActivities) {
            Object[] o = new Object[6];
            o[0] = activity.getId();
            o[1] = activity.getName();
            o[2] = activity.getStartTime();
            o[3] = activity.getEndTime();
            o[4] = activity.getWorker();
            o[5] = (activity.getRoom() != null) ? activity.getRoom() : ""; // Check for null room
            model.addRow(o);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton unsubscribeButton = new JButton("Wypisz się");
        unsubscribeButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int activityId = (int) model.getValueAt(selectedRow, 0);
                Activity selectedActivity = selectedActivities.stream()
                        .filter(activity -> activity.getId() == activityId)
                        .findFirst()
                        .orElse(null);
                if (selectedActivity != null) {
                    clubMember.signOutFromActivity(selectedActivity);
                    model.removeRow(selectedRow);
                    db.serializeActivities(activities);
                }
            }
        });

        JButton goBackButton = new JButton("Powrót");
        goBackButton.addActionListener(e -> setVisible(false));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(goBackButton);
        buttonPanel.add(unsubscribeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
