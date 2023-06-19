import model.user.ClubMember;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Test {
    public static void main(String[] args) {
        try {
            FileInputStream fileIn = new FileInputStream("src/files/club_members.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object obj = in.readObject();

            if (obj instanceof ClubMember) {
                ClubMember member = (ClubMember) obj;
                // Manipuluj odczytanym obiektem
                System.out.println("Odczytany obiekt: " + member.toString());
            }

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
