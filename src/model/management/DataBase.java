package model.management;

import model.gym.Activity;
import model.gym.GymRoom;
import model.user.ClubMember;
import model.user.Manager;
import model.user.User;
import model.user.Worker;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase implements Serializable {
    public static final String MAIN_FOLDER = "src/files/";
    public static final String USERS_FILE = MAIN_FOLDER + "users.ser";

    private List<User> users;
    private List<Activity> activities;
    private List<GymRoom> gymRooms;

    public DataBase() {
        this.users = new ArrayList<>();
        this.activities = new ArrayList<>();
        this.gymRooms = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
        saveData();
    }

    public List<User> getUsers() {
        return users;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void addGymRoom(GymRoom gymRoom) {
        gymRooms.add(gymRoom);
    }

    public List<GymRoom> getGymRooms() {
        return gymRooms;
    }

    public void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            out.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            users = (List<User>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Map<String, ObjectInputStream> loadSerializedFiles() {
        Map<String, ObjectInputStream> fileStreams = new HashMap<>();
        try {
            ObjectInputStream usersInputStream = new ObjectInputStream(new FileInputStream(USERS_FILE));
            fileStreams.put(USERS_FILE, usersInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileStreams;
    }
}
