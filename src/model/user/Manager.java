package model.user;

import java.io.Serializable;
import java.time.LocalDate;

public class Manager extends User  implements Serializable {
    private double salary;
    private String managementStyle;

    public Manager(String firstName, String lastName, String login, String password, LocalDate birthDay, double salary, String managementStyle, String userRole) {
        super(firstName, lastName, login, password, birthDay, userRole);
        this.salary = salary;
        this.managementStyle = managementStyle;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getManagementStyle() {
        return managementStyle;
    }

    public void setManagementStyle(String managementStyle) {
        this.managementStyle = managementStyle;
    }
    public String getManagerLogin()
    {
        return this.getLogin();
    }


    // inne metody...
}
