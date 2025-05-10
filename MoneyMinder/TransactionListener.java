/**
 * Listener interface for transaction events.
 */
public interface TransactionListener {
    /**
     * Called when a transaction is added.
     * @param transaction The transaction that was added
     */
    void onTransactionAdded(Transaction transaction);
} 