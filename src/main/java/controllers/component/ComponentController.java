package controllers.component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ComponentController {

    @FXML
    private GridPane addCPUgui;

    @FXML
    private Button decreaseCore;

    @FXML
    private Button increaseCore;

    @FXML
    void addCPU(ActionEvent event) {

    }

    @FXML
    void close(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

}
