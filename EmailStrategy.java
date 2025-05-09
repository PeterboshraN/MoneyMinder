import java.io.Serializable;

public interface EmailStrategy extends Serializable {
    void validate(String email);
}
