package users;

public class User {
    private String username;
    private String password;
    private int accessLevel;

    public User(String username, String password, int accessLevel){
        setUsername(username);
        setPassword(password);
        setAccessLevel(accessLevel);
    }

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
        this.accessLevel = 2;
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

    public void setAccessLevel(int accessLevel){
        if(accessLevel < 0 || accessLevel > 2){
            throw new IllegalArgumentException("Access level has to be an integer between 0 and 2:" +
                    "\n access level 0: root" +
                    "\n access level 1: admin" +
                    "\n access level 2: end user");
        }
        this.accessLevel = accessLevel;
    }

    public int getAccessLevel(){
        return accessLevel;
    }
}
