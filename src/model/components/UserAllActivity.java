package model.components;

import model.gym.Activity;
import model.management.DataBase;
import model.user.ClubMember;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserAllActivity extends JFrame {
    private List<Activity> activities;
    private ClubMember clubMember;

    private DataBase db;
    private DefaultTableModel model;

    public UserAllActivity(DataBase db, ClubMember clubMember) {
        this.db = db;
        this.clubMember = clubMember;
        setTitle("Lista wszystkich aktywności");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        activities = DataBase.deserializeActivities();
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        String[] columnNames = {"ID", "Nazwa", "Data rozpoczęcia", "Data zakończenia", "Pracownik", "Pokój"};
        model = new DefaultTableModel(columnNames, 0);

        for (Activity a : activities) {
            Object[] o = new Object[6];
            o[0] = a.getId();
            o[1] = a.getName();
            o[2] = a.getStartTime();
            o[3] = a.getEndTime();
            o[4] = a.getWorker();
            o[5] = (a.getRoom() != null) ? a.getRoom() : ""; // Check for null room
            model.addRow(o);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Zapisz się");
        addButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) model.getValueAt(row, 0);
                Activity selectedActivity = activities.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
                boolean alreadySigned = activities.stream()
                        .anyMatch(activity -> activity.getClubMembers()
                                .stream()
                                .anyMatch(clubMember1 -> clubMember1.getId() == this.clubMember.getId()));

                if(alreadySigned){
                    JOptionPane.showMessageDialog(null, "Jesteś juz zapisany na te zajęcia.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (selectedActivity != null) {
                    if(!clubMember.isPaid()){
                        JOptionPane.showMessageDialog(null, "Masz nie opłacony karnet.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if (!selectedActivity.getClubMembers().contains(clubMember)) {
                        selectedActivity.addToActivity(clubMember);
                        db.serializeActivities(activities);
                        JOptionPane.showMessageDialog(null, "Zapisałeś się na zajęcia: " + selectedActivity.getName(), "Sukces", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Jesteś już zapisany na te zajęcia.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        JButton goBackButton = new JButton("Powrót");
        goBackButton.addActionListener(e -> setVisible(false));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(goBackButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
