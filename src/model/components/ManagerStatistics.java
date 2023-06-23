package model.components;

import model.gym.Activity;
import model.gym.GymRoom;
import model.management.DataBase;
import model.user.ClubMember;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ManagerStatistics extends JFrame {
    private DataBase db;
    private List<ClubMember> clubMembers;

    public ManagerStatistics(DataBase db) {
        this.db = db;
        this.clubMembers = db.getClubMembers(); // Aktualizacja listy członków klubu

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
        statisticsPanel.setLayout(new BoxLayout(statisticsPanel, BoxLayout.Y_AXIS));

        // Create two table models
        DefaultTableModel model1 = new DefaultTableModel(new Object[][]{
                {"Średnia ilość klubowiczy zapisanych na zajęcia jednego dnia", getAverageActivityPerDay()},
                {"Stosunek nieopłaconych do opłaconych klubowiczy.", getPaidToUnpaidRatio()},

        }, new String[]{"Statystyka", "Wartość"});

        DefaultTableModel model2 = new DefaultTableModel(new String[]{"Nazwa zajęć", "Średnia liczba zapisanych"}, 0);

        Map<String, Double> averageActivityForRoom = getAverageActivityForRoom();

        for (Map.Entry<String, Double> entry : averageActivityForRoom.entrySet()) {
            model2.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }

        // Create two tables and put them into scroll panes
        JTable table1 = new JTable(model1);
        JScrollPane scrollPane1 = new JScrollPane(table1);

        JTable table2 = new JTable(model2);
        JScrollPane scrollPane2 = new JScrollPane(table2);

        // Add scroll panes to panel
        statisticsPanel.add(scrollPane1);
        statisticsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add a spacer between the two tables
        statisticsPanel.add(scrollPane2);

        return statisticsPanel;
    }

    private double getAverageActivityPerDay() {
        updateClubMembers();
        List<Activity> activities = DataBase.deserializeActivities(); // Wczytaj aktywności z bazy danych

        long totalActivities = activities.size();
        long totalDays = clubMembers.stream()
                .flatMap(member -> member.getActivities().stream())
                .map(Activity::getStartTime)
                .map(LocalDate::from)
                .distinct()
                .count();

        if (totalDays == 0) {
            return 0;
        }

        return (double) totalActivities / totalDays;
    }

    private String getPaidToUnpaidRatio() {
        updateClubMembers(); // Aktualizacja listy członków klubu

        long paidMembers = clubMembers.stream()
                .filter(ClubMember::isPaid)
                .count();

        long unpaidMembers = clubMembers.size() - paidMembers;

        return paidMembers + " : " + unpaidMembers;
    }

    private Map<String, Double> getAverageActivityForRoom() {
        List<GymRoom> allRooms = DataBase.deserializeRooms();

        Map<String, Double> roomCountMap = allRooms.stream()
                .collect(Collectors.toMap(
                        GymRoom::getName,
                        room -> {
                            long count = clubMembers.stream()
                                    .flatMap(member -> member.getActivities().stream())
                                    .filter(activity -> Objects.equals(activity.getRoom().getName(), room.getName()))
                                    .count();
                            return count > 0 ? 1.0 : 0.0;
                        }
                ));

        double totalMembers = clubMembers.size();
        roomCountMap.replaceAll((room, count) -> count / totalMembers);

        return roomCountMap;
    }

    private void updateClubMembers() {
        db = DataBase.deserialize();
        clubMembers = db.getClubMembers();
        List<Activity> activities = DataBase.deserializeActivities(); // Wczytaj aktywności z bazy danych
        for (ClubMember member : clubMembers) {
            List<Activity> memberActivities = activities.stream()
                    .filter(activity -> activity.getClubMembers().stream().anyMatch(clubMember -> clubMember.getId() == member.getId()))
                    .toList();
            memberActivities.forEach(member::signUpForActivity);
        }
    }
}
