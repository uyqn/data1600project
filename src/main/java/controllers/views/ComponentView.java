package controllers.views;

import components.Component;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.App;

import java.net.URL;
import java.util.ResourceBundle;

public class ComponentView implements Initializable {

    @FXML
    private TableView<Component> componentView;

    @FXML
    private TableColumn<Component, String> typeCol;

    @FXML
    private TableColumn<Component, String> manufacturerCol;

    @FXML
    private TableColumn<Component, String> modelCol;

    @FXML
    private TableColumn<Component, Double> priceCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        App.componentList.setTableView(componentView);

        typeCol.setCellValueFactory(new PropertyValueFactory<>("COMPONENT_TYPE"));
    }
}
