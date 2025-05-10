/**
 * Listener interface for goal completion events.
 */
public interface GoalCompletionListener {
    /**
     * Called when a goal is completed.
     * @param goal The goal that was completed
     */
    void onGoalCompleted(Goal goal);
}