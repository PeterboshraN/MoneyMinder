import java.io.*;
import java.util.*;
import java.time.LocalDateTime;

public class AuthService {
    private static final String FILE_PATH = "users.db";
    private static final Scanner scanner = new Scanner(System.in);
    private static List<User> users;

    static {
        users = loadUsers();
    }

    private static List<User> loadUsers() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<User>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading users: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static void saveUsers() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(users);
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    private static void signUp() {
            System.out.println("Email must contain @\nUsername must contain only letters and numbers\nPassword must contain at least one uppercase letter, one lowercase letter, and one number\nPassword must be at least 8 characters long");
        try {
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Username: ");
            String username = scanner.nextLine().trim();

            System.out.print("Password: ");
            String password = scanner.nextLine().trim();

            if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                throw new IllegalArgumentException("All fields are required.");
            }

            // Validate username and email uniqueness
            for (User user : users) {
                if (user.getUsername().equalsIgnoreCase(username)) {
                    throw new IllegalArgumentException("Username already exists.");
                }
                if (user.getEmail().equalsIgnoreCase(email)) {
                    throw new IllegalArgumentException("Email already exists.");
                }
            }

            // Create new user with strategies
            User newUser = new User(
                username,
                email,
                new BasicEmailStrategy(),
                password,
                new BasicPasswordStrategy()
            );

            users.add(newUser);
            saveUsers();

            System.out.println("Sign-up successful. Please log in.");
        } catch (IllegalArgumentException e) {
            System.out.println("Sign-up failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Sign-up failed: An unexpected error occurred.");
            e.printStackTrace();
        }
    }

    private static void login() {
        try {
            System.out.print("Username: ");
            String username = scanner.nextLine().trim();

            System.out.print("Password: ");
            String password = scanner.nextLine().trim();

            if (username.isEmpty() || password.isEmpty()) {
                throw new IllegalArgumentException("Username and password are required.");
            }

            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    user.setLastLogin(LocalDateTime.now());
                    saveUsers();
                    System.out.println("Login successful. Welcome " + user.getUsername() + "!");
                    return;
                }
            }

            throw new IllegalArgumentException("Invalid username or password.");
        } catch (IllegalArgumentException e) {
            System.out.println("Login failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Login failed: An unexpected error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("\n=== MENU ===\n1. Sign Up\n2. Login\n3. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1": signUp(); break;
                    case "2": login(); break;
                    case "3": 
                        System.out.println("Goodbye!");
                        scanner.close();
                        return;
                    default: System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
} 