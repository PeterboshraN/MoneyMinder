import java.io.Serializable;

public class BasicEmailStrategy implements EmailStrategy, Serializable {
    private static final long serialVersionUID = 1L;
    
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
