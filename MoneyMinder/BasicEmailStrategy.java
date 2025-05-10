/**
 * Basic implementation of EmailStrategy for validating emails.
 */
public class BasicEmailStrategy implements EmailStrategy {
    private static final long serialVersionUID = 1L;
    
    /**
     * Validates the given email address.
     * @param email The email address to validate
     * @throws IllegalArgumentException if the email is invalid
     */
    @Override
    public void validate(String email) {
        if (email == null || email.length() < 13) {
            throw new IllegalArgumentException("Invalid email, must be at least 13 characters long");
        }
        if (!email.endsWith("@gmail.com")) {
            throw new IllegalArgumentException("Invalid email, must end with @gmail.com");
        }
    }
    
}
