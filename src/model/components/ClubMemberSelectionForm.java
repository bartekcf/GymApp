package model.components;

import model.management.DataBase;
import model.user.ClubMember;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClubMemberSelectionForm extends JDialog {
    private final DataBase db;
    private ClubMember selectedClubMember;
    private JList<ClubMember> clubMemberList;
    private DefaultListModel<ClubMember> listModel;

    public ClubMemberSelectionForm(DataBase db) {
        this.db = db;

        setTitle("Wybór członka klubu");
        setSize(300, 200);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        listModel = new DefaultListModel<>();
        clubMemberList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(clubMemberList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton removeButton = new JButton("Usuń");
        removeButton.addActionListener(e -> {
            ClubMember selectedMember = clubMemberList.getSelectedValue();
            if (selectedMember != null) {
                int choice = JOptionPane.showConfirmDialog(this, "Czy na pewno chcesz usunąć członka " + selectedMember.getClubMemberLogin() + "?", "Potwierdzenie", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    removeClubMember(selectedMember);
                }
            }
        });
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setClubMembers(db.getClubMembers()); // Ustawienie członków klubu na podstawie bazy danych

        setVisible(true);
    }

    public void setClubMembers(List<ClubMember> clubMembers) {
        listModel.clear();
        for (ClubMember member : clubMembers) {
            listModel.addElement(member);
        }
    }

    private void removeClubMember(ClubMember clubMember) {
        db.getUsers().remove(clubMember);
        db.serialize(); // Zapisz zmiany do pliku
        setClubMembers(db.getClubMembers()); // Odświeżenie listy członków klubu
    }

    public ClubMember getSelectedClubMember() {
        return selectedClubMember;
    }
}
