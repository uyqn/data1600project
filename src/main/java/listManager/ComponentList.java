package listManager;

import components.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComponentList implements Serializable, ItemList {
    private transient ObservableList<Component> components = FXCollections.observableArrayList();

    @Override
    public ObservableList<Component> getList(){
        return components;
    }

    @Override
    public void add(Component component){
        components.add(component);
    }

    public void remove(Component component){
        components.remove(component);
    }

    @Override
    public String toCSV(){
        StringBuilder csv = new StringBuilder();
        for(Component component : components){
            csv.append(component.toCSV()).append("\n");
        }
        return csv.toString();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(new ArrayList<>(components));
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        List<Component> list = (List<Component>) objectInputStream.readObject();
        components = FXCollections.observableArrayList(list); }
}
