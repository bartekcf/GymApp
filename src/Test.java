//
//import model.gym.Activity;
//import model.management.DataBase;
//import model.user.ClubMember;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.Serializable;
//import java.util.List;
//
//public class Test implements Serializable {
//
//    public static void main(String[] args) {
//        try {
//            FileInputStream fileIn = new FileInputStream(DataBase.ACTIVITIES_FILE);
//            ObjectInputStream in = new ObjectInputStream(fileIn);
//            List<Activity> activities = (List<Activity>) in.readObject();
//            in.close();
//            fileIn.close();
//
//            for (Activity activity : activities) {
//                System.out.println("Aktywność: " + activity);
//                System.out.println("Członkowie:");
//                for (ClubMember member : activity.getClubMembers()) {
//                    System.out.println(member);
//                }
//                System.out.println("----------");
//            }
//
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//}
import model.gym.Activity;
import model.management.DataBase;
import model.user.ClubMember;
import model.user.User;
import model.user.Worker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Test implements Serializable {
    private List<Activity> activities;

    public static void main(String[] args) {
        try {
            FileInputStream fileIn = new FileInputStream(DataBase.USER_GYM_PASS_STATUS);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Map<Integer, Boolean> gymPassStatus = (Map<Integer, Boolean>) in.readObject();
            in.close();
            fileIn.close();

            DataBase db = DataBase.deserialize();
            db.updateUserMembershipStatus();

            List<ClubMember> clubMembers = db.getClubMembers();
            for (ClubMember member : clubMembers) {
                boolean isPaid = gymPassStatus.getOrDefault(member.getId(), false);
                System.out.println("Członek: " + member);
                System.out.println("Opłacony karnet: " + isPaid);
                System.out.println("----------");
            }

            List<User> users = db.getUsers();

            System.out.println("Lista pracowników:");
            for (User user : users) {
                if (user instanceof Worker) {
                    System.out.println(user.getId());
                }
            }

            List<Activity> activities = DataBase.deserializeActivities();
            System.out.println("Lista aktywności:");
            for (Activity activity : activities) {
                System.out.println(activity.getWorker().getId());
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
