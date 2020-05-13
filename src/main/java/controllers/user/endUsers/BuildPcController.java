package controllers.user.endUsers;

import components.CPU;
import components.Component;
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
    private TableColumn<CPU, String> ManufacturerColumn;

    @FXML
    private TableColumn<CPU, String> ModelColumn;

    @FXML
    private TableColumn<CPU, Double> PriceColumn;

    @FXML
    private TableColumn<CPU, String> SocketColumn;

    @FXML
    private TableColumn<CPU, Integer> CoreCountColumn;

    @FXML
    private TableColumn<CPU, Double> CoreClockColumn;

    @FXML
    private TableColumn<CPU, Double> BoostClockColumn;

    @FXML
    private TableColumn<CPU, Double> PowerColumn;

    @FXML
    private ChoiceBox<String> filterBox;

    @FXML
    private TextField filterText;

    ObservableList<Component> cpuList = App.componentList.getList().stream().filter(component ->
            component.getComponentType().equals(CPU.COMPONENT_TYPE)
    ).collect(Collectors.toCollection(FXCollections::observableArrayList));

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Enabler next-button idet man trykker
        addBtn.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));

        filterBox.getItems().setAll("Manufacturer", "Model", "Price (NOK) ≤", "Socket", "Core Count",
                "Core Clock", "Boost Clock", "Power Consumption");
        filterBox.setValue(null);

        //Setter opp kolonner

        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<CPU, String>("manufacturer"));
        ModelColumn.setCellValueFactory(new PropertyValueFactory<CPU, String>("model"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<CPU, Double>("price"));
        SocketColumn.setCellValueFactory(new PropertyValueFactory<CPU, String>("socket"));
        CoreCountColumn.setCellValueFactory(new PropertyValueFactory<CPU, Integer>("coreCount"));
        CoreClockColumn.setCellValueFactory(new PropertyValueFactory<CPU, Double>("coreClock"));
        BoostClockColumn.setCellValueFactory(new PropertyValueFactory<CPU, Double>("boostClock"));
        PowerColumn.setCellValueFactory(new PropertyValueFactory<CPU, Double>("powerConsumption"));


        tableView.setItems(cpuList);

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
                        try {
                            return component.getPrice() <= Double.parseDouble(search);
                        } catch (NumberFormatException e){
                            return false;
                        }
                    case 3:
                        return component.getSocket().toLowerCase().contains(search);
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

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseGpu.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
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
