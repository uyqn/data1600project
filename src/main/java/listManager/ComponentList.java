package listManager;

import components.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComponentList<S extends Component>
        extends attachTableView<S> implements Serializable, ItemList<S> {
    private transient ObservableList<S> list = FXCollections.observableArrayList();
    private transient ObservableList<? extends S> subList;

    @Override
    public ObservableList<S> getList(){
        return list;
    }

    @SuppressWarnings("unchecked")
    public <E extends S> ObservableList<E> getSubList(Class<E> className) {
        setSubList(className);

        if(subList != null) {
            return (ObservableList<E>) subList;
        }
        else {
            return null;
        }
    }

    @Override
    public void add(S item){
        list.add(item);
    }

    @SafeVarargs
    public final void addAll(S... items){
        list.addAll(items);
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
        for(Component item : list){
            csv.append(item.toCSV()).append("\n");
        }
        return csv.toString();
    }

    @SuppressWarnings("unchecked")
    public <E extends S> void setSubList(Class<E> className) {
        ObservableList<E> filteredList = FXCollections.observableArrayList();

        if(className.equals(Component.class)){
            this.subList = list;
        }

        else {
            for (Component item : list) {
                if (item.getClass() == className) {
                    filteredList.add((E) item);
                }
            }
            this.subList = filteredList;
        }
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
        list = FXCollections.observableArrayList(list); }
}
