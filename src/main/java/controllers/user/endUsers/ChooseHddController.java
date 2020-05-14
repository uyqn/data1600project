package controllers.user.endUsers;

import components.Component;
import components.Storage.HDD;
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
import javafx.scene.input.KeyEvent;
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
    private ChoiceBox<String> filterBox;

    @FXML
    private TextField filterText;

    ObservableList<Component> hddList = App.listableList.getList().stream().filter(component ->
            component.getComponentType().equals(HDD.COMPONENT_TYPE)
    ).collect(Collectors.toCollection(FXCollections::observableArrayList));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filterBox.getItems().setAll(
                "Manufacturer",
                "Model",
                "Price (NOK) ≤",
                "Capacity (GB) ≤",
                "RPM ≤");
        filterBox.setValue(null);
        filterText.setText(null);


        //Setter opp kolonner

        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<HDD, String>("manufacturer"));
        ModelColumn.setCellValueFactory(new PropertyValueFactory<HDD, String>("model"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<HDD, Double>("price"));
        CapacityColumn.setCellValueFactory(new PropertyValueFactory<HDD, Integer>("capacity"));
        RpmColumn.setCellValueFactory(new PropertyValueFactory<HDD, String>("rpm"));


        tableView.setItems(
                App.listableList.getList().stream().filter(component ->
                        component.getComponentType().equals(HDD.COMPONENT_TYPE)
                ).collect(Collectors.toCollection(FXCollections::observableArrayList))
        );

    }

    @FXML
    void filterEvt(KeyEvent event) {
        String search = filterText.getText().toLowerCase();
        int filterIndex = filterBox.getSelectionModel().getSelectedIndex();

        tableView.setItems(hddList.stream().filter(component -> {
            if (search.isBlank() || search.isEmpty() || filterBox.getSelectionModel().getSelectedItem() == null) {
                return true;
            } else {
                switch (filterIndex) {
                    case 0:
                        return component.getManufacturer().toLowerCase().contains(search);
                    case 1:
                        return component.getModel().toLowerCase().contains(search);
                    case 3:
                        try {
                            return component.getCapacity() <= Integer.parseInt(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 4:
                        try {
                            return component.getRpm() <= Integer.parseInt(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 2:
                        try {
                            return component.getPrice() <= Double.parseDouble(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    default:
                        return false;
                }
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }

    @FXML
    private Button backBtn;

    @FXML
    private Button nextBtn;

    @FXML
    private Button addBtn;

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

    @FXML
    void addEvt(ActionEvent event) throws IOException{

    }
}
