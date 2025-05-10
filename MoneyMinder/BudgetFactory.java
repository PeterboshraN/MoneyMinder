/**
 * Factory interface for creating Budget objects.
 */
public interface BudgetFactory {
    /**
     * Creates a new Budget instance.
     * @param budgetId The budget ID
     * @param category The budget category
     * @param limit The budget limit
     * @return The created Budget
     */
    Budget createBudget(int budgetId, String category, double limit);
}