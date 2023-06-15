package model.user;

import model.gym.Activity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Worker extends User implements Serializable {
    private static int nextId = 1;
    private int id;
    private double salary;
    private List<Activity> activities;  // lista zajęć, które pracownik prowadzi

    public Worker(String firstName, String lastName, String login, String password, LocalDate birthDay, double salary, String userRole) {
        super(firstName, lastName, login, password, birthDay, userRole);
        this.id = nextId++;
        this.salary = salary;
        this.activities = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        this.activities.remove(activity);
    }

    public String getWorkerLogin()
    {
        return this.getLogin();
    }

}
