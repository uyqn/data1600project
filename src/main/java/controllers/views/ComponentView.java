package controllers.views;

import components.*;
import components.Storage.*;
import controllers.guiManager.DialogBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import main.App;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ComponentView implements Initializable {

    private ObservableList<Component> filteredList;

    @FXML
    private TableView<Component> tableView;

    @FXML
    private ChoiceBox<String> filterBox;

    @FXML
    private TextField searchText;

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

    private BooleanStringConverter booleanStringConverter = new BooleanStringConverter();

    @FXML
    void viewAll(ActionEvent event) {
        filterBox.getItems().setAll("Manufacturer", "Model", "Price (NOK) ≤");
        filterBox.setValue(null);
        searchText.setText(null);
        filteredList = App.componentList.getList();

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

        searchText.setOnKeyReleased(keyEvent -> {
            String search = searchText.getText().toLowerCase();
            int filterIndex = filterBox.getSelectionModel().getSelectedIndex();

            tableView.setItems(filteredList.stream().filter(component -> {
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
                        default:
                            return false;
                    }
                }
            }).collect(Collectors.toCollection(FXCollections::observableArrayList)));
        });

        tableView.setItems(filteredList);
    }

    @FXML

    void viewCpu(ActionEvent event) {
        filterBox.getItems().setAll(
                "Manufacturer",
                "Model",
                "Socket",
                "Cores ≤",
                "Base clock speed (Hz) ≤",
                "Boost clock speed (Hz) ≤",
                "Power consumption (W) ≤",
                "Price (NOK) ≤");
        filterBox.setValue(null);
        searchText.setText(null);
        filteredList = App.componentList.getList().stream().filter(component ->
                component.getComponentType().equals(CPU.COMPONENT_TYPE)
        ).collect(Collectors.toCollection(FXCollections::observableArrayList));



        TableColumn<Component, String> manuCol = new TableColumn<>("Manufacturer");
        manuCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        manuCol.setCellFactory(TextFieldTableCell.forTableColumn());
        manuCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("manufacturer", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelCol.setCellFactory(TextFieldTableCell.forTableColumn());
        modelCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("model", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> socketCol = new TableColumn<>("Socket");
        socketCol.setCellValueFactory(new PropertyValueFactory<>("socket"));
        socketCol.setCellFactory(TextFieldTableCell.forTableColumn());
        socketCol.setOnEditCommit(edit -> {
            try{
                tableView.getSelectionModel().getSelectedItem().setSocket(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("socket", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setSocket(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Integer> coreCol = new TableColumn<>("Cores");
        coreCol.setCellValueFactory(new PropertyValueFactory<>("coreCount"));
        coreCol.setCellFactory(TextFieldTableCell.forTableColumn(integerStringConverter));
        coreCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setCoreCount(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("cores", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setCoreCount(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Double> baseClockCol = new TableColumn<>("Base clock speed (Hz)");
        baseClockCol.setCellValueFactory(new PropertyValueFactory<>("coreClock"));
        baseClockCol.setCellFactory(TextFieldTableCell.forTableColumn(doubleStringConverter));
        baseClockCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setCoreClock(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("base clock", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setCoreClock(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Double> boostClockCol = new TableColumn<>("Boost clock speed (Hz)");
        boostClockCol.setCellValueFactory(new PropertyValueFactory<>("boostClock"));
        boostClockCol.setCellFactory(TextFieldTableCell.forTableColumn(doubleStringConverter));
        boostClockCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setBoostClock(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("boost clock", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setBoostClock(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Double> powerCol = new TableColumn<>("Power consumption (W)");
        powerCol.setCellValueFactory(new PropertyValueFactory<>("powerConsumption"));
        powerCol.setCellFactory(TextFieldTableCell.forTableColumn(doubleStringConverter));
        powerCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setPowerConsumption(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("power consumption", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setPowerConsumption(edit.getOldValue());
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

        tableView.getColumns().setAll(
                manuCol,
                modelCol,
                socketCol,
                coreCol,
                baseClockCol,
                boostClockCol,
                powerCol,
                priceCol
        );

        searchText.setOnKeyReleased(keyEvent -> {
            String search = searchText.getText().toLowerCase();
            int filterIndex = filterBox.getSelectionModel().getSelectedIndex();

            tableView.setItems(filteredList.stream().filter(component -> {
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
        });

        tableView.setItems(filteredList);
    }

    @FXML
    void viewGpu(ActionEvent event) {
        filterBox.getItems().setAll(
                "Manufacturer",
                "Model",
                "Buss type",
                "Memory (GB) ≤",
                "Technology",
                "Boost clock (MHz) ≤",
                "Price (NOK) ≤");
        filterBox.setValue(null);
        searchText.setText(null);
        filteredList = App.componentList.getList().stream().filter(component ->
                component.getComponentType().equals(GPU.COMPONENT_TYPE)
        ).collect(Collectors.toCollection(FXCollections::observableArrayList));

        TableColumn<Component, String> manuCol = new TableColumn<>("Manufacturer");
        manuCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        manuCol.setCellFactory(TextFieldTableCell.forTableColumn());
        manuCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("manufacturer", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelCol.setCellFactory(TextFieldTableCell.forTableColumn());
        modelCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("model", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> bussCol = new TableColumn<>("Buss type");
        bussCol.setCellValueFactory(new PropertyValueFactory<>("bussType"));
        bussCol.setCellFactory(TextFieldTableCell.forTableColumn());
        bussCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setBussType(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("buss type", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setBussType(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Integer> memoryCol = new TableColumn<>("Memory (GB)");
        memoryCol.setCellValueFactory(new PropertyValueFactory<>("memory"));
        memoryCol.setCellFactory(TextFieldTableCell.forTableColumn(integerStringConverter));
        memoryCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setMemory(edit.getNewValue());
            } catch (IllegalArgumentException e) {
                inputError("memory", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setMemory(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> memoryTypeCol = new TableColumn<>("Technology");
        memoryTypeCol.setCellValueFactory(new PropertyValueFactory<>("memoryType"));
        memoryTypeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        memoryTypeCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setMemoryType(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("buss type", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setMemoryType(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Integer> boostCol = new TableColumn<>("Boost clock (MHz)");
        boostCol.setCellValueFactory(new PropertyValueFactory<>("boostSpeed"));
        boostCol.setCellFactory(TextFieldTableCell.forTableColumn(integerStringConverter));
        boostCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setBoostSpeed(edit.getNewValue());
            } catch (IllegalArgumentException e) {
                inputError("boost clock", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setBoostSpeed(edit.getOldValue());
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

        tableView.getColumns().setAll(
                manuCol,
                modelCol,
                bussCol,
                memoryCol,
                memoryTypeCol,
                boostCol,
                priceCol
        );

        searchText.setOnKeyReleased(keyEvent -> {
            String search = searchText.getText().toLowerCase();
            int filterIndex = filterBox.getSelectionModel().getSelectedIndex();

            tableView.setItems(filteredList.stream().filter(component -> {
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
                            return component.getBussType().toLowerCase().contains(search);
                        case 3:
                            try {
                                return component.getMemory() <= Integer.parseInt(search);
                            } catch (NumberFormatException e){
                                return false;
                            }
                        case 4:
                            return component.getMemoryTech().toLowerCase().contains(search);
                        case 5:
                            try {
                                return component.getBoostSpeed() <= Double.parseDouble(search);
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        case 6:
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
        });

        tableView.setItems(filteredList);
    }

    @FXML
    void viewMb(ActionEvent event) {
        filterBox.getItems().setAll(
                "Manufacturer",
                "Model",
                "Socket",
                "Buss type",
                "Ram slots ≤",
                "Technology",
                "Max memory (GB) ≤",
                "Form factor",
                "Price (NOK) ≤");
        filterBox.setValue(null);
        searchText.setText(null);
        filteredList = App.componentList.getList().stream().filter(component ->
                component.getComponentType().equals(Motherboard.COMPONENT_TYPE)
        ).collect(Collectors.toCollection(FXCollections::observableArrayList));

        TableColumn<Component, String> manuCol = new TableColumn<>("Manufacturer");
        manuCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        manuCol.setCellFactory(TextFieldTableCell.forTableColumn());
        manuCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("manufacturer", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelCol.setCellFactory(TextFieldTableCell.forTableColumn());
        modelCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("model", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> socketCol = new TableColumn<>("Socket");
        socketCol.setCellValueFactory(new PropertyValueFactory<>("socket"));
        socketCol.setCellFactory(TextFieldTableCell.forTableColumn());
        socketCol.setOnEditCommit(edit -> {
            try{
                tableView.getSelectionModel().getSelectedItem().setSocket(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("socket", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setSocket(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> bussCol = new TableColumn<>("Buss type");
        bussCol.setCellValueFactory(new PropertyValueFactory<>("bussType"));
        bussCol.setCellFactory(TextFieldTableCell.forTableColumn());
        bussCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setBussType(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("buss type", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setBussType(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Integer> ramSlotsCol = new TableColumn<>("Ram slots");
        ramSlotsCol.setCellValueFactory(new PropertyValueFactory<>("ramSlots"));
        ramSlotsCol.setCellFactory(TextFieldTableCell.forTableColumn(integerStringConverter));
        ramSlotsCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setRamSlots(edit.getNewValue());
            } catch (IllegalArgumentException e) {
                inputError("ram slots", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setRamSlots(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> techCol = new TableColumn<>("Memory Technology");
        techCol.setCellValueFactory(new PropertyValueFactory<>("memoryTech"));
        techCol.setCellFactory(TextFieldTableCell.forTableColumn());
        techCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setMemoryTech(edit.getNewValue());
            } catch (IllegalArgumentException e) {
                inputError("memory technology", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setMemoryTech(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Integer> maxRamCol = new TableColumn<>("Max RAM (GB)");
        maxRamCol.setCellValueFactory(new PropertyValueFactory<>("maxRamSize"));
        maxRamCol.setCellFactory(TextFieldTableCell.forTableColumn(integerStringConverter));
        maxRamCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setMaxRamSize(edit.getNewValue());
            } catch (IllegalArgumentException e) {
                inputError("max RAM", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setMaxRamSize(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> formFactorCol = new TableColumn<>("Form factor");
        formFactorCol.setCellValueFactory(new PropertyValueFactory<>("formFactor"));
        formFactorCol.setCellFactory(TextFieldTableCell.forTableColumn());
        formFactorCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setFormFactor(edit.getNewValue());
            } catch (IllegalArgumentException e) {
                inputError("form factor", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setFormFactor(edit.getOldValue());
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

        tableView.getColumns().setAll(
                manuCol,
                modelCol,
                socketCol,
                bussCol,
                ramSlotsCol,
                techCol,
                maxRamCol,
                formFactorCol,
                priceCol
        );

        searchText.setOnKeyReleased(keyEvent -> {
            String search = searchText.getText().toLowerCase();
            int filterIndex = filterBox.getSelectionModel().getSelectedIndex();

            tableView.setItems(filteredList.stream().filter(component -> {
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
                            return component.getBussType().toLowerCase().contains(search);
                        case 4:
                            try {
                                return component.getRamSlots() <= Integer.parseInt(search);
                            } catch (NumberFormatException e){
                                return false;
                            }
                        case 5:
                            return component.getMemoryTech().toLowerCase().contains(search);
                        case 6:
                            try {
                                return component.getMaxRamSize() <= Integer.parseInt(search);
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        case 7:
                            return component.getFormFactor().toLowerCase().contains(search);
                        case 8:
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
        });

        tableView.setItems(filteredList);
    }

    @FXML
    void viewRam(ActionEvent event) {
        filterBox.getItems().setAll(
                "Manufacturer",
                "Model",
                "Capacity ≤",
                "RPM ≤",
                "Price (NOK) ≤");
        filterBox.setValue(null);
        searchText.setText(null);
        filteredList = App.componentList.getList().stream().filter(component ->
                component.getComponentType().equals(Memory.COMPONENT_TYPE)
        ).collect(Collectors.toCollection(FXCollections::observableArrayList));

        TableColumn<Component, String> manuCol = new TableColumn<>("Manufacturer");
        manuCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        manuCol.setCellFactory(TextFieldTableCell.forTableColumn());
        manuCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("manufacturer", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelCol.setCellFactory(TextFieldTableCell.forTableColumn());
        modelCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("model", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Integer> ramCol = new TableColumn<>("RAM (GB)");
        ramCol.setCellValueFactory(new PropertyValueFactory<>("ram"));
        ramCol.setCellFactory(TextFieldTableCell.forTableColumn(integerStringConverter));
        ramCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setRam(edit.getNewValue());
            } catch (IllegalArgumentException e) {
                inputError("RAM", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setRam(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Integer> speedCol = new TableColumn<>("Speed (MHz)");
        speedCol.setCellValueFactory(new PropertyValueFactory<>("speed"));
        speedCol.setCellFactory(TextFieldTableCell.forTableColumn(integerStringConverter));
        speedCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setSpeed(edit.getNewValue());
            } catch (IllegalArgumentException e) {
                inputError("Speed", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setSpeed(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> techCol = new TableColumn<>("Memory Technology");
        techCol.setCellValueFactory(new PropertyValueFactory<>("memoryTech"));
        techCol.setCellFactory(TextFieldTableCell.forTableColumn());
        techCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setMemoryTech(edit.getNewValue());
            } catch (IllegalArgumentException e) {
                inputError("memory technology", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setMemoryTech(edit.getOldValue());
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

        tableView.getColumns().setAll(
                manuCol,
                modelCol,
                ramCol,
                speedCol,
                techCol,
                priceCol
        );

        searchText.setOnKeyReleased(keyEvent -> {
            String search = searchText.getText().toLowerCase();
            int filterIndex = filterBox.getSelectionModel().getSelectedIndex();

            tableView.setItems(filteredList.stream().filter(component -> {
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
                                return component.getRam() <= Integer.parseInt(search);
                            } catch (NumberFormatException e){
                                return false;
                            }
                        case 3:
                            try {
                                return component.getSpeed() <= Integer.parseInt(search);
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        case 4:
                            return component.getMemoryTech().toLowerCase().contains(search);
                        case 5:
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
        });

        tableView.setItems(filteredList);
    }

    @FXML
    void viewHdd(ActionEvent event) {
        filterBox.getItems().setAll(
                "Manufacturer",
                "Model",
                "Capacity ≤",
                "RPM ≤",
                "Price (NOK) ≤");
        filterBox.setValue(null);
        searchText.setText(null);
        filteredList = App.componentList.getList().stream().filter(component ->
                component.getComponentType().equals(HDD.COMPONENT_TYPE)
        ).collect(Collectors.toCollection(FXCollections::observableArrayList));

        TableColumn<Component, String> manuCol = new TableColumn<>("Manufacturer");
        manuCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        manuCol.setCellFactory(TextFieldTableCell.forTableColumn());
        manuCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("manufacturer", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelCol.setCellFactory(TextFieldTableCell.forTableColumn());
        modelCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("model", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Integer> capacityCol = new TableColumn<>("Capacity (GB)");
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        capacityCol.setCellFactory(TextFieldTableCell.forTableColumn(integerStringConverter));
        capacityCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setCapacity(edit.getNewValue());
            } catch (IllegalArgumentException e) {
                inputError("capacity", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setCapacity(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Integer> rpmCol = new TableColumn<>("RPM");
        rpmCol.setCellValueFactory(new PropertyValueFactory<>("rpm"));
        rpmCol.setCellFactory(TextFieldTableCell.forTableColumn(integerStringConverter));
        rpmCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setRpm(edit.getNewValue());
            } catch (IllegalArgumentException e) {
                inputError("capacity", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setRpm(edit.getOldValue());
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

        tableView.getColumns().setAll(
                manuCol,
                modelCol,
                capacityCol,
                rpmCol,
                priceCol
        );

        searchText.setOnKeyReleased(keyEvent -> {
            String search = searchText.getText().toLowerCase();
            int filterIndex = filterBox.getSelectionModel().getSelectedIndex();

            tableView.setItems(filteredList.stream().filter(component -> {
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
                                return component.getCapacity() <= Integer.parseInt(search);
                            } catch (NumberFormatException e){
                                return false;
                            }
                        case 3:
                            try {
                                return component.getRpm() <= Integer.parseInt(search);
                            } catch (NumberFormatException e) {
                                return false;
                            }
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
        });

        tableView.setItems(filteredList);
    }

    @FXML
    void viewSsd(ActionEvent event) {
        filterBox.getItems().setAll(
                "Manufacturer",
                "Model",
                "Capacity ≤",
                "Price (NOK) ≤");
        filterBox.setValue(null);
        searchText.setText(null);
        filteredList = App.componentList.getList().stream().filter(component ->
                component.getComponentType().equals(HDD.COMPONENT_TYPE)
        ).collect(Collectors.toCollection(FXCollections::observableArrayList));

        TableColumn<Component, String> manuCol = new TableColumn<>("Manufacturer");
        manuCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        manuCol.setCellFactory(TextFieldTableCell.forTableColumn());
        manuCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("manufacturer", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelCol.setCellFactory(TextFieldTableCell.forTableColumn());
        modelCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("model", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Integer> capacityCol = new TableColumn<>("Capacity (GB)");
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        capacityCol.setCellFactory(TextFieldTableCell.forTableColumn(integerStringConverter));
        capacityCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setCapacity(edit.getNewValue());
            } catch (IllegalArgumentException e) {
                inputError("capacity", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setCapacity(edit.getOldValue());
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

        tableView.getColumns().setAll(
                manuCol,
                modelCol,
                capacityCol,
                priceCol
        );

        searchText.setOnKeyReleased(keyEvent -> {
            String search = searchText.getText().toLowerCase();
            int filterIndex = filterBox.getSelectionModel().getSelectedIndex();

            tableView.setItems(filteredList.stream().filter(component -> {
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
                                return component.getCapacity() <= Integer.parseInt(search);
                            } catch (NumberFormatException e){
                                return false;
                            }
                        case 3:
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
        });

        tableView.setItems(filteredList);
    }

    @FXML
    void viewCooler(ActionEvent event) {
        TableColumn<Component, String> manuCol = new TableColumn<>("Manufacturer");
        manuCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        manuCol.setCellFactory(TextFieldTableCell.forTableColumn());
        manuCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("manufacturer", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelCol.setCellFactory(TextFieldTableCell.forTableColumn());
        modelCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("model", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> dimCol = new TableColumn<>("Dimension (cm)");
        dimCol.setCellValueFactory(new PropertyValueFactory<>("dimension"));
        dimCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dimCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setDimension(edit.getNewValue());
            } catch (IllegalArgumentException e) {
                inputError("dimension", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setDimension(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> rpmCol = new TableColumn<>("RPM");
        rpmCol.setCellValueFactory(new PropertyValueFactory<>("rpmString"));
        rpmCol.setCellFactory(TextFieldTableCell.forTableColumn());
        rpmCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setRpmString(edit.getNewValue());
            } catch (IllegalArgumentException e) {
                inputError("RPM", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setRpmString(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> noiseCol = new TableColumn<>("Noise (dBA)");
        noiseCol.setCellValueFactory(new PropertyValueFactory<>("noise"));
        noiseCol.setCellFactory(TextFieldTableCell.forTableColumn());
        noiseCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setNoise(edit.getNewValue());
            } catch (IllegalArgumentException e) {
                inputError("Noise", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setNoise(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Double> powerCol = new TableColumn<>("Power consumption (W)");
        powerCol.setCellValueFactory(new PropertyValueFactory<>("powerConsumption"));
        powerCol.setCellFactory(TextFieldTableCell.forTableColumn(doubleStringConverter));
        powerCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setPowerConsumption(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("power consumption", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setPowerConsumption(edit.getOldValue());
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

        tableView.setItems(
                App.componentList.getList().stream().filter(component ->
                        component.getComponentType().equals(Cooler.COMPONENT_TYPE)
                ).collect(Collectors.toCollection(FXCollections::observableArrayList))
        );

        tableView.getColumns().setAll(
                manuCol,
                modelCol,
                dimCol,
                rpmCol,
                noiseCol,
                powerCol,
                priceCol
        );
    }

    @FXML
    void viewPsu(ActionEvent event) {
        TableColumn<Component, String> manuCol = new TableColumn<>("Manufacturer");
        manuCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        manuCol.setCellFactory(TextFieldTableCell.forTableColumn());
        manuCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("manufacturer", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelCol.setCellFactory(TextFieldTableCell.forTableColumn());
        modelCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("model", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Integer> powerCol = new TableColumn<>("Power capacity (W)");
        powerCol.setCellValueFactory(new PropertyValueFactory<>("powerCapacity"));
        powerCol.setCellFactory(TextFieldTableCell.forTableColumn(integerStringConverter));
        powerCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setPowerCapacity(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("power consumption", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setPowerCapacity(edit.getOldValue());
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

        tableView.setItems(
                App.componentList.getList().stream().filter(component ->
                        component.getComponentType().equals(PSU.COMPONENT_TYPE)
                ).collect(Collectors.toCollection(FXCollections::observableArrayList))
        );

        tableView.getColumns().setAll(
                manuCol,
                modelCol,
                powerCol,
                priceCol
        );
    }

    @FXML
    void viewCabin(ActionEvent event) {
        TableColumn<Component, String> manuCol = new TableColumn<>("Manufacturer");
        manuCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        manuCol.setCellFactory(TextFieldTableCell.forTableColumn());
        manuCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("manufacturer", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelCol.setCellFactory(TextFieldTableCell.forTableColumn());
        modelCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("model", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> formFactorCol = new TableColumn<>("Form factor");
        formFactorCol.setCellValueFactory(new PropertyValueFactory<>("formFactor"));
        formFactorCol.setCellFactory(TextFieldTableCell.forTableColumn());
        formFactorCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setFormFactor(edit.getNewValue());
            } catch (IllegalArgumentException e) {
                inputError("form factor", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setFormFactor(edit.getOldValue());
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

        tableView.setItems(
                App.componentList.getList().stream().filter(component ->
                        component.getComponentType().equals(Cabin.COMPONENT_TYPE)
                ).collect(Collectors.toCollection(FXCollections::observableArrayList))
        );

        tableView.getColumns().setAll(
                manuCol,
                modelCol,
                formFactorCol,
                priceCol
        );
    }

    @FXML
    void viewMouse(ActionEvent event) {
        TableColumn<Component, String> manuCol = new TableColumn<>("Manufacturer");
        manuCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        manuCol.setCellFactory(TextFieldTableCell.forTableColumn());
        manuCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("manufacturer", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelCol.setCellFactory(TextFieldTableCell.forTableColumn());
        modelCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("model", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Integer> numButtCol = new TableColumn<>("Buttons");
        numButtCol.setCellValueFactory(new PropertyValueFactory<>("numberButtons"));
        numButtCol.setCellFactory(TextFieldTableCell.forTableColumn(integerStringConverter));
        numButtCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setNumberButtons(edit.getNewValue());
            } catch (IllegalArgumentException e) {
                inputError("buttons", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setNumberButtons(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Integer> dpiCol = new TableColumn<>("Polling rate (dpi)");
        dpiCol.setCellValueFactory(new PropertyValueFactory<>("dpi"));
        dpiCol.setCellFactory(TextFieldTableCell.forTableColumn(integerStringConverter));
        dpiCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setDpi(edit.getNewValue());
            } catch (IllegalArgumentException e) {
                inputError("dpi", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setDpi(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Boolean> ergonomicCol = new TableColumn<>("Eronomic");
        ergonomicCol.setCellValueFactory(new PropertyValueFactory<>("ergonomic"));
        ergonomicCol.setCellFactory(TextFieldTableCell.forTableColumn(booleanStringConverter));
        ergonomicCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setErgonomic(edit.getNewValue());
            } catch (IllegalArgumentException e) {
                inputError("ergonomic", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setErgonomic(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Boolean> wirelessCol = new TableColumn<>("Wireless");
        wirelessCol.setCellValueFactory(new PropertyValueFactory<>("wireless"));
        wirelessCol.setCellFactory(TextFieldTableCell.forTableColumn(booleanStringConverter));
        wirelessCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setWireless(edit.getNewValue());
            } catch (IllegalArgumentException e) {
                inputError("wireless", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setWireless(edit.getOldValue());
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

        tableView.setItems(
                App.componentList.getList().stream().filter(component ->
                        component.getComponentType().equals(Mouse.COMPONENT_TYPE)
                ).collect(Collectors.toCollection(FXCollections::observableArrayList))
        );

        tableView.getColumns().setAll(
                manuCol,
                modelCol,
                numButtCol,
                dpiCol,
                ergonomicCol,
                wirelessCol,
                priceCol
        );
    }

    @FXML
    void viewMonitor(ActionEvent event){
        TableColumn<Component, String> manuCol = new TableColumn<>("Manufacturer");
        manuCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        manuCol.setCellFactory(TextFieldTableCell.forTableColumn());
        manuCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("manufacturer", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelCol.setCellFactory(TextFieldTableCell.forTableColumn());
        modelCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("model", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Double> sizeCol = new TableColumn<>("Display size (inches)");
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        sizeCol.setCellFactory(TextFieldTableCell.forTableColumn(doubleStringConverter));
        sizeCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setSize(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("display size", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setSize(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Integer> refreshCol = new TableColumn<>("Refresh Rate (Hz)");
        refreshCol.setCellValueFactory(new PropertyValueFactory<>("refreshRate"));
        refreshCol.setCellFactory(TextFieldTableCell.forTableColumn(integerStringConverter));
        refreshCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setRefreshRate(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("refresh rate", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setRefreshRate(edit.getOldValue());
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

        tableView.setItems(
                App.componentList.getList().stream().filter(component ->
                        component.getComponentType().equals(Monitor.COMPONENT_TYPE)
                ).collect(Collectors.toCollection(FXCollections::observableArrayList))
        );

        tableView.getColumns().setAll(
                manuCol,
                modelCol,
                sizeCol,
                refreshCol,
                priceCol
        );
    }

    @FXML
    void viewKeyboard(ActionEvent event) {
        TableColumn<Component, String> manuCol = new TableColumn<>("Manufacturer");
        manuCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        manuCol.setCellFactory(TextFieldTableCell.forTableColumn());
        manuCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("manufacturer", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setManufacturer(edit.getNewValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelCol.setCellFactory(TextFieldTableCell.forTableColumn());
        modelCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getNewValue());
            } catch (IllegalArgumentException e){
                inputError("model", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setModel(edit.getOldValue());
            }
            tableView.refresh();
        });

        TableColumn<Component, Boolean> tactileCol = new TableColumn<>("Tactile");
        tactileCol.setCellValueFactory(new PropertyValueFactory<>("tactile"));
        tactileCol.setCellFactory(TextFieldTableCell.forTableColumn(booleanStringConverter));
        tactileCol.setOnEditCommit(edit -> {
            try {
                tableView.getSelectionModel().getSelectedItem().setTactile(edit.getNewValue());
            } catch (IllegalArgumentException e) {
                inputError("tactile", e.getMessage());
                tableView.getSelectionModel().getSelectedItem().setTactile(edit.getOldValue());
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

        tableView.setItems(
                App.componentList.getList().stream().filter(component ->
                        component.getComponentType().equals(Keyboard.COMPONENT_TYPE)
                ).collect(Collectors.toCollection(FXCollections::observableArrayList))
        );

        tableView.getColumns().setAll(
                manuCol,
                modelCol,
                tactileCol,
                priceCol
        );
    }

    private void inputError(String inputField, String error) {
        DialogBox.error("Input error!",
                "could not update " + inputField,
                error);
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filterBox.getItems().setAll("Manufacturer", "Model", "Price (NOK) ≤");
        filterBox.setValue(null);
        filteredList = App.componentList.getList();

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

        searchText.setOnKeyReleased(keyEvent -> {
            String search = searchText.getText().toLowerCase();
            int filterIndex = filterBox.getSelectionModel().getSelectedIndex();

            tableView.setItems(filteredList.stream().filter(component -> {
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
                        default:
                            return false;
                    }
                }
            }).collect(Collectors.toCollection(FXCollections::observableArrayList)));
        });

        tableView.setItems(filteredList);
        tableView.setEditable(true);
    }
}
