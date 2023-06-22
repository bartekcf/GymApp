package model.components;

import model.gym.Activity;
import model.management.DataBase;
import model.user.User;
import model.user.Worker;

import javax.swing.*;
import java.awt.*;

public class WorkerSelectionForm extends JDialog {
    private final DataBase db;
    private final Activity activity;
    private Worker selectedWorker; // przechowuje wybranego pracownika
    private boolean isGUIInitialized = false; // flaga informująca, czy GUI zostało już zainicjalizowane

    public WorkerSelectionForm(DataBase db, Activity activity) {
        this.db = db;
        this.activity = activity;

        setTitle("Wybór pracownika");
        setSize(300, 200);
        setLayout(new FlowLayout());
        setVisible(true);
    }

    public void createAndShowGUI() {
        JComboBox<String> workerComboBox = new JComboBox<>();
        for (User user : db.getUsers()) {
            if (user instanceof Worker) {
                workerComboBox.addItem(((Worker) user).getWorkerLogin());
            }
        }

        JButton confirmButton = new JButton("Potwierdź");
        confirmButton.addActionListener(e -> {
            String selectedWorkerLogin = (String) workerComboBox.getSelectedItem();
            for (User user : db.getUsers()) {
                if (user instanceof Worker && ((Worker) user).getWorkerLogin().equals(selectedWorkerLogin)) {
                    selectedWorker = (Worker) user;
                    db.addWorker(selectedWorker, activity);
//                    activity.setWorker(selectedWorker);
                    selectedWorker.addActivity(activity);
                    setVisible(false);
                    break;
                }
            }
        });

        add(workerComboBox);
        add(confirmButton);
    }

    // metoda getter zwracająca wybranego pracownika
    public Worker getSelectedWorker() {
        return selectedWorker;
    }
}