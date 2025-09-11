package cart;

public class User {
    public enum UserRole {
        ADMIN,
        CUSTOMER
    }

    private final long id;
    private final String username;
    private final String password;
    private final String email;
    private final String fullName;
    private final String address;
    private final String phone;
    private final UserRole role;


    public User(long id, String username, String password, String email, String fullName, String address, String phone, UserRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public UserRole getRole() {
        return role;
    }
}