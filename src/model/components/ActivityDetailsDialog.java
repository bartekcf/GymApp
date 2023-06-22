package model.components;

import model.gym.Activity;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ActivityDetailsDialog extends JDialog {
    public ActivityDetailsDialog(Frame parent, String title, boolean modal, List<Activity> activities) {
        super(parent, title, modal);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        for (int i = 0; i < activities.size(); i++) {
            Activity activity = activities.get(i);
            JLabel nameLabel = new JLabel("Nazwa: " + activity.getName());
            JLabel timeLabel = new JLabel("Godzina: " + formatTime(activity.getStartTime()) + " - " + formatTime(activity.getEndTime()));
            JLabel worker = new JLabel("Trener: " + activity.getWorker().toString());
            JLabel gymRoom = new JLabel("Sala: " + activity.getRoom().toString());
            gbc.gridx = 0;
            gbc.gridy = i * 4;
            contentPanel.add(nameLabel, gbc);
            gbc.gridy = i * 4 + 1;
            contentPanel.add(timeLabel, gbc);
            gbc.gridy = i * 4 + 2;
            contentPanel.add(worker, gbc);
            gbc.gridy = i * 4 + 3;
            contentPanel.add(gymRoom, gbc);
        }

        add(contentPanel, BorderLayout.CENTER);
    }

    private String formatTime(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
