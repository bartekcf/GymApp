package model.components;

import model.gym.Activity;
import model.gym.GymRoom;
import model.user.ClubMember;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClubMemberStatistics extends JFrame {
    private ClubMember clubMember;
    private List<Activity> activities;

    public ClubMemberStatistics(ClubMember clubMember, List<Activity> activities) {
        this.clubMember = clubMember;
        this.activities = activities;

        setTitle("Statystyki użytkownika");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        createAndShowGUI();
    }

    public void createAndShowGUI() {
        JLabel nameLabel = new JLabel(clubMember.getFirstName() + " " + clubMember.getLastName());
        add(nameLabel, BorderLayout.NORTH);

        JPanel statisticsPanel = createStatisticsPanel();
        add(statisticsPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createStatisticsPanel() {
        JPanel statisticsPanel = new JPanel();
        statisticsPanel.setLayout(new BorderLayout());

        String[] columnNames = {"Statystyka", "Wartość"};
        Object[][] data = {
                {"Liczba wszystkich zajęć", getTotalActivityCount()},
                {"Całkowity czas spędzony na zajęciach", formatDuration(getTotalTimeSpent())},
                {"Najczęściej odwiedzane pokoje", getMostFrequentRooms()}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        statisticsPanel.add(scrollPane, BorderLayout.CENTER);

        return statisticsPanel;
    }

    private int getTotalActivityCount() {
        return activities.size();
    }

    private Duration getTotalTimeSpent() {
        Duration totalTime = Duration.ZERO;

        for (Activity activity : activities) {
            LocalDateTime startTime = activity.getStartTime();
            LocalDateTime endTime = activity.getEndTime();

            int startHour = startTime.getHour();
            int endHour = endTime.getHour();

            if (startHour < endHour) {
                Duration activityDuration = Duration.between(startTime, endTime);
                totalTime = totalTime.plus(activityDuration);
            }
        }

        return totalTime;
    }


    private String getMostFrequentRooms() {
        Map<String, Integer> roomCounts = new HashMap<>();

        for (Activity activity : activities) {
            GymRoom room = activity.getRoom();
            if (room != null) {
                String roomName = room.getName();
                roomCounts.put(roomName, roomCounts.getOrDefault(roomName, 0) + 1);
            }
        }

        StringBuilder roomsText = new StringBuilder();
        for (Map.Entry<String, Integer> entry : roomCounts.entrySet()) {
            String roomName = entry.getKey();
            int count = entry.getValue();
            roomsText.append(roomName).append(" (").append(count).append(" razy)").append(", ");
        }

        // Usuń ostatni przecinek i spację
        if (roomsText.length() > 2) {
            roomsText.setLength(roomsText.length() - 2);
        }

        return roomsText.toString();
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();

        return hours + " godz. " + minutes + " min.";
    }
}
