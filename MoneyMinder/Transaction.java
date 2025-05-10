import java.io.Serializable;
import java.time.LocalDate;

public class Transaction implements Serializable {
    private String category;
    private double amount;
    private LocalDate date;
    private String type; // "income" or "expense"

    public Transaction(String category, double amount, String type) {
        this.category = category;
        this.amount = amount;
        this.type = type;
        this.date = LocalDate.now();
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getSummary() {
        return String.format("[%s] %s: $%.2f (%s)", date, category, amount, type);
    }
}