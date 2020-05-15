package controllers.user.endUsers;

import components.Component;
import components.Computer;
import components.Monitor;
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

public class ChooseMonitorController implements Initializable {

    @FXML
    private TableView<Component> tableView;

    @FXML
    private TableColumn<Monitor, String> ManufacturerColumn;

    @FXML
    private TableColumn<Monitor, String> ModelColumn;

    @FXML
    private TableColumn<Monitor, Double> PriceColumn;

    @FXML
    private TableColumn<Monitor, Integer> RefreshRateColumn;


    @FXML
    private ChoiceBox<String> filterBox;

    @FXML
    private TextField filterText;

    @FXML
    private TreeView<String> treeView = new TreeView<>();

    @FXML
    private Label priceLabel;

    ObservableList<Component> monitorList = App.listableList.getList().stream().filter(component ->
            component.getComponentType().equals(Monitor.COMPONENT_TYPE)
    ).collect(Collectors.toCollection(FXCollections::observableArrayList));

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        filterBox.getItems().setAll(
                "Manufacturer",
                "Model",
                "Price (NOK) ≤",
                "Refresh rate (Hz)");
        filterBox.setValue(null);
        filterText.setText(null);


        //Setter opp kolonner

        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<Monitor, String>("manufacturer"));
        ModelColumn.setCellValueFactory(new PropertyValueFactory<Monitor, String>("model"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<Monitor, Double>("price"));
        RefreshRateColumn.setCellValueFactory(new PropertyValueFactory<Monitor, Integer>("refreshRate"));


        tableView.setItems(monitorList
                /*App.listableList.getList().stream().filter(component ->
                        component.getComponentType().equals(Monitor.COMPONENT_TYPE)
                ).collect(Collectors.toCollection(FXCollections::observableArrayList))*/
        );
        try{
            treeView=App.computer.setTreeView(treeView);
            treeView.refresh();
            priceLabel.setText("Total price: "+App.computer.getPrice()+" NOK");
        }catch (NullPointerException ignored){}
    }

    @FXML
    void filterEvt(KeyEvent event) {
        String search = filterText.getText().toLowerCase();
        int filterIndex = filterBox.getSelectionModel().getSelectedIndex();

        tableView.setItems(monitorList.stream().filter(component -> {
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
                            return component.getRefreshRate() <= Integer.parseInt(search);
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
    void goBack(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseMouse.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML
    void AddMonitor(ActionEvent event) throws IOException {
        if (App.computer==null){
            App.computer=new Computer();
        }

        if(tableView.getSelectionModel().getSelectedItem() != null) {
            App.computer.setMonitor((Monitor) tableView.getSelectionModel().getSelectedItem());
        }

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseKeyboard.fxml"));
        Scene scene = new Scene(view);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
