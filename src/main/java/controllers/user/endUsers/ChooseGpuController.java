package controllers.user.endUsers;

import components.Component;
import components.Computer;
import components.GPU;
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
    private TableColumn<GPU, String> BoostClockColumn;

    @FXML
    private TreeView<String> treeView;

    @FXML
    private Label priceLabel;

    @FXML
    private ChoiceBox<String> filterBox;

    @FXML
    private TextField filterText;

    ObservableList<Component> gpuList = App.listableList.getList().stream().filter(component ->
            component.getComponentType().equals(GPU.COMPONENT_TYPE)
    ).collect(Collectors.toCollection(FXCollections::observableArrayList));

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        filterBox.getItems().setAll(
                "Manufacturer",
                "Model",
                "Price (NOK) ≤",
                "Buss type",
                "Memory (GB) ≤",
                "Memory Type",
                "Boost clock (MHz) ≤");
        filterBox.setValue(null);
        filterText.setText(null);

        //Enabler next-button idet man velger en komponent
        nextBtn.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));
        //Setter opp kolonner

        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<GPU, String>("manufacturer"));
        ModelColumn.setCellValueFactory(new PropertyValueFactory<GPU, String>("model"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<GPU, Double>("price"));
        BussTypeColumn.setCellValueFactory(new PropertyValueFactory<GPU, String>("bussType"));
        MemoryColumn.setCellValueFactory(new PropertyValueFactory<GPU, String>("memory"));
        MemoryTypeColumn.setCellValueFactory(new PropertyValueFactory<GPU, String>("memoryType"));
        BoostClockColumn.setCellValueFactory(new PropertyValueFactory<GPU, String>("boostSpeed"));

        tableView.setItems(gpuList);
        try{
            treeView = App.computer.setTreeView(treeView);
            treeView.refresh();
            priceLabel.setText("Total price: " + App.computer.getPrice() + " NOK");
            if(treeView.getTreeItem(1).getValue().contains("GPU")){
                nextBtn.disableProperty().unbind();
                nextBtn.setDisable(false);
            }
        } catch (NullPointerException ignored){}
    }

    @FXML
    void filterEvt(KeyEvent event) {
        String search = filterText.getText().toLowerCase();
        int filterIndex = filterBox.getSelectionModel().getSelectedIndex();

        tableView.setItems(gpuList.stream().filter(component -> {
            if (search.isBlank() || search.isEmpty() || filterBox.getSelectionModel().getSelectedItem() == null) {
                return true;
            } else {
                switch (filterIndex) {
                    case 0:
                        return component.getManufacturer().toLowerCase().contains(search);
                    case 1:
                        return component.getModel().toLowerCase().contains(search);
                    case 3:
                        return component.getBussType().toLowerCase().contains(search);
                    case 4:
                        try {
                            return component.getMemory() <= Integer.parseInt(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 5:
                        return component.getMemoryTech().toLowerCase().contains(search);
                    case 6:
                        try {
                            return component.getBoostSpeed() <= Double.parseDouble(search);
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
    void GoBack(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/buildPc.fxml"));

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
            if(tableView.getSelectionModel().getSelectedItem() != null) {
                App.computer.setGpu((GPU) tableView.getSelectionModel().getSelectedItem());
            }

            Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseMotherboard.fxml"));

            Scene scene = new Scene(view);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (NotCompatibleException e) {
            DialogBox.error("Not compatible!", null, e.getMessage());
            tableView.getSelectionModel().clearSelection();
        }
    }

}
