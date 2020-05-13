package controllers.user.endUsers;

import components.Component;
import components.Storage.HDD;
import components.Storage.SSD;
import javafx.beans.binding.Bindings;
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

public class ChooseSsdController implements Initializable {

    @FXML
    private TableView<Component> tableView;

    @FXML
    private TableColumn<SSD, String> ManufacturerColumn;

    @FXML
    private TableColumn<SSD, String> ModelColumn;

    @FXML
    private TableColumn<SSD, Double> PriceColumn;

    @FXML
    private TableColumn<SSD, Double> CapacityColumn;

    @FXML
    private TableColumn<SSD, Integer> RpmColumn;

    @FXML
    private TableColumn<SSD, String> FormFactorColumn;



    @FXML
    private ChoiceBox<String> filterBox;

    @FXML
    private TextField filterText;

    ObservableList<Component> ssdList = App.componentList.getList().stream().filter(component ->
            component.getComponentType().equals(SSD.COMPONENT_TYPE)
    ).collect(Collectors.toCollection(FXCollections::observableArrayList));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filterBox.getItems().setAll(
                "Manufacturer",
                "Model",
                "Capacity (GB) ≤",
                "Price (NOK) ≤");
        filterBox.setValue(null);
        filterText.setText(null);


        //Setter opp kolonner
        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<SSD, String>("manufacturer"));
        ModelColumn.setCellValueFactory(new PropertyValueFactory<SSD, String>("model"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<SSD, Double>("price"));
        CapacityColumn.setCellValueFactory(new PropertyValueFactory<SSD, Double>("capacity"));
        RpmColumn.setCellValueFactory(new PropertyValueFactory<SSD, Integer>("rpm"));
        FormFactorColumn.setCellValueFactory(new PropertyValueFactory<SSD, String>("form"));


        tableView.setItems(
                ssdList
        );

    }

    @FXML
    void filterEvt(KeyEvent event) {
        String search = filterText.getText().toLowerCase();
        int filterIndex = filterBox.getSelectionModel().getSelectedIndex();

        tableView.setItems(ssdList.stream().filter(component -> {
            if (search.isBlank() || search.isEmpty() || filterBox.getSelectionModel().getSelectedItem() == null) {
                return true;
            } else {
                switch (filterIndex) {
                    case 0:
                        return component.getManufacturer().toLowerCase().contains(search);
                    case 1:
                        return component.getModel().toLowerCase().contains(search);
                    case 2:
                        try {
                            return component.getCapacity() <= Integer.parseInt(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 3:
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
    void GoBack(ActionEvent event) throws IOException {

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseHdd.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML
    void GoNext(ActionEvent event) throws IOException {

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseCooler.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }
}
