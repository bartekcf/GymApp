package model.components;

import model.gym.Activity;
import model.management.DataBase;
import model.user.Worker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class WorkerActivityList extends JFrame {
    private List<Activity> activities;
    private List<Activity> selectedActivities;
    private Worker worker;
    private DataBase db;
    private DefaultTableModel model;

    public WorkerActivityList(DataBase db, Worker worker) {
        this.db = db;
        this.worker = worker;

        setTitle("Lista zajęć dla pracownika");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        activities = DataBase.deserializeActivities();
        selectedActivities = activities.stream()
                .filter(activity -> activity.getWorker().getId() == worker.getId())
                .collect(Collectors.toList());

        if (!selectedActivities.isEmpty()) {
            createAndShowGUI();
        } else {
            JOptionPane.showMessageDialog(null, "Brak aktywności dla tego pracownika.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void createAndShowGUI() {

        //wychodzi po alercie bez sensu dalej wchodzic
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

        JButton goBackButton = new JButton("Powrót");
        goBackButton.addActionListener(e -> {
            setVisible(false);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(goBackButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
