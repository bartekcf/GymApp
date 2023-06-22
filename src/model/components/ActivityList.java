package model.components;

import model.gym.Activity;
import model.management.DataBase;
import model.user.ClubMember;
import model.user.Worker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ActivityList extends JFrame {
    private List<Activity> activities;
    private Worker worker;
    private DataBase db;
    private DefaultTableModel model;

    public ActivityList(DataBase db, Worker worker) {
        this.db = db;
        this.worker = worker;

        setTitle("Lista aktywności");
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

        JButton addActivityButton = new JButton("Dodaj zajęcia");
        addActivityButton.addActionListener(e -> {
            ActivityForm activityForm = new ActivityForm(activities, worker, db);
            activityForm.setWorker(worker);
            activityForm.createAndShowGUI();
        });

        JButton addMemberButton = new JButton("Dodaj członka");
        addMemberButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) model.getValueAt(row, 0);
                Activity selectedActivity = activities.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
                if (selectedActivity != null) {
                    ClubMember member = selectClubMember(); // Wybór członka klubu
                    if (member != null) {
                        selectedActivity.addToActivity(member);
                        db.serializeActivities(activities);
                        model.fireTableDataChanged(); // Odświeżenie danych w tabeli
                    }
                }
            }
        });

        JButton removeMemberButton = new JButton("Usuń członka");
        removeMemberButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) model.getValueAt(row, 0);
                Activity selectedActivity = activities.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
                if (selectedActivity != null) {
                    ClubMember member = selectClubMember(); // Wybór członka klubu
                    if (member != null) {
                        selectedActivity.removeFromActivity(member);
                        db.serializeActivities(activities);
                        model.fireTableDataChanged(); // Odświeżenie danych w tabeli
                    }
                }
            }
        });

        JButton addWorkerButton = new JButton("Dodaj pracownika");
        addWorkerButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) model.getValueAt(row, 0);
                activities.stream().filter(a -> a.getId() == id).findFirst().ifPresent(selectedActivity -> {
                    if (selectedActivity.getWorker() != null) {
                        JOptionPane.showMessageDialog(null, "Aktywność ma już przypisanego pracownika.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    } else {
                        WorkerSelectionForm workerSelectionForm = new WorkerSelectionForm(db, selectedActivity);
                        workerSelectionForm.createAndShowGUI();
                        workerSelectionForm.setVisible(true);
                    }
                });
            }
        });

        JButton removeWorkerButton = new JButton("Usuń pracownika");
        removeWorkerButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) model.getValueAt(row, 0);
                Activity selectedActivity = activities.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
                if (selectedActivity != null && selectedActivity.getWorker() != null) {
                    db.removeWorker(selectedActivity.getWorker(), selectedActivity);
                    selectedActivity.removeWorker(selectedActivity.getWorker()); // Usunięcie pracownika z aktywności
                    db.serializeActivities(activities);
                    model.setValueAt("", row, 4);
                    model.fireTableDataChanged(); // Odświeżenie danych w tabeli
                }
            }
        });

        JButton removeActivityButton = new JButton("Usuń aktywność");
        removeActivityButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) model.getValueAt(row, 0);
                db.removeActivity(id); // Usunięcie aktywności z bazy danych
                activities.removeIf(a -> a.getId() == id); // Usunięcie aktywności z listy w pamięci
                model.removeRow(row); // Usunięcie wiersza z tabeli
                activities = DataBase.deserializeActivities(); // Odświeżenie listy aktywności
            }
        });

        JButton goBackButton = new JButton("Zamknij");
        goBackButton.addActionListener(e -> {
            setVisible(false);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addActivityButton);
        buttonPanel.add(addMemberButton);
        buttonPanel.add(removeMemberButton);
        buttonPanel.add(addWorkerButton);
        buttonPanel.add(removeWorkerButton);
        buttonPanel.add(removeActivityButton);
        buttonPanel.add(goBackButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    private ClubMember selectClubMember() {
        ClubMemberSelectionForm clubMemberSelectionForm = new ClubMemberSelectionForm(db);
        clubMemberSelectionForm.setClubMembers(db.getClubMembers());
        clubMemberSelectionForm.setVisible(true);
        return clubMemberSelectionForm.getSelectedClubMember();
    }
}
