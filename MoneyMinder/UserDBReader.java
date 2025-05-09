import java.io.*;
import java.util.List;

public class UserDBReader {
    public static void main(String[] args) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("users.db"))) {
            List<User> users = (List<User>) in.readObject();
            System.out.println("=== Users in Database ===");
            for (User user : users) {
                System.out.println("\nUsername: " + user.getUsername());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Created At: " + user.getCreatedAt());
                System.out.println("Last Login: " + user.getLastLogin());
                System.out.println("------------------------");
            }
        } catch (FileNotFoundException e) {
            System.out.println("users.db file not found. No users have been created yet.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading users.db: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 