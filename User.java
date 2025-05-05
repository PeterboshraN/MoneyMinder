class User{
    private int id;
    private String name;
    private String email;
    private String password;
    public User(int id,String name, String email, String password){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public boolean register(String name, String email, String password) {
        if (name.length() < 3 || email.length() < 3 || password.length() < 3) {
            return false;
        }
        if (!email.contains("@")) {
            return false;
        }
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }
        return true;
    }
    public void login(String email, String password) {
        if (authenticate(email, password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid email or password.");
        }
    }
    public boolean authenticate(String email, String password) {
        if (email.equals(this.email) && password.equals(this.password)) {
            return true;
        } else {
            return false;
        }
    }
    public void viewDashboard(){
        System.out.println("Welcome to the dashboard, " + name + "!");  

    }
    public void viewProfile(){
        System.out.println("User ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
    }
    public void updateProfile(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public void logout(){
        System.out.println("Logout successful!");
    }
}