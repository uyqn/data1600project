package listManager;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public abstract class attachTableView {
    private ArrayList<TableColumn> listOfColumns = new ArrayList<>();

    abstract void setTableView(TableView tableView);

    public void setColumns(Class className, String... parameters){

    }

    private String firstLetterUppercase(String str){
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }

    private String identifyClass(Class className){
        return className.getName().substring(className.getName().indexOf(".")+1);
    }

    private TableColumn createColumn(Class className, String parameter){
        return null;
    }
}
