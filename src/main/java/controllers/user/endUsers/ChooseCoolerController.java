package controllers.user.endUsers;

import components.Cabin;
import components.Component;
import components.Cooler;
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

public class ChooseCoolerController implements Initializable {

    @FXML
    private TableView<Component> tableView;

    @FXML
    private TableColumn<Cooler, String> ManufacturerColumn;

    @FXML
    private TableColumn<Cooler, String> ModelColumn;

    @FXML
    private TableColumn<Cooler, Double> PriceColumn;

    @FXML
    private TableColumn<Cooler, Integer> CoreRpmColumn;

    @FXML
    private TableColumn<Cooler, Integer> MaxRpmColumn;

    @FXML
    private TableColumn<Cooler, Double> CoreNoiseColumn;

    @FXML
    private TableColumn<Cooler, Double> MaxNoiseColumn;

    @FXML
    private TableColumn<Cooler, Double> PowerColumn;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Setter opp kolonner

        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<Cooler, String>("manufacturer"));
        ModelColumn.setCellValueFactory(new PropertyValueFactory<Cooler, String>("model"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<Cooler, Double>("price"));
        CoreRpmColumn.setCellValueFactory(new PropertyValueFactory<Cooler, Integer>("coreRpm"));
        MaxRpmColumn.setCellValueFactory(new PropertyValueFactory<Cooler, Integer>("maxRpm"));
        CoreNoiseColumn.setCellValueFactory(new PropertyValueFactory<Cooler, Double>("coreNoise"));
        MaxNoiseColumn.setCellValueFactory(new PropertyValueFactory<Cooler, Double>("maxNoise"));
        PowerColumn.setCellValueFactory(new PropertyValueFactory<Cooler, Double>("powerConsumption"));



        tableView.setItems(
                App.componentList.getList().stream().filter(component ->
                        component.getComponentType().equals(Cooler.COMPONENT_TYPE)
                ).collect(Collectors.toCollection(FXCollections::observableArrayList))
        );

    }
    @FXML
    private Button backBtn;

    @FXML
    private Button nextBtn;

    @FXML
    void GoBack(ActionEvent event) throws IOException {

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseSsd.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML
    void GoNext(ActionEvent event) throws IOException {

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChoosePower.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();


    }
}
