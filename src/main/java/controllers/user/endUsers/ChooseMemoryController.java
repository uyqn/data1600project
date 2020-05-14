package controllers.user.endUsers;

import components.Component;
import components.Memory;
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

public class ChooseMemoryController implements Initializable {

    @FXML
    private TableView<Component> tableView;

    @FXML
    private TableColumn<Memory, String> ManufacturerColumn;

    @FXML
    private TableColumn<Memory, String> ModelColumn;

    @FXML
    private TableColumn<Memory, Double> PriceColumn;

    @FXML
    private TableColumn<Memory, Integer> RamColumn;

    @FXML
    private TableColumn<Memory, String> MemoryColumn;

    @FXML
    private TableColumn<Memory, Integer> SpeedColumn;

    @FXML
    private ChoiceBox<String> filterBox;

    @FXML
    private TreeView<String> treeView;

    @FXML
    private Label priceLabel;

    @FXML
    private TextField filterText;


    ObservableList<Component> memoryList = App.listableList.getList().stream().filter(component ->
            component.getComponentType().equals(Memory.COMPONENT_TYPE)
    ).collect(Collectors.toCollection(FXCollections::observableArrayList));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filterBox.getItems().setAll(
                "Manufacturer",
                "Model",
                "Price (NOK) ≤",
                "RAM (GB) ≤",
                "Technology",
                "Speed (MHz) ≤");
        filterBox.setValue(null);
        filterText.setText(null);

        //Enabler next-button idet man velger en komponent
        nextBtn.setDisable(true);
        addBtn.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));

        //Setter opp kolonner

        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<Memory, String>("manufacturer"));
        ModelColumn.setCellValueFactory(new PropertyValueFactory<Memory, String>("model"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<Memory, Double>("price"));
        RamColumn.setCellValueFactory(new PropertyValueFactory<Memory, Integer>("ram"));
        MemoryColumn.setCellValueFactory(new PropertyValueFactory<Memory, String>("memoryTech"));
        SpeedColumn.setCellValueFactory(new PropertyValueFactory<Memory, Integer>("speed"));

        tableView.setItems(memoryList);

        try{
            treeView = App.computer.setTreeView(treeView);
            treeView.refresh();
            priceLabel.setText("Total price: " + App.computer.getPrice() + " NOK");
            if(treeView.getTreeItem(3).getValue().contains("Memor")) {
                nextBtn.setDisable(false);
            }
        } catch (NullPointerException ignored){}
    }

    @FXML
    void filterEvt(KeyEvent event) {
        String search = filterText.getText().toLowerCase();
        int filterIndex = filterBox.getSelectionModel().getSelectedIndex();

        tableView.setItems(memoryList.stream().filter(component -> {
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
                            return component.getRam() <= Integer.parseInt(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 5:
                        try {
                            return component.getSpeed() <= Integer.parseInt(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 4:
                        return component.getMemoryTech().toLowerCase().contains(search);
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
        try {
            App.computer.addMemory((Memory) tableView.getSelectionModel().getSelectedItem());
        } catch (NotCompatibleException | NullPointerException ignored){}

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseMotherboard.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    void GoNext(ActionEvent event) throws IOException {
        try{
            if(tableView.getSelectionModel().getSelectedItem() != null) {
                App.computer.addMemory((Memory) tableView.getSelectionModel().getSelectedItem());
            }
            nextBtn.setDisable(false);

            Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseHdd.fxml"));

            Scene scene = new Scene(view);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (NotCompatibleException e){
            DialogBox.error("Not compatible!", null, e.getMessage());
        }
    }

    @FXML
    void addEvt(ActionEvent event) throws IOException {
        try{
            if(tableView.getSelectionModel().getSelectedItem() != null) {
                App.computer.addMemory((Memory) tableView.getSelectionModel().getSelectedItem());
            }
            treeView = App.computer.setTreeView(treeView);
            treeView.refresh();
            nextBtn.setDisable(false);
        } catch (NotCompatibleException e){
            DialogBox.error("Not compatible!", null, e.getMessage());
        }
    }

}
