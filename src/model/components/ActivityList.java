package model.components;

import model.gym.Activity;
import model.management.DataBase;
import model.management.GraphicalUserInterface;
import model.user.Worker;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ActivityList extends JFrame {
    private List<Activity> activities;
    private Worker worker;
    private DataBase db;

    public ActivityList(DataBase db, Worker worker) {
        this.db = db;
        this.worker = worker;
        setTitle("Lista aktywności");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(GraphicalUserInterface.FRAME_WIDTH, GraphicalUserInterface.FRAME_HEIGHT);
        setLayout(new BorderLayout());

        activities = DataBase.deserializeActivities();
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        JList<Activity> activityJList = new JList<>(activities.toArray(new Activity[0]));
        JScrollPane scrollPane = new JScrollPane(activityJList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addActivityButton = new JButton("Dodaj");
        addActivityButton.addActionListener(e -> {
            ActivityForm activityForm = new ActivityForm(activities, worker, db);
            activityForm.setWorker(worker);
            activityForm.createAndShowGUI();
        });

        buttonPanel.add(addActivityButton);

        JButton backButton = new JButton("Cofnij");
        backButton.addActionListener(e -> dispose());
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
