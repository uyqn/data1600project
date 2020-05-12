package controllers.user.endUsers;

import components.Component;
import components.Memory;
import components.Storage.HDD;
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

public class ChooseHddController implements Initializable {

    @FXML
    private TableView<Component> tableView;

    @FXML
    private TableColumn<HDD, String> ManufacturerColumn;

    @FXML
    private TableColumn<HDD, String> ModelColumn;

    @FXML
    private TableColumn<HDD, Double> PriceColumn;

    @FXML
    private TableColumn<HDD, Integer> CapacityColumn;

    @FXML
    private TableColumn<HDD, String> RpmColumn;

    @FXML
    private TableColumn<HDD, String> FormFactorColumn;



    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Setter opp kolonner

        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<HDD, String>("manufacturer"));
        ModelColumn.setCellValueFactory(new PropertyValueFactory<HDD, String>("model"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<HDD, Double>("price"));
        CapacityColumn.setCellValueFactory(new PropertyValueFactory<HDD, Integer>("capacity"));
        RpmColumn.setCellValueFactory(new PropertyValueFactory<HDD, String>("rpm"));
        FormFactorColumn.setCellValueFactory(new PropertyValueFactory<HDD, String>("form"));


        tableView.setItems(
                App.componentList.getList().stream().filter(component ->
                        component.getComponentType().equals(HDD.COMPONENT_TYPE)
                ).collect(Collectors.toCollection(FXCollections::observableArrayList))
        );

    }
    @FXML
    private Button backBtn;

    @FXML
    private Button nextBtn;

    @FXML
    void GoBack(ActionEvent event) throws IOException {

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseMemory.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML
    void GoNext(ActionEvent event) throws IOException {

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseSsd.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();


    }
}
