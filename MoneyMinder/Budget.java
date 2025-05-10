import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * Represents a budget for a specific category.
 */
public class Budget implements TransactionListener, Serializable {
    private int budgetId;
    private String category;
    private double limit;
    private List<Double> transactions;

    /**
     * Default constructor. Initializes the transactions list.
     */
    public Budget() {
        this.transactions = new ArrayList<>();
    }
    /**
     * Sets the budget limit.
     * @param limit The budget limit
     */
    public void setLimit(double limit) {
        this.limit = limit;
    }
    /**
     * Adjusts the budget limit by the specified amount.
     * @param amount The amount to adjust
     */
    public void adjustBudget(double amount) {
        this.limit += amount;
    }
    /**
     * Adds a transaction amount to the budget's record.
     * @param amount The transaction amount
     */
    public void addTransaction(double amount) {
        this.transactions.add(amount);
    }
    /**
     * Handles a transaction being added (from TransactionListener).
     * @param transaction The transaction added
     */
    @Override
    public void onTransactionAdded(Transaction transaction) {
        if (transaction.getCategory().equals(category)) {
            addTransaction(transaction.getAmount());
        }
    }
    /**
     * Checks if the budget has been exceeded.
     * @return True if exceeded, false otherwise
     */
    public boolean isExceeded() {
        return getCurrentSpending() > limit;
    }
    /**
     * Gets the current total spending for this budget.
     * @return The current spending
     */
    public double getCurrentSpending() {
        return transactions.stream().mapToDouble(Double::doubleValue).sum();
    }
    /**
     * Gets the remaining budget.
     * @return The remaining budget
     */
    public double getRemainingBudget() {
        return limit - getCurrentSpending();
    }

    /**
     * Gets the budget ID.
     * @return The budget ID
     */
    public int getBudgetId() {
        return budgetId;
    }
    /**
     * Sets the budget ID.
     * @param budgetId The budget ID
     */
    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }
    /**
     * Gets the budget category.
     * @return The category
     */
    public String getCategory() {
        return category;
    }
    /**
     * Sets the budget category.
     * @param category The category
     */
    public void setCategory(String category) {
        this.category = category;
    }
    /**
     * Gets the budget limit.
     * @return The limit
     */
    public double getLimit() {
        return limit;
    }
    /**
     * Gets the list of transaction amounts for this budget.
     * @return The list of transaction amounts
     */
    public List<Double> getTransactions() {
        return new ArrayList<>(transactions);
    }
}