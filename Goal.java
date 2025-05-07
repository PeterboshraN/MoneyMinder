import java.util.Date;

public class Goal {
    private int goalId;
    private String title;
    private double targetAmount;
    private double savedAmount;
    private Date deadline;

    public void updateProgress(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Savings amount cannot be negative");
        }
        this.savedAmount += amount;
    }
    public double getProgress() {
        return savedAmount;
    }
    public double getProgressPercentage() {
        return (savedAmount / targetAmount) * 100;
    }
    public boolean isCompleted() {
        return savedAmount >= targetAmount;
    }
    public long getDaysRemaining() {
        long millisRemaining = deadline.getTime() - System.currentTimeMillis();
        return millisRemaining / (1000 * 60 * 60 * 24);
    }

    public int getGoalId() {
        return goalId;
    }
    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public double getTargetAmount() {
        return targetAmount;
    }
    public void setTargetAmount(double targetAmount) {
        if (targetAmount <= 0) {
            throw new IllegalArgumentException("Target amount must be positive");
        }
        this.targetAmount = targetAmount;
    }
    public double getSavedAmount() {
        return savedAmount;
    }
    public Date getDeadline() {
        return new Date(deadline.getTime());
    }
    public void setDeadline(Date deadline) {
        if (deadline.before(new Date())) {
            throw new IllegalArgumentException("Deadline cannot be in the past");
        }
        this.deadline = new Date(deadline.getTime());
    }
}