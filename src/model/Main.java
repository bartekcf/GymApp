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


//        System.out.println(mirek.getId());
//        System.out.println(marek.getId());
//        System.out.println(mirekmen.getId());
//        System.out.println(marekmen.getId());
//        System.out.println(marekprac.getId());
//        System.out.println(mirekprac.getId());

//        System.out.println(mirekprac.getSalary());
//        mirekprac.setSalary(5000);
//        System.out.println(mirekprac.getSalary());
//
//        System.out.println(marekmen.getManagementStyle());

//        Activity aktywnosc1 = new Activity("Jogging", LocalDate.of(2023, 6, 12).atStartOfDay(), LocalDate.of(2023, 6, 12).atStartOfDay(), marekprac);
//
//        aktywnosc1.addToActivity(mirek);
//        aktywnosc1.addToActivity(marek);


//        System.out.println(aktywnosc1.getWorker());
//        System.out.println(aktywnosc1.getName());
//        System.out.println(aktywnosc1.getMembers());
//
//        aktywnosc1.removeFromActivity(marek);
//
//        System.out.println(aktywnosc1.getMembers());

//        GymRoom pokoj222 = new GymRoom("Jogging Room", 222);
//
////        pokoj222.addActivity(aktywnosc1);
//        System.out.println(pokoj222.getName() + " " + pokoj222.getRoomNumber());
//
//        System.out.println(pokoj222.getActivities());


//        DataBase database = new DataBase(managementSystem);

//        database.getClubMembers().add(marek);
//        database.saveData();

//        database.loadData();

//        System.out.println("Club Members: " + database.getClubMembers());
//        System.out.println("Workers: " + database.getWorkers());
//        System.out.println("Managers: " + database.getManagers());
//        System.out.println("Activities: " + database.getActivities());
//        System.out.println("Gym Rooms: " + database.getGymRooms());

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
