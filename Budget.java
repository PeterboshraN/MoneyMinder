import java.util.ArrayList;
import java.util.List;

public class Budget {
    private int budgetId;
    private String category;
    private double limit;
    private List<Double> transactions;

    public Budget() {
        this.transactions = new ArrayList<>();
    }
    public void setLimit(double limit) {
        this.limit = limit;
    }
    public void adjustBudget(double amount) {
        this.limit += amount;
    }
    public void addTransaction(double amount) {
        this.transactions.add(amount);
    }
    public boolean isExceeded() {
        return getCurrentSpending() > limit;
    }
    public double getCurrentSpending() {
        return transactions.stream().mapToDouble(Double::doubleValue).sum();
    }
    public double getRemainingBudget() {
        return limit - getCurrentSpending();
    }

    public int getBudgetId() {
        return budgetId;
    }
    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public double getLimit() {
        return limit;
    }
    public List<Double> getTransactions() {
        return new ArrayList<>(transactions);
    }
}