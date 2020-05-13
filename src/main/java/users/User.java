package users;

import components.Computer;
import components.Listable;
import fileManager.FileManager;
import javafx.beans.property.SimpleStringProperty;
import listManager.ItemList;
import listManager.ListableList;

import java.io.Serializable;
import java.nio.file.Path;

public abstract class User implements Serializable, Listable {
    private transient SimpleStringProperty username = new SimpleStringProperty();
    private transient SimpleStringProperty password = new SimpleStringProperty();

    private transient FileManager manager = new FileManager();
    private transient ListableList<Computer> computerList = new ListableList<>();

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

    public abstract void open();
    public abstract Path getPath();
    public abstract <S extends Listable> void save(ItemList<S> list);
    public abstract <S extends Listable> void saveAs(ItemList<S> list);
}
