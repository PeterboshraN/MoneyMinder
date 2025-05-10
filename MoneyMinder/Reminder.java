import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.ZoneId;

/**
 * Represents a reminder for the user.
 */
public class Reminder implements Serializable {
    private String title;
    private LocalDateTime dateTime;
    private boolean notified;

    /**
     * Default constructor. Initializes notified to false.
     */
    public Reminder() {
        this.notified = false;
    }

    /**
     * Gets the reminder title.
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the reminder title.
     * @param title The title
     * @throws IllegalArgumentException if title is invalid
     */
    public void setTitle(String title) {
        if (title == null || title.length() < 3 || title.length() > 50) {
            throw new IllegalArgumentException("Reminder title must be between 3 and 50 characters.");
        }
        this.title = title;
    }

    /**
     * Gets the reminder date and time.
     * @return The date and time
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Sets the reminder date and time from date and time strings.
     * @param date The date string (YYYY-MM-DD)
     * @param time The time string (HH:MM)
     * @throws IllegalArgumentException if date/time is invalid or in the past
     */
    public void setDateTime(String date, String time) {
        try {
            String dateTimeStr = date + "T" + time;
            this.dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            // Optionally, print the system time for debugging
            // System.out.println("System time: " + LocalDateTime.now(ZoneId.systemDefault()));
            if (this.dateTime.isBefore(LocalDateTime.now(ZoneId.systemDefault()))) {
                throw new IllegalArgumentException("Reminder date and time must be in the future.");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date or time format. Use YYYY-MM-DD and HH:MM.");
        }
    }

    /**
     * Checks if the reminder has been notified.
     * @return True if notified, false otherwise
     */
    public boolean isNotified() {
        return notified;
    }

    /**
     * Sets the notified status of the reminder.
     * @param notified The notified status
     */
    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    /**
     * Returns a string representation of the reminder.
     * @return The string representation
     */
    @Override
    public String toString() {
        return String.format("Reminder: %s at %s", title, dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }
}