package controllers.views;

import components.CPU;
import components.Component;
import controllers.guiManager.DialogBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import main.App;

import java.net.URL;
import java.util.ResourceBundle;

public class ComponentView<T> implements Initializable {

    private TableView<Component> componentView = new TableView<>();
    private TableView<CPU> cpuView = new TableView<>();

    private IntegerStringConverter integerStringConverter = new IntegerStringConverter() {
        public Integer fromString(String s) {
            try {
                return super.fromString(s);
            }catch (NumberFormatException e){
                DialogBox.error("Could not format input!",
                        null,
                        "This field requires an integer");
                return null;
            }
        }
    };

    private DoubleStringConverter doubleStringConverter = new DoubleStringConverter(){
        @Override
        public Double fromString(String s) {
            try {
                return super.fromString(s);
            }catch (NumberFormatException e){
                DialogBox.error("Could not format input!",
                        null,
                        "This field requires a real number");
                return null;
            }
        }
    };

        @FXML
    private GridPane viewComponentsGui;

    private void getComponentView(){
        TableColumn<Component, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("componentType"));
        componentView.getColumns().add(typeCol);

        TableColumn<Component, String> manufacturerCol = new TableColumn<>("Manufacturer");
        manufacturerCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        manufacturerCol.setCellFactory(TextFieldTableCell.forTableColumn());
        manufacturerCol.setOnEditCommit(edit -> {
            Component component = componentView.getSelectionModel().getSelectedItem();
            try{
                component.setManufacturer(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("manufacturer", e.getMessage());
                component.setManufacturer(edit.getOldValue());
            }
            componentView.refresh();
        });
        componentView.getColumns().add(manufacturerCol);

        TableColumn<Component, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelCol.setCellFactory(TextFieldTableCell.forTableColumn());
        modelCol.setOnEditCommit(edit -> {
            Component component = componentView.getSelectionModel().getSelectedItem();
            try{
                component.setModel(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("model", e.getMessage());
                component.setModel(edit.getOldValue());
            }
            componentView.refresh();
        });
        componentView.getColumns().add(modelCol);

        TableColumn<Component, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(doubleStringConverter));
        priceCol.setOnEditCommit(edit -> {
            Component component = componentView.getSelectionModel().getSelectedItem();
            try{
                component.setPrice(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("price", e.getMessage());
                component.setPrice(edit.getOldValue());
            }
            componentView.refresh();
        });
        componentView.getColumns().add(priceCol);

        TableColumn<Component, String> testCol = new TableColumn<>("Test");
        testCol.setCellValueFactory(new PropertyValueFactory<>("test"));
        componentView.getColumns().add(testCol);

        componentView.setEditable(true);
    }

    private void getCpuView(){
        TableColumn<CPU, String> manufacturerCol = new TableColumn<>("Manufacturer");
        manufacturerCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        manufacturerCol.setCellFactory(TextFieldTableCell.forTableColumn());
        manufacturerCol.setOnEditCommit(edit -> {
            CPU cpu = cpuView.getSelectionModel().getSelectedItem();
            try{
                cpu.setManufacturer(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("manufacturer", e.getMessage());
                cpu.setManufacturer(edit.getOldValue());
            }
            cpuView.refresh();
        });
        cpuView.getColumns().add(manufacturerCol);

        TableColumn<CPU, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelCol.setCellFactory(TextFieldTableCell.forTableColumn());
        modelCol.setOnEditCommit(edit -> {
            CPU cpu = cpuView.getSelectionModel().getSelectedItem();
            try{
                cpu.setModel(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("model", e.getMessage());
                cpu.setModel(edit.getOldValue());
            }
            cpuView.refresh();
        });
        cpuView.getColumns().add(modelCol);

        TableColumn<CPU, String> socketCol = new TableColumn<>("Socket");
        socketCol.setCellValueFactory(new PropertyValueFactory<>("socket"));
        socketCol.setCellFactory(TextFieldTableCell.forTableColumn());
        socketCol.setOnEditCommit(edit -> {
            CPU cpu = cpuView.getSelectionModel().getSelectedItem();
            try{
                cpu.setModel(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("socket", e.getMessage());
                cpu.setModel(edit.getOldValue());
            }
            cpuView.refresh();
        });
        cpuView.getColumns().add(socketCol);

        TableColumn<CPU, Integer> coreCol = new TableColumn<>("Core count");
        coreCol.setCellValueFactory(new PropertyValueFactory<>("coreCount"));
        coreCol.setCellFactory(TextFieldTableCell.forTableColumn(integerStringConverter));
        coreCol.setOnEditCommit(edit -> {
            CPU cpu = cpuView.getSelectionModel().getSelectedItem();
            try{
                cpu.setCoreCount(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("core count", e.getMessage());
                cpu.setCoreCount(edit.getOldValue());
            }
            cpuView.refresh();
        });
        cpuView.getColumns().add(coreCol);

        TableColumn<CPU, Double> baseClock = new TableColumn<>("Base clock (gHz)");
        baseClock.setCellValueFactory(new PropertyValueFactory<>("coreClock"));
        baseClock.setCellFactory(TextFieldTableCell.forTableColumn(doubleStringConverter));
        baseClock.setOnEditCommit(edit -> {
            CPU cpu = cpuView.getSelectionModel().getSelectedItem();
            try{
                cpu.setCoreClock(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("base clock", e.getMessage());
                cpu.setCoreClock(edit.getOldValue());
            }
            cpuView.refresh();
        });
        cpuView.getColumns().add(baseClock);

        TableColumn<CPU, Double> boostClock = new TableColumn<>("Boost clock (gHz)");
        boostClock.setCellValueFactory(new PropertyValueFactory<>("boostClock"));
        boostClock.setCellFactory(TextFieldTableCell.forTableColumn(doubleStringConverter));
        boostClock.setOnEditCommit(edit -> {
            CPU cpu = cpuView.getSelectionModel().getSelectedItem();
            try{
                cpu.setBoostClock(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("boost clock", e.getMessage());
                cpu.setBoostClock(edit.getOldValue());
            }
            cpuView.refresh();
        });
        cpuView.getColumns().add(boostClock);

        TableColumn<CPU, Double> powerCol = new TableColumn<>("Power consumption (W)");
        powerCol.setCellValueFactory(new PropertyValueFactory<>("powerConsumption"));
        powerCol.setCellFactory(TextFieldTableCell.forTableColumn(doubleStringConverter));
        powerCol.setOnEditCommit(edit -> {
            CPU cpu = cpuView.getSelectionModel().getSelectedItem();
            try {
                cpu.setPowerConsumption(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("power consumption", e.getMessage());
                cpu.setPowerConsumption(edit.getOldValue());
            }
            cpuView.refresh();
        });
        cpuView.getColumns().add(powerCol);

        TableColumn<CPU, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(doubleStringConverter));
        priceCol.setOnEditCommit(edit -> {
            try {
                CPU cpu = cpuView.getSelectionModel().getSelectedItem();
            } catch (IllegalArgumentException e){
                inputError("price", e.getMessage());
            }
        });
        cpuView.getColumns().add(priceCol);
    }

    private void inputError(String inputField, String error){
        DialogBox.error("Input error!",
                "could not update " + inputField,
                error);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getComponentView();
        getCpuView();

        App.componentList.setTableView(componentView);
        viewComponentsGui.add(componentView,0,2);
    }
}
