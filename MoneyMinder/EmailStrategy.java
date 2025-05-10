import java.io.Serializable;

/**
 * Strategy interface for email validation.
 */
public interface EmailStrategy extends Serializable {
    /**
     * Validates the given email address.
     * @param email The email address to validate
     * @throws IllegalArgumentException if the email is invalid
     */
    void validate(String email);
}
