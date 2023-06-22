package model.components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.user.ClubMember;
import model.management.DataBase;
import model.gym.Activity;

public class AddClubMemberForm extends JDialog {
    private final DataBase db;
    private final List<Activity> activities;
    private JTable table;
    private Activity selectedActivity;

    public AddClubMemberForm(DataBase db, List<Activity> activities, Activity selectedActivity) {
        this.db = db;
        this.activities = activities;
        this.selectedActivity = selectedActivity;

        setTitle("Dodaj członka klubu");
        setSize(500, 300);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        List<ClubMember> clubMembers = db.getClubMembers();

        Object[][] data = new Object[clubMembers.size()][3];
        for (int i = 0; i < clubMembers.size(); i++) {
            ClubMember clubMember = clubMembers.get(i);
            data[i][0] = clubMember.getId();
            data[i][1] = clubMember.getFirstName();
            data[i][2] = clubMember.getLastName();
        }

        // Utwórz tablicę z nazwami kolumn
        String[] columnNames = {"ID", "Imię", "Nazwisko"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Dodaj");
        addButton.addActionListener(e -> {
            int selectedRowIndex = table.getSelectedRow();
            if (selectedRowIndex != -1) {
                int memberId = (int) table.getValueAt(selectedRowIndex, 0);
                db.updateUserMembershipStatus();
                ClubMember selectedClubMember = clubMembers.stream()
                        .filter(member -> member.getId() == memberId)
                        .findFirst()
                        .orElse(null);

                if (selectedClubMember != null) {
                    boolean isMemberAssigned = selectedActivity.getClubMembers().contains(selectedClubMember);
                    if (isMemberAssigned) {
                        JOptionPane.showMessageDialog(this, "Ten użytkownik jest już przypisany do tych zajęć.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    } else if (!selectedClubMember.isPaid()) {
                        JOptionPane.showMessageDialog(this, "Ten użytkownik nie posiada opłaconego karnetu.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    } else {
                        selectedActivity.addToActivity(selectedClubMember);
                        db.serializeActivities(activities);
                        JOptionPane.showMessageDialog(this, "Członek klubu został dodany.");
                    }
                }
            }
            dispose();
        });


        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
