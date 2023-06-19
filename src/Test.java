import model.management.DataBase;
import model.user.User;
import model.user.ClubMember;
import model.user.Worker;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Test implements Serializable {

    public static void main(String[] args) {
        try {
            FileInputStream fileIn = new FileInputStream("src/files/users.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object obj = in.readObject();

            if (obj instanceof DataBase) {
                DataBase db = (DataBase) obj;

                // Teraz możesz manipulować odczytanymi użytkownikami
                for (User user : db.getUsers()) {
                    if (user instanceof ClubMember) {
                        ClubMember member = (ClubMember) user;
                        System.out.println("Odczytany obiekt: " + member + " " + member.getId());
                    }
                }
                for (User user : db.getUsers()) {
                    if (user instanceof Worker) {
                        Worker worker = (Worker) user;
                        System.out.println("Odczytany obiekt: " + worker + " " + worker.getId() + " Pensja:" + worker.getSalary());
                    }
                }

            }

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
