package controllers.user.endUsers;

import components.Component;
import components.Computer;
import components.Cooler;
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
    private TableColumn<Cooler, String> RpmColumn;

    @FXML
    private TableColumn<Cooler, String> NoiseColumn;

    @FXML
    private TableColumn<Cooler, Double> PowerColumn;

    @FXML
    private ChoiceBox<String> filterBox;

    @FXML
    private TextField filterText;

    @FXML
    private TreeView<String> treeView;

    @FXML
    private Label priceLabel;

    ObservableList<Component> coolerList = App.listableList.getList().stream().filter(component ->
            component.getComponentType().equals(Cooler.COMPONENT_TYPE)
    ).collect(Collectors.toCollection(FXCollections::observableArrayList));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filterBox.getItems().setAll(
                "Manufacturer",
                "Model",
                "Price (NOK) ≤",
                "Base RPM ≤",
                "Max RPM ≥",
                "Base noise (dBA) ≤",
                "Max noise (dBA) ≥",
                "Power consumption ≤"
                );
        filterBox.setValue(null);
        filterText.setText(null);

        //Enabler next-button idet man velger en komponent
        addBtn.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));

        //Setter opp kolonner

        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        ModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        RpmColumn.setCellValueFactory(new PropertyValueFactory<>("rpmString"));
        NoiseColumn.setCellValueFactory(new PropertyValueFactory<>("noise"));
        PowerColumn.setCellValueFactory(new PropertyValueFactory<>("powerConsumption"));


        tableView.setItems(coolerList);

        try {
            treeView=App.computer.setTreeView(treeView);
            treeView.refresh();
            priceLabel.setText("Total price: "+App.computer.getPrice()+" NOK");
        }catch (NullPointerException ignored){}
    }

    @FXML
    void filterEvt(ActionEvent event) {
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
                        return component.getPrice() <= Double.parseDouble(search);
                        } catch (NumberFormatException e) {
                        return false;
                        }
                    case 3:
                        try {
                            return component.getCoreRpm() <= Integer.parseInt(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 4:
                        try {
                            return component.getMaxRpm() >= Integer.parseInt(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 5:
                        try {
                            return component.getCoreNoise() <= Double.parseDouble(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 6:
                        try {
                            return component.getMaxNoise() >= Double.parseDouble(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 7:
                        try {
                            return component.getPowerConsumption() <= Double.parseDouble(search);
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
    private Button addBtn;

    @FXML
    void goBack(ActionEvent event) throws IOException {



        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseSsd.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML
    void AddCooler(ActionEvent event) throws IOException {

        if (App.computer==null){
            App.computer=new Computer();
        }
        App.computer.setCooler((Cooler) tableView.getSelectionModel().getSelectedItem());


        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChoosePower.fxml"));
        Scene scene = new Scene(view);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }
}
