package controllers.user.endUsers;

import components.CPU;
import components.Component;
import components.Computer;
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

public class BuildPcController implements Initializable {

    //Tableview med CPU-komponenter
    @FXML
    private TableView<Component> tableView;

    @FXML
    private TableColumn<Component, String> ManufacturerColumn;

    @FXML
    private TableColumn<Component, String> ModelColumn;

    @FXML
    private TableColumn<Component, Double> PriceColumn;

    @FXML
    private TableColumn<Component, String> SocketColumn;

    @FXML
    private TableColumn<Component, Integer> CoreCountColumn;

    @FXML
    private TableColumn<Component, Double> CoreClockColumn;

    @FXML
    private TableColumn<Component, Double> BoostClockColumn;

    @FXML
    private TableColumn<Component, Double> PowerColumn;

    @FXML
    private ChoiceBox<String> filterBox;

    @FXML
    private TextField filterText;

    @FXML
    private TreeView<String> treeView = new TreeView<>();

    @FXML
    private Label priceLabel;

    ObservableList<Component> cpuList = App.listableList.getList().stream().filter(component ->
            component.getComponentType().equals(CPU.COMPONENT_TYPE)
    ).collect(Collectors.toCollection(FXCollections::observableArrayList));

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Enabler next-button idet man velger en komponent
        addBtn.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));

        filterBox.getItems().setAll("Manufacturer", "Model", "Price (NOK) ≤", "Socket", "Core Count",
                "Core Clock", "Boost Clock", "Power Consumption");
        filterBox.setValue(null);

        //Setter opp kolonner

        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        ModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        SocketColumn.setCellValueFactory(new PropertyValueFactory<>("socket"));
        CoreCountColumn.setCellValueFactory(new PropertyValueFactory<>("coreCount"));
        CoreClockColumn.setCellValueFactory(new PropertyValueFactory<>("coreClock"));
        BoostClockColumn.setCellValueFactory(new PropertyValueFactory<>("boostClock"));
        PowerColumn.setCellValueFactory(new PropertyValueFactory<>("powerConsumption"));

        tableView.setItems(cpuList);
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

        tableView.setItems(cpuList.stream().filter(component -> {
            if(search.isBlank() || search.isEmpty() || filterBox.getSelectionModel().getSelectedItem() == null){
                return true;
            }
            else {
                switch (filterIndex){
                    case 0:
                        return component.getManufacturer().toLowerCase().contains(search);
                    case 1:
                        return component.getModel().toLowerCase().contains(search);
                    case 2:
                        return component.getSocket().toLowerCase().contains(search);
                    case 3:
                        try {
                            return component.getCoreCount() <= Integer.parseInt(search);
                        } catch (NumberFormatException e){
                            return false;
                        }
                    case 4:
                        try {
                            return component.getCoreClock() <= Double.parseDouble(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 5:
                        try {
                            return component.getBoostClock() <= Double.parseDouble(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 6:
                        try {
                            return component.getPowerConsumption() <= Double.parseDouble(search);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    case 7:
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
    private Button homeBtn;

    @FXML
    private Button addBtn;

    @FXML
    void AddGpu(ActionEvent event) throws IOException {
        if(App.computer == null){
            App.computer = new Computer();
        }

        App.computer.setCpu((CPU) tableView.getSelectionModel().getSelectedItem());

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseGpu.fxml"));
        Scene scene = new Scene(view);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window. setScene(scene);
        window.show();
    }

    @FXML
    void backHome(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUser.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

}
