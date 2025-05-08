import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {
    private Long id;
    private String username;
    private String email;
    private PasswordStrategy passwordStrategy;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private List<Transaction> transactions;
    private List<Budget> budgets;
    private List<Goal> goals;
    private List<TransactionListener> transactionListeners = new ArrayList<>();

    public User() {
        this.transactions = new ArrayList<>();
        this.budgets = new ArrayList<>();
        this.goals = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.passwordStrategy = new BasicPasswordStrategy();
    }
    public User(String username, String email, String password, PasswordStrategy passwordStrategy) {
        this();
        this.username = username;
        this.email = email;
        this.passwordStrategy = passwordStrategy;
        setPassword(password);
    }

    private void notifyTransactionListeners(Transaction transaction) {
        for (TransactionListener listener : transactionListeners) {
            listener.onTransactionAdded(transaction);
        }
    }
 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        passwordStrategy.validate(password);
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(List<Budget> budgets) {
        this.budgets = budgets;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        notifyTransactionListeners(transaction);  
    }
 
    public void addBudget(Budget budget) {
        this.budgets.add(budget);
        transactionListeners.add(budget); 
    }

    public void addGoal(Goal goal) {
        this.goals.add(goal);
    }
}
