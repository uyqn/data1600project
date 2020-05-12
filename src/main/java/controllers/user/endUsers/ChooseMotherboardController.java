package controllers.user.endUsers;

import components.CPU;
import components.Component;
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

public class ChooseMotherboardController implements Initializable {

    @FXML
    private TableView<Component> tableView;

    @FXML
    private TableColumn<Motherboard, String> ManufacturerColumn;

    @FXML
    private TableColumn<Motherboard, String> ModelColumn;

    @FXML
    private TableColumn<Motherboard, Double> PriceColumn;

    @FXML
    private TableColumn<Motherboard, Integer> PSpacesColumn;

    @FXML
    private TableColumn<Motherboard, Integer> MaxRamColumn;

    @FXML
    private TableColumn<Motherboard, String> BoostTypeColumn;

    @FXML
    private TableColumn<Motherboard, String> SocketColumn;

    @FXML
    private TableColumn<Motherboard, String> BussTypeColumn;

    @FXML
    private TableColumn<Motherboard, String> FormFactorColumn;

    @FXML
    private TableColumn<Motherboard, String> MemoryColumn;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Setter opp kolonner

        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<Motherboard, String>("manufacturer"));
        ModelColumn.setCellValueFactory(new PropertyValueFactory<Motherboard, String>("model"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<Motherboard, Double>("price"));
        PSpacesColumn.setCellValueFactory(new PropertyValueFactory<Motherboard, Integer>("ProcessorSpaces"));
        MaxRamColumn.setCellValueFactory(new PropertyValueFactory<Motherboard, Integer>("maxRamSize"));
        BoostTypeColumn.setCellValueFactory(new PropertyValueFactory<Motherboard, String>("boostType"));
        SocketColumn.setCellValueFactory(new PropertyValueFactory<Motherboard, String>("socket"));
        BussTypeColumn.setCellValueFactory(new PropertyValueFactory<Motherboard, String>("bussType"));
        FormFactorColumn.setCellValueFactory(new PropertyValueFactory<Motherboard, String>("formFactor"));
        MemoryColumn.setCellValueFactory(new PropertyValueFactory<Motherboard, String>("memoryTech"));

        tableView.setItems(
                App.componentList.getList().stream().filter(component ->
                        component.getComponentType().equals(Motherboard.COMPONENT_TYPE)
                ).collect(Collectors.toCollection(FXCollections::observableArrayList))
        );

    }
    @FXML
    private Button backBtn;

    @FXML
    private Button nextBtn;

    @FXML
    void GoBack(ActionEvent event) throws IOException {

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseGpu.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML
    void GoNext(ActionEvent event) throws IOException {

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseMemory.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();


    }
}
