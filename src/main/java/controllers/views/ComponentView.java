package controllers.views;

import components.Component;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import main.App;

import java.net.URL;
import java.util.ResourceBundle;

public class ComponentView implements Initializable {

    @FXML
    private TableView<Component> componentView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        App.componentList.setTableView(componentView);
    }
}
