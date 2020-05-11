package controllers.views;

import components.CPU;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ComponentView<E> implements Initializable {

    private TableView<CPU> cpuView = new TableView<>();

    @FXML
    private GridPane viewComponentsGui;

    private void getAllView(){
        TableColumn<CPU, String> typeCol = new TableColumn<>("Type");
        TableColumn<CPU, String> manufacturerCol = new TableColumn<>("Manufacturer");
        TableColumn<CPU, String> modelCol = new TableColumn<>("Model");
        TableColumn<CPU, Double> priceCol = new TableColumn<>("Price");

        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        manufacturerCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));

        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));

        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        cpuView.getColumns().add(typeCol);
        cpuView.getColumns().add(manufacturerCol);
        cpuView.getColumns().add(modelCol);
        cpuView.getColumns().add(priceCol);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAllView();
        viewComponentsGui.add(cpuView, 0, 2);
    }
}
