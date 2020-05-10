package listManager;

import components.Component;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public abstract class attachTableView<S> {
    private ArrayList<TableColumn<S, ?>> myColumns = new ArrayList<>();

    abstract void setTableView(TableView<S> tableView);

    public void setColumns(TableView<S> tableView, Class<? extends Component> className,
                           boolean insertTypeColumn, String... parameters) throws NoSuchMethodException {
        myColumns.clear();

        if(insertTypeColumn){
            TableColumn<S, String> typeCol = new TableColumn<>("Type");
            typeCol.setCellValueFactory(new PropertyValueFactory<>("componentType"));
            myColumns.add(typeCol);
        }

        for(String parameter : parameters){
            myColumns.add(createColumn(className, parameter));
        }

        tableView.getColumns().setAll(myColumns);
    }

    private String firstLetterUppercase(String str){
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }

    private String columnName(String parameter){
        return firstLetterUppercase(parameter).replaceAll("(.)([A-ZÆØÅ])", "$1 $2");
    }

    public Class<?> identifyReturnType
            (Class<? extends Component> className, String parameter) throws NoSuchMethodException {
        return className.getMethod("get" + firstLetterUppercase(parameter)).getReturnType();
    }

    private TableColumn<S, ?> createColumn(Class<? extends Component> className, String parameter) throws NoSuchMethodException {
        if (identifyReturnType(className, parameter) == String.class) {
            TableColumn<S, String> tableColumn = new TableColumn<>(columnName(parameter));
            tableColumn.setCellValueFactory(new PropertyValueFactory<>(parameter));
            return tableColumn;
        } else if (identifyReturnType(className, parameter) == Double.class) {
            TableColumn<S, Double> tableColumn = new TableColumn<>(columnName(parameter));
            tableColumn.setCellValueFactory(new PropertyValueFactory<>(parameter));
            return tableColumn;
        } else {
            TableColumn<S, Integer> tableColumn = new TableColumn<>(columnName(parameter));
            tableColumn.setCellValueFactory(new PropertyValueFactory<>(parameter));
            return tableColumn;
        }
    }
}
