/**
 * Basic implementation of PasswordStrategy for validating passwords.
 */
public class BasicPasswordStrategy implements PasswordStrategy {
    private static final long serialVersionUID = 1L;
    
    /**
     * Validates the given password.
     * @param password The password to validate
     * @throws IllegalArgumentException if the password is invalid
     */
    @Override
    public void validate(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter.");
        }
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must contain at least one number.");
        }
    }
}