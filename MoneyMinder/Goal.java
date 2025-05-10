import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * Represents a savings goal for a user.
 */
public class Goal implements Serializable {
    private int goalId;
    private String title;
    private double targetAmount;
    private double savedAmount;
    private Date deadline;
    private List<GoalCompletionListener> listeners = new ArrayList<>();

    /**
     * Adds a listener for goal completion.
     * @param listener The listener to add
     */
    public void addListener(GoalCompletionListener listener) {
        listeners.add(listener);
    }

    /**
     * Updates the progress of the goal by adding the specified amount.
     * @param amount The amount to add to savings
     * @throws IllegalArgumentException if amount is negative
     */
    public void updateProgress(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Savings amount cannot be negative");
        }
        this.savedAmount += amount;
        if (isCompleted()) {
            listeners.forEach(listener -> listener.onGoalCompleted(this));
        }
    }
    /**
     * Gets the current saved amount.
     * @return The saved amount
     */
    public double getProgress() {
        return savedAmount;
    }
    /**
     * Gets the progress percentage towards the target amount.
     * @return Progress percentage
     */
    public double getProgressPercentage() {
        return (savedAmount / targetAmount) * 100;
    }
    /**
     * Checks if the goal is completed.
     * @return True if completed, false otherwise
     */
    public boolean isCompleted() {
        return savedAmount >= targetAmount;
    }
    /**
     * Gets the number of days remaining until the deadline.
     * @return Days remaining
     */
    public long getDaysRemaining() {
        long millisRemaining = deadline.getTime() - System.currentTimeMillis();
        return millisRemaining / (1000 * 60 * 60 * 24);
    }

    /**
     * Gets the goal ID.
     * @return The goal ID
     */
    public int getGoalId() {
        return goalId;
    }
    /**
     * Sets the goal ID.
     * @param goalId The goal ID
     */
    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }
    /**
     * Gets the goal title.
     * @return The goal title
     */
    public String getTitle() {
        return title;
    }
    /**
     * Sets the goal title.
     * @param title The goal title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * Gets the target amount for the goal.
     * @return The target amount
     */
    public double getTargetAmount() {
        return targetAmount;
    }
    /**
     * Sets the target amount for the goal.
     * @param targetAmount The target amount
     * @throws IllegalArgumentException if targetAmount is not positive
     */
    public void setTargetAmount(double targetAmount) {
        if (targetAmount <= 0) {
            throw new IllegalArgumentException("Target amount must be positive");
        }
        this.targetAmount = targetAmount;
    }
    /**
     * Gets the amount saved so far.
     * @return The saved amount
     */
    public double getSavedAmount() {
        return savedAmount;
    }
    /**
     * Gets the deadline for the goal.
     * @return The deadline date
     */
    public Date getDeadline() {
        return new Date(deadline.getTime());
    }
    /**
     * Sets the deadline for the goal.
     * @param deadline The deadline date
     * @throws IllegalArgumentException if deadline is in the past
     */
    public void setDeadline(Date deadline) {
        if (deadline.before(new Date())) {
            throw new IllegalArgumentException("Deadline cannot be in the past");
        }
        this.deadline = new Date(deadline.getTime());
    }
}