package controllers.user.endUsers;

import components.Component;
import components.Memory;
import components.Motherboard;
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

public class ChooseMemoryController implements Initializable {

    @FXML
    private TableView<Component> tableView;

    @FXML
    private TableColumn<Memory, String> ManufacturerColumn;

    @FXML
    private TableColumn<Memory, String> ModelColumn;

    @FXML
    private TableColumn<Memory, Double> PriceColumn;

    @FXML
    private TableColumn<Memory, Integer> RamColumn;

    @FXML
    private TableColumn<Memory, String> MemoryColumn;

    @FXML
    private TableColumn<Memory, Integer> SpeedColumn;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Setter opp kolonner

        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<Memory, String>("manufacturer"));
        ModelColumn.setCellValueFactory(new PropertyValueFactory<Memory, String>("model"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<Memory, Double>("price"));
        RamColumn.setCellValueFactory(new PropertyValueFactory<Memory, Integer>("ram"));
        MemoryColumn.setCellValueFactory(new PropertyValueFactory<Memory, String>("memoryTech"));
        SpeedColumn.setCellValueFactory(new PropertyValueFactory<Memory, Integer>("speed"));

        tableView.setItems(
                App.componentList.getList().stream().filter(component ->
                        component.getComponentType().equals(Memory.COMPONENT_TYPE)
                ).collect(Collectors.toCollection(FXCollections::observableArrayList))
        );

    }

    @FXML
    private Button backBtn;

    @FXML
    private Button nextBtn;

    @FXML
    void GoBack(ActionEvent event) throws IOException {

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseMotherboard.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML
    void GoNext(ActionEvent event) throws IOException {

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseHdd.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();


    }
}
