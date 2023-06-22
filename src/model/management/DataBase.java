package model.management;

import model.gym.Activity;
import model.gym.GymRoom;
import model.user.ClubMember;
import model.user.User;
import model.user.Worker;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private List<User> users = new ArrayList<>();
    private static Map<Integer, Boolean> gymPassStatus = new HashMap<>();
    private List<Activity> activities = new ArrayList<>();
    private List<GymRoom> gymRooms = new ArrayList<>();
    public static final String MAIN_FOLDER = "src/files/";
    public static final String USERS_FILE = MAIN_FOLDER + "users.ser";
    public static final String USER_GYM_PASS_STATUS = MAIN_FOLDER + "is_paid.ser";
    public static final String ACTIVITIES_FILE = MAIN_FOLDER + "activities.ser";
    public static final String ROOMS_FILE = MAIN_FOLDER + "gym_rooms.ser";

    public void addUser(User user) {
        this.users.add(user);
        serialize();
    }

    public void serialize() {
        try {
            FileOutputStream fileOut = new FileOutputStream(USERS_FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.users);  // zapisuj tylko listę użytkowników
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static DataBase deserialize() {
        List<User> users;
        try {
            FileInputStream fileIn = new FileInputStream(USERS_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            users = (List<User>) in.readObject();  // odczytuj tylko listę użytkowników
            in.close();
            fileIn.close();

            // Ustalanie nextId na podstawie zdeserializowanych użytkowników
            if (users != null && !users.isEmpty()) {
                int maxId = users.stream()
                        .mapToInt(User::getId)
                        .max()
                        .orElse(0);
                User.setNextId(maxId + 1);
            }
        } catch (IOException i) {
            i.printStackTrace();
            return new DataBase();
        } catch (ClassNotFoundException c) {
            System.out.println("DataBase class not found");
            c.printStackTrace();
            return new DataBase();
        }
        DataBase db = new DataBase();
        db.users = users;  // ustaw listę użytkowników dla bieżącej instancji
        return db;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void updateUserStatus(ClubMember clubMember) {
        gymPassStatus.put(clubMember.getId(), clubMember.isPaid());
        serializeUserStatus();
    }

    public void serializeUserStatus() {
        try {
            FileOutputStream fileOut = new FileOutputStream(USER_GYM_PASS_STATUS);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.gymPassStatus); // zapisuj tylko gymPassStatus
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static DataBase deserializeUserStatus() {
        try {
            FileInputStream fileIn = new FileInputStream(USER_GYM_PASS_STATUS);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            gymPassStatus = (Map<Integer, Boolean>) in.readObject();
            in.close();
            fileIn.close();

        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Nie znaleziono klasy");
            c.printStackTrace();
        }
        return new DataBase();
    }

    public Map<Integer, Boolean> getMembershipStatus() {
        return gymPassStatus;
    }

    public void updateUserMembershipStatus() {
        DataBase membershipStatusDb = DataBase.deserializeUserStatus();

        // Aktualizacja statusów opłacenia dla użytkowników
        for (User user : this.users) {
            if (user instanceof ClubMember) {
                Boolean isPaid = membershipStatusDb.getMembershipStatus().get(user.getId());
                if (isPaid != null) {
                    ((ClubMember) user).setPaid(isPaid);
                }
            }
        }
    }

    public void serializeActivities(List<Activity> activities) {
        try {
            FileOutputStream fileOut = new FileOutputStream(ACTIVITIES_FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(activities);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static List<Activity> deserializeActivities() {
        List<Activity> activities;
        try {
            FileInputStream fileIn = new FileInputStream(ACTIVITIES_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            activities = (List<Activity>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return activities;
    }

    public List<GymRoom> getGymRooms() {
        return this.gymRooms;
    }

    public void addRoom(GymRoom room) {
        this.gymRooms.add(room);
    }

    public void serializeRooms(List<GymRoom> rooms) {
        try {
            FileOutputStream fileOut = new FileOutputStream(ROOMS_FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(rooms);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static List<GymRoom> deserializeRooms() {
        List<GymRoom> rooms;
        try {
            FileInputStream fileIn = new FileInputStream(ROOMS_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            rooms = (List<GymRoom>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        // Pobierz listę aktywności z bazy danych
        List<Activity> activities = DataBase.deserializeActivities();

        for (GymRoom room : rooms) {
            List<Activity> roomActivities = new ArrayList<>();
            for (Activity activity : activities) {
                if (activity.getRoom().equals(room)) {
                    roomActivities.add(activity);
                }
            }
            room.setActivities(roomActivities);
        }

        return rooms;
    }

    public List<Activity> getActivities() {
        return this.activities;
    }

    public void removeActivity(int activityId) {
        activities = DataBase.deserializeActivities();
        activities.removeIf(a -> a.getId() == activityId);
        serializeActivities(activities);
    }

    public void addWorker(Worker worker, Activity activity) {
        activity.addWorker(worker);
        activities = DataBase.deserializeActivities();
        activities.stream()
                .filter(a -> a.getId() == activity.getId())
                .forEach(a -> a.setWorker(activity.getWorker()));
        serializeActivities(activities);
    }

    public void removeWorker(Worker worker, Activity activity) {
        activity.removeWorker(worker);
        serializeActivities(activities);
    }
    public void removeClubMember(ClubMember clubMember, Activity activity) {
        activity.removeClubMember(clubMember);
        serializeActivities(activities);
    }

    public List<ClubMember> getClubMembers() {
        List<ClubMember> clubMembers = new ArrayList<>();
        for (User user : this.users) {
            if (user instanceof ClubMember) {
                clubMembers.add((ClubMember) user);
            }
        }
        return clubMembers;
    }
}
