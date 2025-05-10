import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for the Finance Manager application. Handles user authentication, main menu, and core features.
 */
public class FinanceManager {
    private static User user;
    static List<User> users;
    private static final Scanner scanner = new Scanner(System.in);
    private static final String FILE_PATH = "users.db";
    private static volatile boolean reminderThreadRunning = false;
    private static Thread reminderThread;

    /**
     * Entry point for the Finance Manager application.
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        users = loadUsers();

        while (true) {
            showAuthMenu();

            if (user == null) {
                break; 
            }

            showMainMenu();
            user = null; 
        }

        saveUsers();
        System.out.println("Goodbye! 👋");
    }

    /**
     * Loads users from the database file.
     * @return List of users
     */
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
    
    /**
     * Saves users to the database file.
     */
    private static void saveUsers() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(users);
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    /**
     * Shows the authentication menu (Sign Up, Sign In, Exit).
     */
    private static void showAuthMenu() {
        boolean inAuthMenu = true;

        while (inAuthMenu && user == null) {
            System.out.println("\n===== Welcome to Finance Manager =====");
            System.out.println("1. Sign Up");
            System.out.println("2. Sign In");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    handleSignUp();
                    break;
                case 2:
                    handleSignIn();
                    break;
                case 3:
                    inAuthMenu = false;
                    break;
                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
    }

    /**
     * Handles user sign up process.
     */
    private static void handleSignUp() {
        System.out.println("\n=== Sign Up ===");
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

            // Validate username and email uniqueness using User methods
            if (User.usernameExists(users, username)) {
                throw new IllegalArgumentException("Username already exists.");
            }
            if (User.emailExists(users, email)) {
                throw new IllegalArgumentException("Email already exists.");
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

            System.out.println("Sign up successful! Please sign in.");
        } catch (IllegalArgumentException e) {
            System.out.println("Sign-up failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Sign up failed: " + e.getMessage());
        }
        
    }

    /**
     * Handles user sign in process.
     */
    private static void handleSignIn() {
        try {
            System.out.println("\n=== Sign In ===");
            System.out.print("Enter username or email: ");
            String identifier = scanner.nextLine().trim();
    
            System.out.print("Enter password: ");
            String password = scanner.nextLine().trim();

            if (identifier.isEmpty() || password.isEmpty()) {
                throw new IllegalArgumentException("Username and password are required.");
            }

            User found = User.authenticate(users, identifier, password);
            if (found != null) {
                user = found;
                user.setLastLogin(LocalDateTime.now());
                saveUsers();
                System.out.println("Sign in successful! Welcome, " + user.getUsername());
                startReminderChecker();
                return;
            }
            throw new IllegalArgumentException("Invalid username or password.");
        } catch (IllegalArgumentException e) {
            System.out.println("Login failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Login failed: An unexpected error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Shows the main dashboard menu after login.
     */
    private static void showMainMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n===== Finance Manager Dashboard =====");
            System.out.println("1. Add Transaction");
            System.out.println("2. Create Budget");
            System.out.println("3. Create Goal");
            System.out.println("4. View Financial Summary");
            System.out.println("5. Adjust Budget Limit");
            System.out.println("6. Update Goal Progress");
            System.out.println("7. Set Reminder");
            System.out.println("8. View Future Reminders");
            System.out.println("9. Log Out");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addTransaction();
                    break;
                case 2:
                    createBudget();
                    break;
                case 3:
                    createGoal();
                    break;
                case 4:
                    showFinancialSummary();
                    break;
                case 5:
                    adjustBudgetLimit();
                    break;
                case 6:
                    updateGoalProgress();
                    break;
                case 7:
                    setReminder();
                    break;
                case 8:
                    showFutureReminders();
                    break;
                case 9:
                    running = false;
                    stopReminderChecker();
                    System.out.println("\nLogged out successfully!");
                    break;
                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
    }

    /**
     * Adds a new transaction for the current user.
     */
    private static void addTransaction() {
        System.out.println("\n=== New Transaction ===");
        System.out.print("Category: ");
        String category = scanner.nextLine();
        
        System.out.print("Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); 
        
        String type = "expense";
        while (true) {
            System.out.print("Type (1 for Income, 2 for Expense): ");
            String typeChoice = scanner.nextLine();
            if (typeChoice.equals("1")) {
                type = "income";
                break;
            } else if (typeChoice.equals("2")) {
                type = "expense";
                break;
            } else {
                System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        }
        user.addTransaction(new Transaction(category, amount, type));
        System.out.println("\nTransaction added successfully!");

        
        saveUsers();
    }

    /**
     * Creates a new budget for the current user.
     */
    private static void createBudget() {
        System.out.println("\n=== Create Budget ===");
        BudgetFactory factory = new DefaultBudgetFactory();
        
        System.out.print("Category: ");
        String category = scanner.nextLine();
        
        System.out.print("Budget Limit: ");
        double limit = scanner.nextDouble();
        scanner.nextLine();
        
        Budget budget = factory.createBudget(user.getBudgets().size() + 1, category, limit);
        user.addBudget(budget);
        System.out.println("\nBudget created successfully!");
        saveUsers();
    }

    /**
     * Creates a new savings goal for the current user.
     */
    private static void createGoal() {
        System.out.println("\n=== Create Savings Goal ===");
        Goal goal = new Goal();
        
        System.out.print("Goal Title: ");
        goal.setTitle(scanner.nextLine());
        
        System.out.print("Target Amount: ");
        goal.setTargetAmount(scanner.nextDouble());
        scanner.nextLine(); 
        
        System.out.print("Deadline (days from now): ");
        int days = scanner.nextInt();
        scanner.nextLine(); 
        
        Date deadline = new Date(System.currentTimeMillis() + (days * 86400000L));
        goal.setDeadline(deadline);

        user.addGoal(goal);
        System.out.println("\nGoal created successfully!");
        saveUsers();
    }

    /**
     * Shows a financial summary (transactions, budgets, goals).
     */
    private static void showFinancialSummary() {
        System.out.println("\n=== Financial Report ===");
        System.out.println("1. Income Transactions");
        System.out.println("2. Expense Transactions");
        System.out.println("3. Budget Summary");
        System.out.println("4. Goals Summary");
        System.out.println("5. All");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.println("\nIncome Transactions:");
                user.getTransactions().stream()
                    .filter(t -> t.getType().equalsIgnoreCase("income"))
                    .forEach(t -> System.out.println("- " + t.getSummary()));
                break;
            case 2:
                System.out.println("\nExpense Transactions:");
                user.getTransactions().stream()
                    .filter(t -> t.getType().equalsIgnoreCase("expense"))
                    .forEach(t -> System.out.println("- " + t.getSummary()));
                break;
            case 3:
                System.out.println("\nBudget Status:");
                user.getBudgets().forEach(b -> {
                    String status = b.isExceeded() ? "Exceeded" : "Within Budget";
                    System.out.printf("- %-15s Limit: $%.2f | Spent: $%.2f | Remaining: $%.2f | %s%n",
                        b.getCategory(), b.getLimit(), b.getCurrentSpending(), 
                        b.getRemainingBudget(), status);
                });
                break;
            case 4:
                System.out.println("\nSavings Goals:");
                user.getGoals().forEach(g -> {
                    String status = g.isCompleted() ? "Completed" : "In Progress";
                    System.out.printf("- %-20s Target: $%.2f | Saved: $%.2f (%.1f%%) | Days Left: %d | %s%n",
                        g.getTitle(), g.getTargetAmount(), g.getSavedAmount(),
                        g.getProgressPercentage(), g.getDaysRemaining(), status);
                });
                break;
            case 5:
                System.out.println("\nRecent Transactions:");
                user.getTransactions().forEach(t -> 
                    System.out.println("- " + t.getSummary()));
                System.out.println("\nBudget Status:");
                user.getBudgets().forEach(b -> {
                    String status = b.isExceeded() ? "Exceeded" : "Within Budget";
                    System.out.printf("- %-15s Limit: $%.2f | Spent: $%.2f | Remaining: $%.2f | %s%n",
                        b.getCategory(), b.getLimit(), b.getCurrentSpending(), 
                        b.getRemainingBudget(), status);
                });
                System.out.println("\nSavings Goals:");
                user.getGoals().forEach(g -> {
                    String status = g.isCompleted() ? "Completed" : "In Progress";
                    System.out.printf("- %-20s Target: $%.2f | Saved: $%.2f (%.1f%%) | Days Left: %d | %s%n",
                        g.getTitle(), g.getTargetAmount(), g.getSavedAmount(),
                        g.getProgressPercentage(), g.getDaysRemaining(), status);
                });
                break;
            default:
                System.out.println("Invalid option!");
        }
    }

    /**
     * Updates progress for a selected goal.
     */
    private static void updateGoalProgress() {
        System.out.println("\n=== Update Goal Progress ===");
        List<Goal> goals = user.getGoals();
        
        if(goals.isEmpty()) {
            System.out.println("No goals available!");
            return;
        }
        
        for(int i = 0; i < goals.size(); i++) {
            System.out.printf("%d. %s%n", i+1, goals.get(i).getTitle());
        }
        System.out.println("Select a goal:");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); 
        
        if(choice < 1 || choice > goals.size()) {
            System.out.println("Invalid selection!");
            return;
        }
        
        Goal selectedGoal = goals.get(choice-1);
        System.out.print("Enter amount to add: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); 
        
        try {
            selectedGoal.updateProgress(amount);
            System.out.printf("\nUpdated progress: %.1f%%%n", 
                selectedGoal.getProgressPercentage());
            if (selectedGoal.isCompleted()) {
                System.out.println("Goal Achieved: " + selectedGoal.getTitle() +
                    " (" + String.format("%.1f", selectedGoal.getProgressPercentage()) + "%)");
            }
            saveUsers();
        } catch(IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Sets a reminder for the user.
     */
    private static void setReminder() {
        System.out.println("\n=== Set Reminder ===");
        Reminder reminder = new Reminder();

        try {
            System.out.println("Current date and time: " + java.time.LocalDateTime.now());
            System.out.print("Reminder Title: ");
            String title = scanner.nextLine();
            reminder.setTitle(title);

            System.out.print("Reminder Date (YYYY-MM-DD): ");
            String date = scanner.nextLine();

            System.out.print("Reminder Time (HH:MM): ");
            String time = scanner.nextLine();

            reminder.setDateTime(date, time);

            user.addReminder(reminder);
            saveUsers();
            System.out.println("Reminder set successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Shows all future reminders for the user.
     */
    private static void showFutureReminders() {
        System.out.println("\n=== Future Reminders ===");
        LocalDateTime now = LocalDateTime.now();
        List<Reminder> futureReminders = new ArrayList<>();
        for (Reminder reminder : user.getReminders()) {
            if (!reminder.isNotified() && reminder.getDateTime().isAfter(now)) {
                futureReminders.add(reminder);
            }
        }
        if (futureReminders.isEmpty()) {
            System.out.println("No future reminders.");
        } else {
            for (Reminder reminder : futureReminders) {
                System.out.println("- " + reminder);
            }
        }
    }

    /**
     * Adjusts the limit for a selected budget.
     */
    private static void adjustBudgetLimit() {
        System.out.println("\n=== Adjust Budget Limit ===");
        List<Budget> budgets = user.getBudgets();
        if (budgets.isEmpty()) {
            System.out.println("No budgets available!");
            return;
        }
        for (int i = 0; i < budgets.size(); i++) {
            System.out.printf("%d. %s (Current Limit: $%.2f)\n", i + 1, budgets.get(i).getCategory(), budgets.get(i).getLimit());
        }
        System.out.print("Select a budget to adjust: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice < 1 || choice > budgets.size()) {
            System.out.println("Invalid selection!");
            return;
        }
        Budget selectedBudget = budgets.get(choice - 1);
        System.out.printf("Current limit for '%s' is $%.2f. Enter new limit: ", selectedBudget.getCategory(), selectedBudget.getLimit());
        double newLimit = scanner.nextDouble();
        scanner.nextLine();
        selectedBudget.setLimit(newLimit);
        saveUsers();
        System.out.println("Budget limit updated successfully!");
    }

    /**
     * Starts the background thread to check for due reminders.
     */
    private static void startReminderChecker() {
        // Stop any existing thread
        stopReminderChecker();
        reminderThread = new Thread(() -> {
            while (user != null) {
                try {
                    LocalDateTime now = LocalDateTime.now();
                    for (Reminder reminder : user.getReminders()) {
                        if (!reminder.isNotified() && reminder.getDateTime().isBefore(now)) {
                            System.out.println("\n[REMINDER] " + reminder.getTitle() + " is due!");
                            reminder.setNotified(true);
                            saveUsers();
                        }
                    }
                    Thread.sleep(1000); // check every second
                } catch (Exception ignored) {}
            }
        });
        reminderThread.setDaemon(true);
        reminderThread.start();
    }

    /**
     * Stops the background reminder checker thread.
     */
    private static void stopReminderChecker() {
        if (reminderThread != null && reminderThread.isAlive()) {
            user = null; // This will cause the thread to exit its loop
            try { reminderThread.join(100); } catch (InterruptedException ignored) {}
        }
    }
}