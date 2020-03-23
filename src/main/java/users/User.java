package users;

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws IllegalArgumentException {
        if(!username.matches("[\\w]{4,}")){
            throw new IllegalArgumentException("Invalid username!");
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws IllegalArgumentException {
        if(!password.matches("[^\\s]*")){
            throw new IllegalArgumentException("Invalid characters");
        }
        this.password = password;
    }
}
