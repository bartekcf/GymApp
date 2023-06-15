package model.management;

import model.components.MainPanel;
import model.components.MainPanelManager;
import model.components.MainPanelWorker;
import model.gym.Activity;
import model.gym.GymRoom;
import model.user.ClubMember;
import model.user.Manager;
import model.user.User;
import model.user.Worker;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase implements Serializable {
    public static final String MAIN_FOLDER = "src/files/";
    public static final String CLUB_MEMBERS_FILE = MAIN_FOLDER + "club_members.txt";
    public static final String WORKERS_FILE = MAIN_FOLDER + "workers.txt";
    public static final String MANAGERS_FILE = MAIN_FOLDER + "managers.txt";
    public static final String ACTIVITIES_FILE = MAIN_FOLDER + "activities.txt";
    public static final String GYM_ROOMS_FILE = MAIN_FOLDER + "gym_rooms.txt";
    public static final String LOGIN_DATA = MAIN_FOLDER + "login_data.txt";

    private ManagementSystem managementSystem;
    private List<ClubMember> clubMembers;
    private List<Worker> workers;
    private List<Manager> managers;
    private List<Activity> activities;
    private List<GymRoom> gymRooms;

    public DataBase(ManagementSystem managementSystem) {
        this.managementSystem = managementSystem;
        this.clubMembers = new ArrayList<>();
        this.workers = new ArrayList<>();
        this.managers = new ArrayList<>();
        this.activities = new ArrayList<>();
        this.gymRooms = new ArrayList<>();
    }

    public ClubMember findClubMember(String username) {
        for (ClubMember clubMember : clubMembers) {
            if (clubMember.getClubMemberLogin().equals(username)) {
                return clubMember;
            }
        }
        return null;
    }

    public Worker findWorker(String username) {
        for (Worker worker : workers) {
            if (worker.getWorkerLogin().equals(username)) {
                return worker;
            }
        }
        return null;
    }

    public Manager findManager(String username) {
        for (Manager manager : managers) {
            if (manager.getManagerLogin().equals(username)) {
                return manager;
            }
        }
        return null;
    }

    public List<ClubMember> getClubMembers() {
        return clubMembers;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public List<Manager> getManagers() {
        return managers;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public List<GymRoom> getGymRooms() {
        return gymRooms;
    }

    public void saveData() {
        saveClubMembers();
        saveWorkers();
        saveManagers();
        saveActivities();
        saveGymRooms();
    }

    private void saveClubMembers() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(CLUB_MEMBERS_FILE))) {
            out.writeObject(clubMembers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveWorkers() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(WORKERS_FILE))) {
            out.writeObject(workers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveManagers() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(MANAGERS_FILE))) {
            out.writeObject(managers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveActivities() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ACTIVITIES_FILE))) {
            out.writeObject(activities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveGymRooms() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(GYM_ROOMS_FILE))) {
            out.writeObject(gymRooms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        loadClubMembers();
        loadWorkers();
        loadManagers();
        loadActivities();
        loadGymRooms();
        loadAllData();
    }

    private void loadClubMembers() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(CLUB_MEMBERS_FILE))) {
            clubMembers = (List<ClubMember>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadWorkers() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(WORKERS_FILE))) {
            workers = (List<Worker>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadManagers() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(MANAGERS_FILE))) {
            managers = (List<Manager>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadActivities() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ACTIVITIES_FILE))) {
            activities = (List<Activity>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadGymRooms() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(GYM_ROOMS_FILE))) {
            gymRooms = (List<GymRoom>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //jebac to gowno @todo zrobic gownob

    public User loadLoginData(String username, String password, String selectedRole) {
        User user = null;

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Niepoprawne dane logowania!");
            return null;
        }

        switch (selectedRole) {
            case User.ROLE_CLUB_MEMBER -> {
                return findClubMember(username);
            }
            case User.ROLE_WORKER -> {
                return findWorker(username);
            }
            case User.ROLE_MANAGER -> {
                return findManager(username);
            }
        }
        return null;
    }

    public void loadAllData() {
        List<String[]> allDataList = new ArrayList<>(); // Lista przechowująca wszystkie dane

        try (BufferedReader reader = new BufferedReader(new FileReader(LOGIN_DATA))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                allDataList.add(data); // Dodawanie danych do listy allDataList
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(User user) {
        if (user instanceof ClubMember) {
            saveClubMember((ClubMember) user);
        } else if (user instanceof Worker) {
            saveWorker((Worker) user);
        } else if (user instanceof Manager) {
            saveManager((Manager) user);
        }

        saveData();
    }

    private void saveClubMember(ClubMember clubMember) {
        clubMembers.add(clubMember);
    }

    private void saveWorker(Worker worker) {
        workers.add(worker);
    }

    private void saveManager(Manager manager) {
        managers.add(manager);
    }
}
