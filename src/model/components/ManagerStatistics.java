package model.components;

import model.gym.Activity;
import model.management.DataBase;
import model.user.ClubMember;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ManagerStatistics extends JFrame {
    private DataBase db;
    private List<ClubMember> clubMembers;

    public ManagerStatistics(DataBase db) {
        this.db = db;

        setTitle("Statystyki Managera");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
    }

    public void createAndShowGUI() {
        JLabel titleLabel = new JLabel("Statystyki Managera");
        add(titleLabel, BorderLayout.NORTH);

        JPanel statisticsPanel = createStatisticsPanel();
        add(statisticsPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createStatisticsPanel() {
        JPanel statisticsPanel = new JPanel();
        statisticsPanel.setLayout(new BorderLayout());

        String[] columnNames = {"Statystyka", "Wartość"};
        Object[][] data = {
                {"Średnia ilość klubowiczów zapisanych na zajęcia jednego dnia", getAverageActivityPerDay()},
                {"Stosunek opłaconych do nieopłaconych klubowiczy.", getPaidToUnpaidRatio()},
                {"Średnia ilość klubowiczy zapisanych na podany typ zajęć", getAverageActivityForRoom()}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        statisticsPanel.add(scrollPane, BorderLayout.CENTER);

        return statisticsPanel;
    }

    private double getAverageActivityPerDay() {
        //TODO: Implement function to calculate average activities per day
        return 0;
    }

    private String getPaidToUnpaidRatio() {
        clubMembers = db.getClubMembers();
        long paidMembers = clubMembers.stream()
                .filter(ClubMember::isPaid)
                .count();

        long unpaidMembers = clubMembers.size() - paidMembers;

        return paidMembers + " : " + unpaidMembers;
    }

    private Map<String, Double> getAverageActivityForRoom() {
        Map<String, List<Activity>> roomActivitiesMap = new HashMap<>();

        for (ClubMember member : clubMembers) {
            for (Activity activity : member.getActivities()) {
                String roomName = activity.getRoom().getName();
                if (!roomActivitiesMap.containsKey(roomName)) {
                    roomActivitiesMap.put(roomName, new ArrayList<>());
                }
                roomActivitiesMap.get(roomName).add(activity);
            }
        }

        return roomActivitiesMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (double) entry.getValue().size() / clubMembers.size()
                ));
    }
}
