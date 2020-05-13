package controllers.user.endUsers;

import components.Component;
import components.Mouse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ChooseMouseController implements Initializable {

    @FXML
    private TableView<Component> tableView;

    @FXML
    private TableColumn<Mouse, String> ManufacturerColumn;

    @FXML
    private TableColumn<Mouse, String> ModelColumn;

    @FXML
    private TableColumn<Mouse, Double> PriceColumn;

    @FXML
    private TableColumn<Mouse, Integer> ButtonsColumn;

    @FXML
    private TableColumn<Mouse, Integer> DpiColumn;

    @FXML
    private TableColumn<Mouse, Boolean> ErgonomicColumn;

    @FXML
    private TableColumn<Mouse, Boolean> WirelessColumn;


    @FXML
    private ChoiceBox<String> filterBox;

    @FXML
    private TextField filterText;

    ObservableList<Component> mouseList = App.listableList.getList().stream().filter(component ->
            component.getComponentType().equals(Mouse.COMPONENT_TYPE)
    ).collect(Collectors.toCollection(FXCollections::observableArrayList));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filterBox.getItems().setAll(
                "Manufacturer",
                "Model",
                "Buttons ≤",
                "Polling rate (dpi) ≤",
                "Ergonomic",
                "Not ergonomic",
                "Wireless",
                "Wired",
                "Price (NOK) ≤");
        filterBox.setValue(null);
        filterText.setText(null);

        //Setter opp kolonner

        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<Mouse, String>("manufacturer"));
        ModelColumn.setCellValueFactory(new PropertyValueFactory<Mouse, String>("model"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<Mouse, Double>("price"));
        ButtonsColumn.setCellValueFactory(new PropertyValueFactory<Mouse, Integer>("price"));
        DpiColumn.setCellValueFactory(new PropertyValueFactory<Mouse, Integer>("price"));
        ErgonomicColumn.setCellValueFactory(new PropertyValueFactory<Mouse, Boolean>("ergonomic"));
        WirelessColumn.setCellValueFactory(new PropertyValueFactory<Mouse, Boolean>("wireless"));


        tableView.setItems(
                App.listableList.getList().stream().filter(component ->
                        component.getComponentType().equals(Mouse.COMPONENT_TYPE)
                ).collect(Collectors.toCollection(FXCollections::observableArrayList))
        );

    }
    @FXML
    private Button backBtn;

    @FXML
    private Button nextBtn;

    @FXML
    void GoBack(ActionEvent event) throws IOException {

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseCabin.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML
    void GoNext(ActionEvent event) throws IOException {

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseMonitor.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();


    }
}
