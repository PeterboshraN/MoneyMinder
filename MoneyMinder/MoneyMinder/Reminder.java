import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Reminder implements Serializable {
    private String title;
    private LocalDateTime dateTime;
    private boolean notified;

    // Constructor
    public Reminder() {
        this.notified = false;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.length() < 3 || title.length() > 50) {
            throw new IllegalArgumentException("Reminder title must be between 3 and 50 characters.");
        }
        this.title = title;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(String date, String time) {
        try {
            String dateTimeStr = date + "T" + time;
            this.dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            if (this.dateTime.isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("Reminder date and time must be in the future.");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date or time format. Use YYYY-MM-DD and HH:MM.");
        }
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    @Override
    public String toString() {
        return String.format("Reminder: %s at %s", title, dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }
}