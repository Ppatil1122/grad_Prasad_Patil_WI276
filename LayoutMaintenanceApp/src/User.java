public class User {
    private int userId;
    private String name;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String address;
    private String role;

    // Constructor
    public User(String name, String username, String password, String phone, String email, String address, String role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.role = role;
    }

    // Getters
    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getRole() { return role; }

    // Setters
    public void setUserId(int userId) { this.userId = userId; }
}