package controllers.user.endUsers;

import components.Component;
import components.GPU;
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

public class ChooseGpuController implements Initializable {

    @FXML
    private TableView<Component> tableView;

    @FXML
    private TableColumn<GPU, String> ManufacturerColumn;

    @FXML
    private TableColumn<GPU, String> ModelColumn;

    @FXML
    private TableColumn<GPU, Double> PriceColumn;

    @FXML
    private TableColumn<GPU, String> BussTypeColumn;

    @FXML
    private TableColumn<GPU, String> MemoryColumn;

    @FXML
    private TableColumn<GPU, String> MemoryTypeColumn;

    @FXML
    private TableColumn<GPU, String> BaseClockColumn;

    @FXML
    private TableColumn<GPU, String> BoostClockColumn;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Setter opp kolonner

        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<GPU, String>("manufacturer"));
        ModelColumn.setCellValueFactory(new PropertyValueFactory<GPU, String>("model"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<GPU, Double>("price"));
        BussTypeColumn.setCellValueFactory(new PropertyValueFactory<GPU, String>("bussType"));
        MemoryColumn.setCellValueFactory(new PropertyValueFactory<GPU, String>("memory"));
        MemoryTypeColumn.setCellValueFactory(new PropertyValueFactory<GPU, String>("memoryType"));
        BaseClockColumn.setCellValueFactory(new PropertyValueFactory<GPU, String>("coreClock"));
        BoostClockColumn.setCellValueFactory(new PropertyValueFactory<GPU, String>("boostClock"));


        tableView.setItems(
                App.componentList.getList().stream().filter(component ->
                        component.getComponentType().equals(GPU.COMPONENT_TYPE)
                ).collect(Collectors.toCollection(FXCollections::observableArrayList))
        );

    }

    @FXML
    private Button backBtn;

    @FXML
    private Button nextBtn;

    @FXML
    void GoBack(ActionEvent event) throws IOException {

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/buildPc.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML
    void GoNext(ActionEvent event) throws IOException {

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseMotherboard.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();


    }

}
