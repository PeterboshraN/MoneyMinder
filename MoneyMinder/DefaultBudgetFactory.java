/**
 * Default implementation of BudgetFactory for creating Budget objects.
 */
public class DefaultBudgetFactory implements BudgetFactory {
    /**
     * Creates a new Budget instance with the specified parameters.
     * @param budgetId The budget ID
     * @param category The budget category
     * @param limit The budget limit
     * @return The created Budget
     */
    @Override
    public Budget createBudget(int budgetId, String category, double limit) {
        Budget budget = new Budget();
        budget.setBudgetId(budgetId);
        budget.setCategory(category);
        budget.setLimit(limit);
        return budget;
    }
}