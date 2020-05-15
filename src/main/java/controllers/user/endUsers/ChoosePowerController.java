package controllers.user.endUsers;

import components.Component;
import components.PSU;
import controllers.guiManager.DialogBox;
import exceptions.NotCompatibleException;
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

public class ChoosePowerController implements Initializable {

    @FXML
    private TableView<Component> tableView;

    @FXML
    private TableColumn<PSU, String> ManufacturerColumn;

    @FXML
    private TableColumn<PSU, String> ModelColumn;

    @FXML
    private TableColumn<PSU, Double> PriceColumn;

    @FXML
    private TableColumn<PSU, Integer> PowerColumn;

    @FXML
    private ChoiceBox<String> filterBox;

    @FXML
    private TextField filterText;

    @FXML
    private TreeView<String> treeView=new TreeView<>();

    @FXML
    private Label priceLabel;

    ObservableList<Component> powerList = App.listableList.getList().stream().filter(component ->
            component.getComponentType().equals(PSU.COMPONENT_TYPE)
    ).collect(Collectors.toCollection(FXCollections::observableArrayList));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filterBox.getItems().setAll(
                "Manufacturer",
                "Model",
                "Price (NOK) ≤",
                "Power capacity (W) ≤");
        filterBox.setValue(null);
        filterText.setText(null);

        //Enabler next-button idet man velger en komponent
       addBtn.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));

        //Setter opp kolonner

        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<PSU, String>("manufacturer"));
        ModelColumn.setCellValueFactory(new PropertyValueFactory<PSU, String>("model"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<PSU, Double>("price"));
        PowerColumn.setCellValueFactory(new PropertyValueFactory<PSU, Integer>("powerCapacity"));


        tableView.setItems(powerList);
               /* App.listableList.getList().stream().filter(component ->
                        component.getComponentType().equals(PSU.COMPONENT_TYPE)
                ).collect(Collectors.toCollection(FXCollections::observableArrayList))*/

        try{
            treeView = App.computer.setTreeView(treeView);
            treeView.refresh();
            priceLabel.setText("Total price: " + App.computer.getPrice() + " NOK");
            if(App.computer.getPsu() != null) {
                addBtn.disableProperty().unbind();
                addBtn.setDisable(false);
            }
        } catch (NullPointerException ignored){}
    }

    @FXML
    void filterEvt(KeyEvent event) {
        String search = filterText.getText().toLowerCase();
        int filterIndex = filterBox.getSelectionModel().getSelectedIndex();

        tableView.setItems(powerList.stream().filter(component -> {
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
                            return component.getPowerCapacity() <= Integer.parseInt(search);
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
    private Button addBtn;

    @FXML
    void AddPSU(ActionEvent event) throws IOException {
        try{
            if(tableView.getSelectionModel().getSelectedItem() != null) {
                App.computer.setPsu((PSU) tableView.getSelectionModel().getSelectedItem());
            }

            Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseCabin.fxml"));

            Scene scene = new Scene(view);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (NotCompatibleException e){
            DialogBox.error("Not compatible!", null, e.getMessage());
            tableView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseCooler.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }


}
