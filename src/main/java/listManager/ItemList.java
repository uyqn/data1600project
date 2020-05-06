package listManager;

import components.Component;
import javafx.collections.ObservableList;

import java.io.Serializable;

public interface ItemList extends Serializable {
    String toCSV();
    ObservableList<Component> getList();
    void add(Component component);
}
