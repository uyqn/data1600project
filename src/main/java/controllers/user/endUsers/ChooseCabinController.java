package controllers.user.endUsers;

import components.Cabin;
import components.Component;
import components.GPU;
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

public class ChooseCabinController implements Initializable {

    @FXML
    private TableView<Component> tableView;

    @FXML
    private TableColumn<Cabin, String> ManufacturerColumn;

    @FXML
    private TableColumn<Cabin, String> ModelColumn;

    @FXML
    private TableColumn<Cabin, Double> PriceColumn;

    @FXML
    private TableColumn<Cabin, String> FormFactorColumn;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Setter opp kolonner

        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<Cabin, String>("manufacturer"));
        ModelColumn.setCellValueFactory(new PropertyValueFactory<Cabin, String>("model"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<Cabin, Double>("price"));
        FormFactorColumn.setCellValueFactory(new PropertyValueFactory<Cabin, String>("formFactor"));



        tableView.setItems(
                App.componentList.getList().stream().filter(component ->
                        component.getComponentType().equals(Cabin.COMPONENT_TYPE)
                ).collect(Collectors.toCollection(FXCollections::observableArrayList))
        );

    }

    @FXML
    private Button backBtn;

    @FXML
    private Button nextBtn;

    @FXML
    void GoBack(ActionEvent event) throws IOException {

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChoosePower.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML
    void GoNext(ActionEvent event) throws IOException {

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseMouse.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();


    }
}
