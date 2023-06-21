package model.components;

import model.gym.Activity;
import model.gym.GymRoom;
import model.management.DataBase;
import model.user.Worker;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ActivityForm {
    private Worker worker;
    private List<Activity> activities;
    private DataBase db;

    public ActivityForm(List<Activity> activities, Worker worker, DataBase db) {
        this.activities = activities;
        this.worker = worker;
        this.db = db;
    }

    public void createAndShowGUI() {
        JFrame addActivityFrame = new JFrame("Dodaj aktywność");
        addActivityFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addActivityFrame.setSize(500, 400);
        addActivityFrame.setLayout(new FlowLayout());

        JLabel nameLabel = new JLabel("Nazwa:");
        JTextField nameTextField = new JTextField(20);
        addActivityFrame.add(nameLabel);
        addActivityFrame.add(nameTextField);

        JLabel startTimeLabel = new JLabel("Data rozpoczęcia (RRRR-MM-DD GG:MM):");
        JTextField startTimeTextField = new JTextField(20);
        addActivityFrame.add(startTimeLabel);
        addActivityFrame.add(startTimeTextField);

        JLabel endTimeLabel = new JLabel("Data zakończenia (RRRR-MM-DD GG:MM):");
        JTextField endTimeTextField = new JTextField(20);
        addActivityFrame.add(endTimeLabel);
        addActivityFrame.add(endTimeTextField);

        JLabel roomLabel = new JLabel("Pokój:");
        JComboBox<GymRoom> roomComboBox = new JComboBox<>(DataBase.deserializeRooms().toArray(new GymRoom[0]));
        addActivityFrame.add(roomLabel);
        addActivityFrame.add(roomComboBox);

        JButton addButton = new JButton("Dodaj");
        addButton.addActionListener(e -> {
            String name = nameTextField.getText();
            String startTimeString = startTimeTextField.getText();
            String endTimeString = endTimeTextField.getText();

            LocalDateTime startTime = LocalDateTime.parse(startTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime endTime = LocalDateTime.parse(endTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            GymRoom selectedRoom = (GymRoom) roomComboBox.getSelectedItem();

            Activity activity = new Activity(name, startTime, endTime, worker, selectedRoom);

            List<Activity> activityList = DataBase.deserializeActivities();

            activityList.add(activity);
            db.serializeActivities(activityList);

            selectedRoom.addActivity(activity);
            List<GymRoom> rooms = DataBase.deserializeRooms();
            rooms.remove(selectedRoom);
            rooms.add(selectedRoom);
            db.serializeRooms(rooms);

            JOptionPane.showMessageDialog(addActivityFrame, "Zajęcia zostały dodane!.");
            addActivityFrame.dispose();
        });
        addActivityFrame.add(addButton);

        addActivityFrame.setVisible(true);
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
