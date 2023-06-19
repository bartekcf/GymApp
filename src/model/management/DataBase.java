package model.management;

import model.user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private List<User> users = new ArrayList<>();
    public static final String MAIN_FOLDER = "src/files/";
    public static final String USERS_FILE = MAIN_FOLDER + "users.ser";

    public void addUser(User user) {
        this.users.add(user);
        serialize();
    }

    public void serialize() {
        try {
            FileOutputStream fileOut = new FileOutputStream(USERS_FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static DataBase deserialize() {
        DataBase db = null;
        try {
            FileInputStream fileIn = new FileInputStream(USERS_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            db = (DataBase) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return new DataBase();
        } catch (ClassNotFoundException c) {
            System.out.println("DataBase class not found");
            c.printStackTrace();
            return new DataBase();
        }
        return db;
    }

    public List<User> getUsers() {
        return this.users;
    }
}
