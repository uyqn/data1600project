package controllers.views;

import components.Component;
import controllers.guiManager.DialogBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import main.App;

import java.net.URL;
import java.util.ResourceBundle;

public class ComponentView implements Initializable {

    @FXML
    private TableView<Component> tableView;

    private IntegerStringConverter integerStringConverter = new IntegerStringConverter() {
        public Integer fromString(String s) {
            try {
                return super.fromString(s);
            } catch (NumberFormatException e) {
                DialogBox.error("Could not format input!",
                        null,
                        "This field requires an integer");
                return null;
            }
        }
    };

    private DoubleStringConverter doubleStringConverter = new DoubleStringConverter() {
        @Override
        public Double fromString(String s) {
            try {
                return super.fromString(s);
            } catch (NumberFormatException e) {
                DialogBox.error("Could not format input!",
                        null,
                        "This field requires a real number");
                return null;
            }
        }
    };

    @FXML
    void viewCpu(ActionEvent event) {

    }



    private void inputError(String inputField, String error) {
        DialogBox.error("Input error!",
                "could not update " + inputField,
                error);
    };


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<Component, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("componentType"));

        TableColumn<Component, String> manuCol = new TableColumn<>("Manufacturer");
        manuCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        manuCol.setCellFactory(TextFieldTableCell.forTableColumn());
        manuCol.setOnEditCommit(edit -> {
            try{
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("manufacturer", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelCol.setCellFactory(TextFieldTableCell.forTableColumn());
        modelCol.setOnEditCommit(edit -> {
            try{
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("model", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Double> priceCol = new TableColumn<>("Price (NOK)");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(doubleStringConverter));
        priceCol.setOnEditCommit(edit -> {
            try{
                tableView.getSelectionModel().getSelectedItem().setPrice(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("price", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setPrice(edit.getOldValue());
            }
            tableView.refresh();
        });

        tableView.getColumns().setAll(typeCol, manuCol, modelCol, priceCol);

        tableView.setItems(App.componentList.getList());
        tableView.setEditable(true);
    }
}
