public class DefaultBudgetFactory implements BudgetFactory {
    @Override
    public Budget createBudget(int budgetId, String category, double limit) {
        Budget budget = new Budget();
        budget.setBudgetId(budgetId);
        budget.setCategory(category);
        budget.setLimit(limit);
        return budget;
    }
}