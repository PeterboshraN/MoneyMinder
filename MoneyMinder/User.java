import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the Finance Manager application.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String email;
    private EmailStrategy emailStrategy;
    private PasswordStrategy passwordStrategy;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private List<Transaction> transactions;
    private List<Budget> budgets;
    private List<Goal> goals;
    private List<TransactionListener> transactionListeners = new ArrayList<>();
    private List<Reminder> reminders = new ArrayList<>();

    /**
     * Default constructor. Initializes lists and strategies.
     */
    public User() {
        this.transactions = new ArrayList<>();
        this.budgets = new ArrayList<>();
        this.goals = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.passwordStrategy = new BasicPasswordStrategy();
        this.emailStrategy = new BasicEmailStrategy();
    }

    /**
     * Constructs a user with the given credentials and strategies.
     * @param username The username
     * @param email The email address
     * @param emailStrategy The email validation strategy
     * @param password The password
     * @param passwordStrategy The password validation strategy
     */
    public User(String username, String email, EmailStrategy emailStrategy, String password, PasswordStrategy passwordStrategy) {
        this();
        this.username = username;
        this.emailStrategy = emailStrategy;
        setEmail(email);
        this.passwordStrategy = passwordStrategy;
        setPassword(password);
    }

    /**
     * Notifies all transaction listeners of a new transaction.
     * @param transaction The transaction to notify about
     */
    private void notifyTransactionListeners(Transaction transaction) {
        for (TransactionListener listener : transactionListeners) {
            listener.onTransactionAdded(transaction);
        }
    }

    /**
     * Gets the user ID.
     * @return The user ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the user ID.
     * @param id The user ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the username.
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email address.
     * @return The email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address and validates it.
     * @param email The email address
     */
    public void setEmail(String email) {
        emailStrategy.validate(email);
        this.email = email;
    }

    /**
     * Gets the password.
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password and validates it.
     * @param password The password
     */
    public void setPassword(String password) {
        passwordStrategy.validate(password);
        this.password = password;
    }

    /**
     * Gets the creation date/time.
     * @return The creation date/time
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation date/time.
     * @param createdAt The creation date/time
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the last login date/time.
     * @return The last login date/time
     */
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    /**
     * Sets the last login date/time.
     * @param lastLogin The last login date/time
     */
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * Gets the list of transactions.
     * @return The list of transactions
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Sets the list of transactions.
     * @param transactions The list of transactions
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Gets the list of budgets.
     * @return The list of budgets
     */
    public List<Budget> getBudgets() {
        return budgets;
    }

    /**
     * Sets the list of budgets.
     * @param budgets The list of budgets
     */
    public void setBudgets(List<Budget> budgets) {
        this.budgets = budgets;
    }

    /**
     * Gets the list of goals.
     * @return The list of goals
     */
    public List<Goal> getGoals() {
        return goals;
    }

    /**
     * Sets the list of goals.
     * @param goals The list of goals
     */
    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }

    /**
     * Adds a transaction and notifies listeners.
     * @param transaction The transaction to add
     */
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        notifyTransactionListeners(transaction);  
    }

    /**
     * Adds a budget and registers it as a transaction listener.
     * @param budget The budget to add
     */
    public void addBudget(Budget budget) {
        this.budgets.add(budget);
        transactionListeners.add(budget); 
    }

    /**
     * Adds a savings goal.
     * @param goal The goal to add
     */
    public void addGoal(Goal goal) {
        this.goals.add(goal);
    }

    /**
     * Gets the list of reminders.
     * @return The list of reminders
     */
    public List<Reminder> getReminders() {
        if (reminders == null) {
            reminders = new ArrayList<>();
        }
        return reminders;
    }

    /**
     * Adds a reminder.
     * @param reminder The reminder to add
     */
    public void addReminder(Reminder reminder) {
        getReminders().add(reminder);
    }

    /**
     * Checks if a username exists in a list of users.
     * @param users The list of users
     * @param username The username to check
     * @return True if exists, false otherwise
     */
    public static boolean usernameExists(List<User> users, String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) return true;
        }
        return false;
    }

    /**
     * Checks if an email exists in a list of users.
     * @param users The list of users
     * @param email The email to check
     * @return True if exists, false otherwise
     */
    public static boolean emailExists(List<User> users, String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) return true;
        }
        return false;
    }

    /**
     * Authenticates a user by username/email and password.
     * @param users The list of users
     * @param identifier Username or email
     * @param password The password
     * @return The authenticated user or null
     */
    public static User authenticate(List<User> users, String identifier, String password) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(identifier) || user.getEmail().equalsIgnoreCase(identifier)) {
                if (user.getPassword().equals(password)) {
                    return user;
                }
            }
        }
        return null;
    }
}
