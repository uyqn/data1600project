package listManager;

import javafx.collections.ObservableList;

import java.io.Serializable;

public interface ItemList<S> extends Serializable {
    String toCSV();
    ObservableList<S> getList();
    void add(S item);
}