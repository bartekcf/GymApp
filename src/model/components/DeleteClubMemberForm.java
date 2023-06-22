package model.components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.user.ClubMember;
import model.gym.Activity;
import model.management.DataBase;

public class DeleteClubMemberForm extends JDialog {
    private final DataBase db;
    private Activity selectedActivity;
    private ClubMember selectedClubMember;
    private JTable table;

    public DeleteClubMemberForm(DataBase db, Activity selectedActivity) {
        this.db = db;
        this.selectedActivity = selectedActivity;

        setTitle("Wybór członka klubu");
        setSize(500, 300);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        List<ClubMember> clubMembers = selectedActivity.getClubMembers();

        // Utwórz tablicę dwuwymiarową dla danych tabeli
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

        // Utwórz panel z przyciskiem "Usuń"
        JPanel buttonPanel = new JPanel();
        JButton removeButton = new JButton("Usuń");
        removeButton.addActionListener(e -> {
            // Pobierz indeks wybranego wiersza
            int selectedRowIndex = table.getSelectedRow();
            if (selectedRowIndex != -1) {
                selectedClubMember = clubMembers.get(selectedRowIndex);
                removeClubMember(selectedClubMember);
                JOptionPane.showMessageDialog(this, "Członek klubu został usunięty z zajęć."); // Reakcja po dodaniu członka klubu
                dispose();
            }
        });

        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void removeClubMember(ClubMember clubMember) {
        selectedActivity.removeFromActivity(clubMember);
        db.serializeActivities(db.getActivities()); // Zapisz zmiany w aktywnościach do pliku
    }

    public ClubMember getSelectedClubMember() {
        return selectedClubMember;
    }
}
