package controllers.views;

import components.Component;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import main.App;

import java.net.URL;
import java.util.ResourceBundle;

public class ComponentView implements Initializable {

    @FXML
    private TableView<Component> componentView;

    @FXML
    private Label detailedView;

    @FXML
    void viewDetailsKey(KeyEvent event) {
        detailedView.setText(
                componentView.getSelectionModel().getSelectedItem().toString()
        );
    }

    @FXML
    void viewDetailsMouse(MouseEvent event) {
        detailedView.setText(
                componentView.getSelectionModel().getSelectedItem().toString()
        );
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        App.componentList.setTableView(componentView);
    }
}
