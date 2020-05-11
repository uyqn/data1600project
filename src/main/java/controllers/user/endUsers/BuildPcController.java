package controllers.user.endUsers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class BuildPcController {

    @FXML
    private TableView<?> tableView;

    @FXML
    private Button homeBtn;

    @FXML
    private Button addBtn;

    @FXML
    void AddGpu(ActionEvent event) throws IOException {

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseGpu.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML
    void backHome(ActionEvent event) throws IOException {

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUser.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

}
