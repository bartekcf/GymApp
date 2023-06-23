package model;

import model.gym.GymRoom;
import model.management.DataBase;
import model.management.GraphicalUserInterface;
import model.management.ManagementSystem;
import model.user.ClubMember;
import model.user.Manager;
import model.user.User;
import model.user.Worker;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {


        ClubMember mirek = new ClubMember("Mirek", "Kowalczuk", "Miras", "Haslo123", LocalDate.of(2002, 8, 17), User.ROLE_WORKER);
        Manager mirekmen = new Manager("Mirek", "Kowalczuk", "Miras", "Haslo123", LocalDate.of(2002, 8, 17), 2000, "Energiczny", User.ROLE_WORKER);
        Worker mirekprac = new Worker("Mirek", "Kowalczuk", "Miras", "Haslo123", LocalDate.of(2002, 8, 17), 100, User.ROLE_WORKER);
        ClubMember marek = new ClubMember("Marek", "Kowalczuk", "Mira11s", "Ha2slo123", LocalDate.of(2002, 8, 17), User.ROLE_WORKER);
        Manager marekmen = new Manager("Marek", "Kowalczuk", "Mira11s", "Ha2slo123", LocalDate.of(2002, 8, 17), 500, "Agresywny", User.ROLE_WORKER);
        Worker marekprac = new Worker("Marek", "Kowalczuk", "Mira11s", "Ha2slo123", LocalDate.of(2002, 8, 17), 100, User.ROLE_WORKER);


        GymRoom room1 = new GymRoom("Fitness", 1);
        GymRoom room2 = new GymRoom("Aerobik", 2);
        GymRoom room3 = new GymRoom("Siłownia", 3);
        GymRoom room4 = new GymRoom("Cycling", 4);
        GymRoom room5 = new GymRoom("Taniec", 5);
        GymRoom room6 = new GymRoom("Stretching", 6);
        GymRoom room7 = new GymRoom("Boks", 7);
        GymRoom room8 = new GymRoom("Joga", 8);

        ManagementSystem managementSystem = new ManagementSystem();

        DataBase db = managementSystem.getDb();

        db.addRoom(room1);
        db.addRoom(room2);
        db.addRoom(room3);
        db.addRoom(room4);
        db.addRoom(room5);
        db.addRoom(room6);
        db.addRoom(room7);
        db.addRoom(room8);

        db.serializeRooms(db.getGymRooms());
        db.setGymRooms(DataBase.deserializeRooms());
        
        GraphicalUserInterface GUI = new GraphicalUserInterface(managementSystem);
        GUI.createAndShowGUI();
    }
}
