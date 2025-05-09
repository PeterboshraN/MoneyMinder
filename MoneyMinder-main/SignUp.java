public class SignUp {

    private User user;

    public SignUp(User user) {
        this.user = user;
        if (FinanceManager.users != null) {
            FinanceManager.users.add(user);
        }
    }

    public User getUser() {
        return user;
    }

   
}