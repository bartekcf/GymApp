
import model.gym.Activity;
import model.management.DataBase;
import model.user.ClubMember;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;

public class Test implements Serializable {

    public static void main(String[] args) {
        try {
            FileInputStream fileIn = new FileInputStream(DataBase.ACTIVITIES_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            List<Activity> activities = (List<Activity>) in.readObject();
            in.close();
            fileIn.close();

            for (Activity activity : activities) {
                System.out.println("Aktywność: " + activity);
                System.out.println("Członkowie:");
                for (ClubMember member : activity.getClubMembers()) {
                    System.out.println(member);
                }
                System.out.println("----------");
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
