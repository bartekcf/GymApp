package model.management;

import model.components.MainPanel;
import model.gym.Activity;
import model.gym.GymRoom;
import model.user.ClubMember;
import model.user.Manager;
import model.user.Worker;

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
//        loadLoginData();
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

    public void loadLoginData(String username, String password) {
        List<String[]> loginDataList = new ArrayList<>(); // Lista przechowująca dane logowania

        try (BufferedReader reader = new BufferedReader(new FileReader(LOGIN_DATA))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] loginData = line.split(",");
                loginDataList.add(loginData); // Dodawanie danych logowania do listy loginDataList
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sprawdź, czy wprowadzone dane zgadzają się z danymi logowania
        for (String[] loginData : loginDataList) {
            if (loginData[0].equals(username) && loginData[1].equals(password)) {
                // Jeśli dane logowania są poprawne, przenieś się do nowego okna
                EventQueue.invokeLater(() -> {
                    MainPanel mainPanel = new MainPanel(managementSystem);
                    mainPanel.createAndShowGUI();
                });
                return; // Zakończ funkcję po przeniesieniu do nowego okna
            }
        }

        // Jeśli dane logowania są niepoprawne, wyświetl komunikat lub wykonaj inne działania
        System.out.println("Niepoprawne dane logowania!");
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

        // Tutaj możesz wykorzystać listę allDataList do dalszej obróbki wszystkich danych
    }

}
