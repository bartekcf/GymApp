package model.user;

import java.io.Serializable;
import java.time.LocalDate;

public class Manager extends User  implements Serializable {
    private double salary;
    private String managementStyle;
    private String message;

    public Manager(String firstName, String lastName, String login, String password, LocalDate birthDay, double salary, String managementStyle, String userRole) {
        super(firstName, lastName, login, password, birthDay, userRole);
        this.salary = salary;
        this.managementStyle = managementStyle;
        this.message = "";
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setManagementStyle(String managementStyle) {
        this.managementStyle = managementStyle;
    }
    public String getManagerLogin()
    {
        return this.getLogin();
    }


}
