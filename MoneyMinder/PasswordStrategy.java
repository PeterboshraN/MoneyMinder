import java.io.Serializable;

/**
 * Strategy interface for password validation.
 */
public interface PasswordStrategy extends Serializable {
    /**
     * Validates the given password.
     * @param password The password to validate
     * @throws IllegalArgumentException if the password is invalid
     */
    void validate(String password);
}