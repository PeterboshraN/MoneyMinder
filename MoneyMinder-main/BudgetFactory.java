public interface BudgetFactory {
    Budget createBudget(int budgetId, String category, double limit);
}