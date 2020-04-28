package listManager;

import components.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.Serializable;

public class ComponentList implements Serializable, ItemList {
    private ObservableList<Component> components = FXCollections.observableArrayList();

    public ObservableList<Component> getList(){
        return components;
    }
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
}
