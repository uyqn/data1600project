package controllers.user.endUsers;

import components.Component;
import components.Keyboard;
import components.Monitor;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ChooseMonitorController implements Initializable {

    @FXML
    private TableView<Component> tableView;

    @FXML
    private TableColumn<Monitor, String> ManufacturerColumn;

    @FXML
    private TableColumn<Monitor, String> ModelColumn;

    @FXML
    private TableColumn<Monitor, Double> PriceColumn;

    @FXML
    private TableColumn<Monitor, Integer> RefreshRateColumn;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Setter opp kolonner

        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<Monitor, String>("manufacturer"));
        ModelColumn.setCellValueFactory(new PropertyValueFactory<Monitor, String>("model"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<Monitor, Double>("price"));
        RefreshRateColumn.setCellValueFactory(new PropertyValueFactory<Monitor, Integer>("refreshRate"));


        tableView.setItems(
                App.componentList.getList().stream().filter(component ->
                        component.getComponentType().equals(Monitor.COMPONENT_TYPE)
                ).collect(Collectors.toCollection(FXCollections::observableArrayList))
        );

    }

    @FXML
    private Button backBtn;

    @FXML
    private Button nextBtn;

    @FXML
    void GoBack(ActionEvent event) throws IOException {

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseMouse.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML
    void GoNext(ActionEvent event) throws IOException {

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseKeyboard.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();


    }
}
