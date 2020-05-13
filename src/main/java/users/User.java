package users;

import components.Computer;
import fileManager.FileManager;
import javafx.beans.property.SimpleStringProperty;
import listManager.ComponentList;

import java.io.Serializable;

public abstract class User implements Serializable {
    private transient SimpleStringProperty username = new SimpleStringProperty();
    private transient SimpleStringProperty password = new SimpleStringProperty();

    private transient FileManager manager = new FileManager();
    private transient ComponentList<Computer> computerList = new ComponentList<>();

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public String getUsername() {
        return username.getValue();
    }

    public void setUsername(String username) throws IllegalArgumentException {
        if(!username.matches("[\\w]{4,}")){
            throw new IllegalArgumentException("Invalid username!");
        }
        this.username.set(username);
    }

    public String getPassword() {
        return password.getValue();
    }

    public void setPassword(String password) throws IllegalArgumentException {
        if(!password.matches("[^\\s]*")){
            throw new IllegalArgumentException("Invalid characters");
        }
        this.password.set(password);
    }
}
