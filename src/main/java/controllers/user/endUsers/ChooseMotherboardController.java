package controllers.user.endUsers;

import components.Component;
import components.Computer;
import components.Motherboard;
import components.NotCompatibleException;
import controllers.guiManager.DialogBox;
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
    private TableColumn<Motherboard, Integer> MaxRamColumn;


    @FXML
    private TableColumn<Motherboard, String> SocketColumn;

    @FXML
    private TableColumn<Motherboard, String> BussTypeColumn;

    @FXML
    private TableColumn<Motherboard, String> FormFactorColumn;

    @FXML
    private TableColumn<Motherboard, String> TechnologyColumn;

    @FXML
    private TableColumn<Motherboard, Integer> RamSlotColumn;

    @FXML
    private TreeView<String> treeView;

    @FXML
    private Label priceLabel;


    @FXML
    private ChoiceBox<String> filterBox;

    @FXML
    private TextField filterText;

    ObservableList<Component> mbList = App.listableList.getList().stream().filter(component ->
            component.getComponentType().equals(Motherboard.COMPONENT_TYPE)
    ).collect(Collectors.toCollection(FXCollections::observableArrayList));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filterBox.getItems().setAll(
                "Manufacturer",
                "Model",
                "Price (NOK) ≤",
                "Socket",
                "Buss type",
                "Ram slots ≤",
                "Max memory (GB) ≤",
                "Technology",
                "Form factor");
        filterBox.setValue(null);
        filterText.setText(null);

        //Enabler next-button idet man velger en komponent
        nextBtn.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));

        //Setter opp kolonner
        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<Motherboard, String>("manufacturer"));
        ModelColumn.setCellValueFactory(new PropertyValueFactory<Motherboard, String>("model"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<Motherboard, Double>("price"));
        RamSlotColumn.setCellValueFactory(new PropertyValueFactory<Motherboard, Integer>("RamSlots"));
        MaxRamColumn.setCellValueFactory(new PropertyValueFactory<Motherboard, Integer>("maxRamSize"));
        TechnologyColumn.setCellValueFactory(new PropertyValueFactory<Motherboard, String>("memoryTech"));
        SocketColumn.setCellValueFactory(new PropertyValueFactory<Motherboard, String>("socket"));
        BussTypeColumn.setCellValueFactory(new PropertyValueFactory<Motherboard, String>("BussType"));
        FormFactorColumn.setCellValueFactory(new PropertyValueFactory<Motherboard, String>("formFactor"));

        tableView.setItems(mbList);

        try{
            treeView = App.computer.setTreeView(treeView);
            treeView.refresh();
            priceLabel.setText("Total price: " + App.computer.getPrice() + " NOK");
        } catch (NullPointerException ignored){}
    }

    @FXML
    void filterEvt(KeyEvent event) {
        String search = filterText.getText().toLowerCase();
        int filterIndex = filterBox.getSelectionModel().getSelectedIndex();

        tableView.setItems(mbList.stream().filter(component -> {
            if (search.isBlank() || search.isEmpty() || filterBox.getSelectionModel().getSelectedItem() == null) {
                return true;
            } else {
                switch (filterIndex) {
                    case 0:
                        return component.getManufacturer().toLowerCase().contains(search);
                    case 1:
                        return component.getModel().toLowerCase().contains(search);
                    case 3:
                        return component.getSocket().toLowerCase().contains(search);
                    case 4:
                        return component.getBussType().toLowerCase().contains(search);
                    case 5:
                        try {
                            return component.getRamSlots() <= Integer.parseInt(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 7:
                        return component.getMemoryTech().toLowerCase().contains(search);
                    case 6:
                        try {
                            return component.getMaxRamSize() <= Integer.parseInt(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 8:
                        return component.getFormFactor().toLowerCase().contains(search);
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
    void GoBack(ActionEvent event) throws IOException {
        if(App.computer == null){
            App.computer = new Computer();
        }
        try {
            App.computer.setMotherboard((Motherboard) tableView.getSelectionModel().getSelectedItem());
        } catch (NotCompatibleException | NullPointerException ignored){}

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseGpu.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    void GoNext(ActionEvent event) throws IOException {
        if(App.computer == null){
            App.computer = new Computer();
        }
        try {
            App.computer.setMotherboard((Motherboard) tableView.getSelectionModel().getSelectedItem());

            Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseMemory.fxml"));

            Scene scene = new Scene(view);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (NotCompatibleException e){
            DialogBox.error("Not compatible", null, e.getMessage());
        }
    }
}
