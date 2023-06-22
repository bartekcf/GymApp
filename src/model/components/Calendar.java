package model.components;

import model.gym.Activity;
import model.management.DataBase;
import model.user.ClubMember;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Calendar {
    private final DataBase db;
    private final ClubMember clubMember;
    private final int frameWidth;
    private final int frameHeight;
    private YearMonth currentMonth;
    private List<Activity> selectedActivities;
    private List<Activity> activities;
    private JFrame frame;


    public Calendar(DataBase db, ClubMember clubMember, int frameWidth, int frameHeight, JFrame frame) {
        this.db = db;
        this.clubMember = clubMember;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.currentMonth = YearMonth.now();
        this.selectedActivities = getClubMemberActivities();
        this.frame = frame;
    }

    public YearMonth getCurrentMonth() {
        return currentMonth;
    }

    public void nextMonth() {
        currentMonth = currentMonth.plusMonths(1);
        selectedActivities = getClubMemberActivities();
    }

    public void previousMonth() {
        currentMonth = currentMonth.minusMonths(1);
        selectedActivities = getClubMemberActivities();
    }

    public JPanel createMonthPanel() {
        JPanel monthPanel = new JPanel(new BorderLayout());
        JPanel daysPanel = new JPanel(new GridLayout(0, 7));
        LocalDate firstDayOfMonth = currentMonth.atDay(1);
        int firstDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue() % 7;
        int daysInMonth = currentMonth.lengthOfMonth();

        JLabel monthLabel = new JLabel(currentMonth.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault()).toUpperCase());
        monthLabel.setHorizontalAlignment(JLabel.CENTER);
        monthLabel.setFont(new Font("Arial", Font.BOLD, 24));
        monthPanel.add(monthLabel, BorderLayout.NORTH); // Add month label to the panel

        for (int i = 1; i <= firstDayOfWeek; i++) {
            daysPanel.add(new JLabel());
        }

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = currentMonth.atDay(day);
            List<Activity> activities = getActivitiesForDay(date);
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.setPreferredSize(new Dimension(frameWidth / 7, frameHeight / 7)); // Adjust button size
            dayButton.setBackground(activities.isEmpty() ? Color.WHITE : Color.GREEN); // Set background color
            dayButton.addActionListener(e -> {
                List<Activity> activitiesForDay = getActivitiesForDay(date);
                if (!activitiesForDay.isEmpty()) {
                    ActivityDetailsDialog activityDetailsDialog = new ActivityDetailsDialog(frame, "Szczegóły aktywności", true, activitiesForDay);
                    activityDetailsDialog.setVisible(true);
                }
            });
            daysPanel.add(dayButton);
        }

        monthPanel.add(daysPanel, BorderLayout.CENTER);

        return monthPanel;
    }

    private List<Activity> getClubMemberActivities() {
        activities = DataBase.deserializeActivities();
        return selectedActivities = activities.stream()
                .filter(activity -> activity.getClubMembers().stream().anyMatch(cm -> cm.getId() == clubMember.getId()))
                .collect(Collectors.toList());
    }

    private List<Activity> getActivitiesForDay(LocalDate date) {
        return selectedActivities.stream()
                .filter(activity -> activity.getStartTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }
}