package controllers.user.endUsers;

import components.Cabin;
import components.Component;
import components.Cooler;
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

    @FXML
    private ChoiceBox<String> filterBox;

    @FXML
    private TextField filterText;

    ObservableList<Component> coolerList = App.componentList.getList().stream().filter(component ->
            component.getComponentType().equals(Cooler.COMPONENT_TYPE)
    ).collect(Collectors.toCollection(FXCollections::observableArrayList));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filterBox.getItems().setAll(
                "Manufacturer",
                "Model",
                "Width (cm) ≤",
                "Depth (cm) ≤",
                "Height (cm) ≤",
                "Base RPM ≤",
                "Max RPM ≤",
                "Base noise (dBA) ≤",
                "Max noise (dBA) ≤",
                "Power consumption ≤",
                "Price (NOK) ≤");
        filterBox.setValue(null);
        filterText.setText(null);

        //Enabler next-button idet man velger en komponent
        nextBtn.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));

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
    void filterEvt(KeyEvent event) {
        String search = filterText.getText().toLowerCase();
        int filterIndex = filterBox.getSelectionModel().getSelectedIndex();

        tableView.setItems(coolerList.stream().filter(component -> {
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
                            return component.getWidth() <= Double.parseDouble(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 3:
                        try {
                            return component.getDepth() <= Double.parseDouble(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 4:
                        try {
                            return component.getHeight() <= Double.parseDouble(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 5:
                        try {
                            return component.getCoreRpm() <= Integer.parseInt(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 6:
                        try {
                            return component.getMaxRpm() <= Integer.parseInt(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 7:
                        try {
                            return component.getCoreNoise() <= Double.parseDouble(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 8:
                        try {
                            return component.getMaxNoise() <= Double.parseDouble(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 9:
                        try {
                            return component.getPowerConsumption() <= Double.parseDouble(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 10:
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
