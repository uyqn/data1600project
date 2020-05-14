package controllers.user.endUsers;

import components.Component;
import components.Computer;
import components.Keyboard;
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
import users.EndUser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ChooseKeyboardController implements Initializable {

    @FXML
    private TableView<Component> tableView;

    @FXML
    private TableColumn<Keyboard, String> ManufacturerColumn;

    @FXML
    private TableColumn<Keyboard, String> ModelColumn;

    @FXML
    private TableColumn<Keyboard, Double> PriceColumn;

    @FXML
    private TableColumn<Keyboard, Boolean> TactileColumn;

    @FXML
    private TreeView<String> treeView;

    @FXML
    private Label priceLabel;


    @FXML
    private ChoiceBox<String> filterBox;

    @FXML
    private TextField filterText;

    ObservableList<Component> keyboardList = App.listableList.getList().stream().filter(component ->
            component.getComponentType().equals(Keyboard.COMPONENT_TYPE)
    ).collect(Collectors.toCollection(FXCollections::observableArrayList));

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        filterBox.getItems().setAll(
                "Manufacturer",
                "Model",
                "Tactile",
                "Not tactile",
                "Price (NOK) ≤");
        filterBox.setValue(null);
        filterText.setText(null);
        //Setter opp kolonner

        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<Keyboard, String>("manufacturer"));
        ModelColumn.setCellValueFactory(new PropertyValueFactory<Keyboard, String>("model"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<Keyboard, Double>("price"));
        TactileColumn.setCellValueFactory(new PropertyValueFactory<Keyboard, Boolean>("tactile"));


        tableView.setItems(keyboardList);

        try{
            treeView=App.computer.setTreeView(treeView);
            treeView.refresh();
            priceLabel.setText("Total price: "+ App.computer.getPrice()+" NOK");
        }catch (NullPointerException ignore){}

    }

    @FXML
    private Button backBtn;

    @FXML
    private Button nextBtn;

    @FXML
    void GoBack(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUsers/ChooseMonitor.fxml"));

        Scene scene = new Scene(view);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML
    void GoNext(ActionEvent event) throws IOException {
        if (App.computer==null){
            App.computer=new Computer();
        }
        App.computer.setKeyboard((Keyboard) tableView.getSelectionModel().getSelectedItem());

        System.out.println(App.computer.toCSV());

        Parent view = FXMLLoader.load(getClass().getResource("/main/user/endUser.fxml"));
        Scene scene = new Scene(view);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

        EndUser.listableList.add(App.computer);
        App.computer=null;
    }

    @FXML
    void filterEvt(ActionEvent event) {
        int index = filterBox.getSelectionModel().getSelectedIndex();
        if (index == 2 || index == 3) {
            filterText.setDisable(true);
            tableView.setItems(keyboardList.stream().filter(component -> {
                switch (index) {
                    case 2:
                        return component.getTactile();
                    case 3:
                        return !component.getTactile();
                    default:
                        return false;
                }
            }).collect(Collectors.toCollection(FXCollections::observableArrayList)));
        } else {
            filterText.setDisable(false);

            if (filterText.getText() == null) {
                tableView.setItems(keyboardList);
            } else {
                String search = filterText.getText().toLowerCase();
                int filterIndex = filterBox.getSelectionModel().getSelectedIndex();

                tableView.setItems(keyboardList.stream().filter(component -> {
                    if (search.isBlank() || search.isEmpty() || filterBox.getSelectionModel().getSelectedItem() == null) {
                        return true;
                    } else {
                        switch (filterIndex) {
                            case 0:
                                return component.getManufacturer().toLowerCase().contains(search);
                            case 1:
                                return component.getModel().toLowerCase().contains(search);
                            case 4:
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
        }
    }
    @FXML
    void filterEvent(KeyEvent event) {
        int index = filterBox.getSelectionModel().getSelectedIndex();
        if (index == 2 || index == 3) {
            filterText.setDisable(true);
            tableView.setItems(keyboardList.stream().filter(component -> {
                switch (index) {
                    case 2:
                        return component.getTactile();
                    case 3:
                        return !component.getTactile();
                    default:
                        return false;
                }
            }).collect(Collectors.toCollection(FXCollections::observableArrayList)));
        } else {
            filterText.setDisable(false);

            if (filterText.getText() == null) {
                tableView.setItems(keyboardList);
            } else {
                String search = filterText.getText().toLowerCase();
                int filterIndex = filterBox.getSelectionModel().getSelectedIndex();

                tableView.setItems(keyboardList.stream().filter(component -> {
                    if (search.isBlank() || search.isEmpty() || filterBox.getSelectionModel().getSelectedItem() == null) {
                        return true;
                    } else {
                        switch (filterIndex) {
                            case 0:
                                return component.getManufacturer().toLowerCase().contains(search);
                            case 1:
                                return component.getModel().toLowerCase().contains(search);
                            case 4:
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
        }
    }
}
