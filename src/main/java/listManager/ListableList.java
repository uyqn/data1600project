package listManager;

import components.Component;
import components.Listable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListableList<S extends Listable>
        extends attachTableView<S> implements Serializable, ItemList<S> {
    private transient ObservableList<S> list = FXCollections.observableArrayList();

    public void setList(ObservableList<S> list){
        this.list = list;
    }

    @Override
    public ObservableList<S> getList(){
        return list;
    }

    @Override
    public void add(S item){
        list.add(item);
    }

    public void remove(S item){
        list.remove(item);
    }

    @Override
    public void setTableView(TableView<S> tableView){
        tableView.setItems(list);
    }

    @Override
    public String toCSV(){
        StringBuilder csv = new StringBuilder();
        for(S item : list){
            csv.append(item.toCSV()).append("\n");
        }
        return csv.toString();
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder("[");

        for(S item : list){
            str.append(item.getName()).append(",");
        }

        return str.substring(0, str.length()-1) + "]";
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(new ArrayList<>(list));
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        List<Component> list = (List<Component>) objectInputStream.readObject();
        this.list = (ObservableList<S>) FXCollections.observableArrayList(list); }
}
