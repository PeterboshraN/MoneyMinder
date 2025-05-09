import java.util.Date;
import java.util.Scanner;
import java.util.List;

public class FinanceManager {
    private static User user;
    static List<User> users;
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        showMainMenu();
    }

    private static void showMainMenu() {
        boolean running = true;
        
        while(running) {
            System.out.println("\n===== Personal Finance Manager =====");
            System.out.println("1. Add Transaction");
            System.out.println("2. Create Budget");
            System.out.println("3. Create Goal");
            System.out.println("4. View Financial Summary");
            System.out.println("5. Update Goal Progress");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch(choice) {
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
                    updateGoalProgress();
                    break;
                case 6:
                    running = false;
                    System.out.println("\nGoodbye! 👋");
                    break;
                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
    }

    private static void addTransaction() {
        System.out.println("\n=== New Transaction ===");
        System.out.print("Category: ");
        String category = scanner.nextLine();
        
        System.out.print("Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); 
        
        user.addTransaction(new Transaction(category, amount));
        System.out.println("\n✅ Transaction added successfully!");
    }

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
        System.out.println("\n✅ Budget created successfully!");
    }

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

        goal.addListener(g -> System.out.println(
            "\n🎉 Goal Achieved: " + g.getTitle() + 
            " (" + String.format("%.1f", g.getProgressPercentage()) + "%)"));

        user.addGoal(goal);
        System.out.println("\n✅ Goal created successfully!");
    }

    private static void showFinancialSummary() {
        System.out.println("\n=== Financial Summary ===");
        
        // Transactions
        System.out.println("\nRecent Transactions:");
        user.getTransactions().forEach(t -> 
            System.out.printf("- %-15s $%.2f%n", t.getCategory(), t.getAmount()));
        
        // Budgets
        System.out.println("\nBudget Status:");
        user.getBudgets().forEach(b -> {
            String status = b.isExceeded() ? "❌ Exceeded" : "✅ Within Budget";
            System.out.printf("- %-15s Limit: $%.2f | Spent: $%.2f | Remaining: $%.2f | %s%n",
                b.getCategory(), b.getLimit(), b.getCurrentSpending(), 
                b.getRemainingBudget(), status);
        });
        
        // Goals
        System.out.println("\nSavings Goals:");
        user.getGoals().forEach(g -> {
            String status = g.isCompleted() ? "✅ Completed" : "⌛ In Progress";
            System.out.printf("- %-20s Target: $%.2f | Saved: $%.2f (%.1f%%) | Days Left: %d | %s%n",
                g.getTitle(), g.getTargetAmount(), g.getSavedAmount(),
                g.getProgressPercentage(), g.getDaysRemaining(), status);
        });
    }

    private static void updateGoalProgress() {
        System.out.println("\n=== Update Goal Progress ===");
        List<Goal> goals = user.getGoals();
        
        if(goals.isEmpty()) {
            System.out.println("No goals available!");
            return;
        }
        
        System.out.println("Select a goal:");
        for(int i = 0; i < goals.size(); i++) {
            System.out.printf("%d. %s%n", i+1, goals.get(i).getTitle());
        }
        
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
        } catch(IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}