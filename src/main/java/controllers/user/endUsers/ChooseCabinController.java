package controllers.user.endUsers;

import components.Cabin;
import components.Component;
import components.Computer;
import components.NotCompatibleException;
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

public class ChooseCabinController implements Initializable {

    @FXML
    private TableView<Component> tableView;

    @FXML
    private TableColumn<Cabin, String> ManufacturerColumn;

    @FXML
    private TableColumn<Cabin, String> ModelColumn;

    @FXML
    private TableColumn<Cabin, Double> PriceColumn;

    @FXML
    private TableColumn<Cabin, String> FormFactorColumn;

    @FXML
    private ChoiceBox<String> filterBox;

    @FXML
    private TextField filterText;

    @FXML
    private TreeView<String> treeView = new TreeView<>();

    @FXML
    private Label priceLabel;

    ObservableList<Component> cabinList = App.listableList.getList().stream().filter(component ->
            component.getComponentType().equals(Cabin.COMPONENT_TYPE)
    ).collect(Collectors.toCollection(FXCollections::observableArrayList));

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        filterBox.getItems().setAll(
                "Manufacturer",
                "Model",
                "Price (NOK) ≤",
                "Form factor");
        filterBox.setValue(null);
        filterText.setText(null);

        addBtn.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));

        //Setter opp kolonner
        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<Cabin, String>("manufacturer"));
        ModelColumn.setCellValueFactory(new PropertyValueFactory<Cabin, String>("model"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<Cabin, Double>("price"));
        FormFactorColumn.setCellValueFactory(new PropertyValueFactory<Cabin, String>("formFactor"));


        tableView.setItems(cabinList);
        try{
            treeView = App.computer.setTreeView(treeView);
            treeView.refresh();
            priceLabel.setText("Total price: "+App.computer.getPrice()+" NOK");

        }catch (NullPointerException ignored){}
    }

    @FXML
    void filterEvt(KeyEvent event) {
        String search = filterText.getText().toLowerCase();
        int filterIndex = filterBox.getSelectionModel().getSelectedIndex();

        tableView.setItems(cabinList.stream().filter(component -> {
            if (search.isBlank() || search.isEmpty() || filterBox.getSelectionModel().getSelectedItem() == null) {
                return true;
            } else {
                switch (filterIndex) {
                    case 0:
                        return component.getManufacturer().toLowerCase().contains(search);
                    case 1:
                        return component.getModel().toLowerCase().contains(search);
                    case 3:
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
    private Button addBtn;

    @FXML
    void goBack(ActionEvent event) throws IOException {

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChoosePower.fxml"));
        Scene scene = new Scene(view);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML
    void AddCabin(ActionEvent event) throws IOException {

        if(App.computer==null){
            App.computer=new Computer();
        }

        App.computer.setCabin((Cabin) tableView.getSelectionModel().getSelectedItem());


        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseMouse.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();


    }
}
