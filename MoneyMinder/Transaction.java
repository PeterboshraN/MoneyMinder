import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a financial transaction (income or expense).
 */
public class Transaction implements Serializable {
    private String category;
    private double amount;
    private LocalDate date;
    private String type; // "income" or "expense"

    /**
     * Constructs a transaction with the given details.
     * @param category The transaction category
     * @param amount The transaction amount
     * @param type The transaction type ("income" or "expense")
     */
    public Transaction(String category, double amount, String type) {
        this.category = category;
        this.amount = amount;
        this.type = type;
        this.date = LocalDate.now();
    }

    /**
     * Gets the transaction category.
     * @return The category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Gets the transaction amount.
     * @return The amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets the transaction date.
     * @return The date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the transaction type ("income" or "expense").
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets a summary string for the transaction.
     * @return The summary string
     */
    public String getSummary() {
        return String.format("[%s] %s: $%.2f (%s)", date, category, amount, type);
    }
}