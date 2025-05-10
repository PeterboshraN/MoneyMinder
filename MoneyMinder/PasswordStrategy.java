import java.io.Serializable;

public interface PasswordStrategy extends Serializable {
    void validate(String password);
}