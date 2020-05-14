package controllers.component;

import components.*;
import components.Storage.HDD;
import components.Storage.SSD;
import components.Storage.Storage;
import controllers.guiManager.DialogBox;
import controllers.guiManager.Limit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.App;

import java.net.URL;
import java.util.ResourceBundle;

public class ComponentController implements Initializable {

    @FXML
    private BorderPane gui;

    @FXML
    private GridPane superHome;

    @FXML
    private GridPane
            addCpuGui,
            addMbGui,
            addCoolerGui,
            addGraphicCardGui,
            addMemoryGui,
            addStorageGui,
            addMonitorGui,
            addMouseGui,
            addKeyboardGui,
            addCabinGui,
            addPowerSupplyGui;

    @FXML
    private ChoiceBox<String> ramTech, speedTech;

    @FXML
    private TextField txtRpm;


    //Increase and decrease cores of Cpu buttons
    @FXML
    void increaseCore(ActionEvent event){
        int[] validCoreCount = {1, 2, 3, 4, 6, 8, 10, 12, 14, 16,
                18, 20, 22, 24, 26, 28, 30, 32, 64};
        increase(validCoreCount, addCpuGui, "core");
    }
    @FXML
    void decreaseCore(ActionEvent event){
        int[] validCoreCount = {1, 2, 3, 4, 6, 8, 10, 12, 14, 16,
                18, 20, 22, 24, 26, 28, 30, 32, 64};

        decrease(validCoreCount, addCpuGui, "core");
    }
    @FXML
    void increaseRamSlots(ActionEvent event){
        int[] validRamSlots = {1, 2, 4, 6, 8, 12, 16};
        increase(validRamSlots, addMbGui, "numSlots");
    }
    @FXML
    void decreaseRamSlots(ActionEvent event){
        int[] validRamSlots = {1, 2, 4, 6, 8, 12, 16};
        decrease(validRamSlots, addMbGui, "numSlots");
    }


    //Add Components
    @FXML
    void addMb(ActionEvent event) {

        try {
            if (validate(addMbGui, "manufacturer", "model", "socket", "bussType", "numSlots", "maxRamSize", "formFactor", "price")) {

                String manufacturer = getString(addMbGui, "manufacturer");
            String model = getString(addMbGui, "model");
            String socket = getString(addMbGui, "socket");
            String bussType = getString(addMbGui, "bussType");
            int availableRamSlots = getInt(addMbGui, "numSlots");
            String memoryTech = ramTech.getSelectionModel().getSelectedItem();
            int maxRamSize = getInt(addMbGui, "maxRamSize");
            String formFactor = getString(addMbGui, "formFactor");
            double price = getDouble(addMbGui, "price");

            Motherboard mb = new Motherboard(manufacturer, model, socket, bussType, availableRamSlots,
                    memoryTech, maxRamSize, formFactor, price);

            DialogBox.info("Motherboard successfully added",
                    "The following motherboard was added:",
                    mb.toString());

            App.listableList.add(mb);

            resetGui(addMbGui,
                    "manufacturer",
                    "model",
                    "socket",
                    "bussType",
                    "numSlots",
                    "maxRamSize",
                    "formFactor",
                    "price");

            ramTech.setValue(null);
        }
        } catch (IllegalArgumentException e){
            DialogBox.error(e.getClass().toString(), null,
                    e.getMessage());
        }
    }

    @FXML
    void addCabin(ActionEvent event){
        try {
            if (validate(addCabinGui, "manufacturer", "model", "formFactor", "price")) {

                String manufacturer = getString(addCabinGui, "manufacturer");
            String model = getString(addCabinGui, "model");
            String formFactor = getString(addCabinGui, "formFactor");
            double price = getDouble(addCabinGui, "price");

            Cabin cabin = new Cabin(manufacturer, model, formFactor, price);

            App.listableList.add(cabin);

            DialogBox.info("Cabin successfully added",
                    "The following cabin was added:",
                    cabin.toString());

            resetGui(addCabinGui, "manufacturer", "model", "formFactor", "price");
        }
        } catch (IllegalArgumentException e){
            DialogBox.error(e.getClass().toString(), null,
                    e.getMessage());
        }
    }

    @FXML
    void addCPU(ActionEvent event) {
        try {
            if (validate(addCpuGui, "manufacturer", "model", "socket","core","coreClock", "boostClock","power", "price")) {

                String manufacturer = getString(addCpuGui, "manufacturer");
            String model = getString(addCpuGui, "model");
            String socket = getString(addCpuGui, "socket");
            int coreCount = getInt(addCpuGui, "core");
            String clockSpeed = getString(addCpuGui, "coreClock") + "/" + getString(addCpuGui, "boostClock");
            double powerConsumption = getDouble(addCpuGui, "power");
            double price = getDouble(addCpuGui, "price");

            CPU cpu = new CPU(manufacturer, model, socket, coreCount, clockSpeed, powerConsumption, price);

            DialogBox.info("CPU successfully added",
                    "The following CPU was added:",
                    cpu.toString());

            App.listableList.add(cpu);

            resetGui(addCpuGui,
                    "manufacturer",
                    "model",
                    "socket",
                    "core",
                    "coreClock",
                    "boostClock",
                    "power",
                    "price");
        }
        } catch (IllegalArgumentException e){
            DialogBox.error(e.getClass().toString(), null,
                    e.getMessage());
        }
    }

    @FXML
    void addCooler(ActionEvent event) {
        try {
            if (validate(addCoolerGui, "manufacturer", "model", "baseRpm","maxRpm","baseNoise", "maxNoise", "power", "price")) {

                String manufacturer = getString(addCoolerGui, "manufacturer");
            String model = getString(addCoolerGui, "model");
            String rpm = getString(addCoolerGui, "baseRpm") + " - " + getString(addCoolerGui, "maxRpm");
            String noise = getString(addCoolerGui, "baseNoise") + " - " + getString(addCoolerGui, "maxNoise");
            double power = getDouble(addCoolerGui, "power");
            double price = getDouble(addCoolerGui, "price");

            Cooler cooler = new Cooler(manufacturer, model, rpm, noise, power, price);

            DialogBox.info("Cooler successfully added",
                    "The following cooler was added:",
                    cooler.toString());

            App.listableList.add(cooler);

            resetGui(addCoolerGui,
                    "manufacturer",
                    "model",
                    "baseRpm",
                    "maxRpm",
                    "baseNoise",
                    "maxNoise",
                    "power",
                    "price");

        }
        } catch (IllegalArgumentException e) {
            DialogBox.error(e.getClass().toString(), null,
                    e.getMessage());
        }
    }

    @FXML
    void addGraphicCard(ActionEvent event) {

        try {
            if (validate(addGraphicCardGui, "manufacturer", "model", "bussType","memory", "memoryType", "boostClock", "price")) {

                String manufacturer = getString(addGraphicCardGui, "manufacturer");
            String model = getString(addGraphicCardGui, "model");
            String bussType = "PCI-Express " + getDouble(addGraphicCardGui, "bussType") + "x16";
            int memory = getInt(addGraphicCardGui, "memory");
            String memoryType = getString(addGraphicCardGui, "memoryType");
            int boostClock = getInt(addGraphicCardGui, "boostClock");
            double price = getDouble(addGraphicCardGui, "price");


            GPU GPU = new GPU(manufacturer, model, bussType, memory, memoryType, boostClock, price);

            DialogBox.info("GPU successfully added",
                    "The following gpu was added:",
                    GPU.toString());

            App.listableList.add(GPU);

            resetGui(addGraphicCardGui,
                    "manufacturer",
                    "model",
                    "bussType",
                    "memory",
                    "memoryType",
                    "boostClock",
                    "price"
            );

        }
        }catch (IllegalArgumentException e){
            DialogBox.error(e.getClass().toString(), null,
                    e.getMessage());
        }


    }

    @FXML
    void addMemory(ActionEvent event) {

        try {
            if (validate(addMemoryGui, "manufacturer", "model", "RAM","speed", "price")) {

                String manufacturer = getString(addMemoryGui, "manufacturer");
            String model = getString(addMemoryGui, "model");
            int RAM = getInt(addMemoryGui, "RAM");
            String speedTech = this.speedTech.getSelectionModel().getSelectedItem();
            int speed = getInt(addMemoryGui, "speed");
            double price = getDouble(addMemoryGui, "price");


            Memory memory = new Memory(manufacturer, model, price, RAM, speedTech, speed);

            DialogBox.info("RAM successfully added",
                    "The following RAM was added:",
                    memory.toString());

            App.listableList.add(memory);

            resetGui(addMemoryGui, "manufacturer", "model", "RAM", "speed", "price");
            this.speedTech.setValue(null);
        }
        }catch (IllegalArgumentException e) {
            DialogBox.error(e.getClass().toString(), null,
                    e.getMessage());

        }


    }

    @FXML
    void ssdBtn(ActionEvent event){
        txtRpm.setDisable(true);
    }

    @FXML
    void hddBtn(ActionEvent event){
        txtRpm.setDisable(false);
    }

    @FXML
    void addStorage(ActionEvent event){

        if (txtRpm.isDisabled()){
            if (validate(addStorageGui, "manufacturer", "model", "capacity", "price")) {

                try {

                    String manufacturer = getString(addStorageGui, "manufacturer");
                    String model = getString(addStorageGui, "model");
                    int capacity = getInt(addStorageGui, "capacity");
                    double price = getDouble(addStorageGui, "price");

                    Storage ssd = new SSD(manufacturer, model, capacity, price);

                    DialogBox.info("SSD successfully added",
                            "The following SSD was added:",
                            ssd.toString());

                    App.listableList.add(ssd);

                    resetGui(addStorageGui, "manufacturer", "model", "capacity", "rpm", "price");
                } catch (IllegalArgumentException e) {
                    DialogBox.error(e.getClass().toString(), null,
                            e.getMessage());
                }
            }
        }else {
            try {
                if (validate(addStorageGui, "manufacturer", "model", "capacity", "rpm", "price")) {

                String manufacturer = getString(addStorageGui, "manufacturer");
                String model = getString(addStorageGui, "model");
                int capacity = getInt(addStorageGui, "capacity");
                int rpm = getInt(addStorageGui, "rpm");
                double price = getDouble(addStorageGui, "price");

                Storage hdd = new HDD(manufacturer, model, capacity, rpm, price);

                DialogBox.info("HDD successfully added",
                        "The following Harddrive was added:",
                        hdd.toString());

                App.listableList.add(hdd);

                resetGui(addStorageGui, "manufacturer", "model", "capacity", "rpm", "price");
            }
            }catch (IllegalArgumentException e){
                DialogBox.error(e.getClass().toString(), null,
                        e.getMessage());
            }
        }

    }

    @FXML
    void addMonitor(ActionEvent event) {

        try {
            if (validate(addMonitorGui, "manufacturer", "model", "displaySize", "refreshRate", "price")) {

                String manufacturer = getString(addMonitorGui, "manufacturer");
                String model = getString(addMonitorGui, "model");
                double size = getDouble(addMonitorGui, "displaySize");
                int refreshRate = getInt(addMonitorGui, "refreshRate");
                double price = getDouble(addMonitorGui, "price");

                Monitor monitor = new Monitor(manufacturer, model, size, refreshRate, price);

                DialogBox.info("Monitor successfully added",
                        "The following monitor was added:",
                        monitor.toString());

                App.listableList.add(monitor);

                resetGui(addMonitorGui,
                        "manufacturer",
                        "model",
                        "displaySize",
                        "refreshRate",
                        "price");
            }
        } catch (IllegalArgumentException e){
            DialogBox.error(e.getClass().toString(), null,
                    e.getMessage());
        }

    }

    @FXML
    void addMouse(ActionEvent event) {

        try {
            if (validate(addMouseGui, "manufacturer", "model", "numButtons", "dpi", "price")) {

                String manufacturer = getString(addMouseGui, "manufacturer");
                String model = getString(addMouseGui, "model");
                int numButtons = getInt(addMouseGui, "numButtons");
                int dpi = getInt(addMouseGui, "dpi");
                boolean ergonomic = ((RadioButton) addMouseGui.lookup("#ergonomicYes")).isSelected();
                boolean wireless = ((RadioButton) addMouseGui.lookup("#wirelessYes")).isSelected();
                double price = getDouble(addMouseGui, "price");

                Mouse mouse = new Mouse(manufacturer, model, numButtons, dpi, ergonomic, wireless, price);

                DialogBox.info("Mouse was successfully added",
                        "The following mouse was added:",
                        mouse.toString());

                App.listableList.add(mouse);

                resetGui(addMouseGui,
                        "manufacturer",
                        "model",
                        "numButtons",
                        "dpi",
                        "price");

                ((RadioButton) addMouseGui.lookup("#ergonomicYes")).setSelected(true);
                ((RadioButton) addMouseGui.lookup("#wirelessNo")).setSelected(true);
            }
        }catch (IllegalArgumentException e){
            DialogBox.error(e.getClass().toString(), null,
                    e.getMessage());
        }


    }

    @FXML
    void addPowerSupply(ActionEvent event) {
            try {
                if (validate(addPowerSupplyGui, "manufacturer", "model", "powerCapacity", "price")) {

                    String manufacturer = getString(addPowerSupplyGui, "manufacturer");
                    String model = getString(addPowerSupplyGui, "model");
                    int powerCapacity = getInt(addPowerSupplyGui, "powerCapacity");
                    double price = getDouble(addPowerSupplyGui, "price");

                    PSU PSU = new PSU(manufacturer, model, powerCapacity, price);

                    DialogBox.info("PSU successfully added",
                            "The following PSU was added:",
                            PSU.toString());

                    App.listableList.add(PSU);

                    resetGui(addPowerSupplyGui,
                            "manufacturer",
                            "model",
                            "powerCapacity",
                            "price");
                }
            } catch (IllegalArgumentException e) {
                DialogBox.error(e.getClass().toString(), null,
                        e.getMessage());
            }

    }

    @FXML
    void addKeyboard(ActionEvent event){

        try {
            if (validate(addKeyboardGui, "manufacturer", "model", "price")) {

                String manufacturer = getString(addKeyboardGui, "manufacturer");
                String model = getString(addKeyboardGui, "model");
                boolean tactile = ((RadioButton) addKeyboardGui.lookup("#tactileYes")).isSelected();
                double price = getDouble(addKeyboardGui, "price");

                Keyboard keyboard = new Keyboard(manufacturer, model, tactile, price);

                DialogBox.info("Keyboard successfully added",
                        "The following keyboard was added:",
                        keyboard.toString());

                App.listableList.add(keyboard);

                resetGui(addKeyboardGui, "manufacturer", "model", "price");

                ((RadioButton) addKeyboardGui.lookup("#tactileYes")).setSelected(true);
            }
        }catch (IllegalArgumentException e){
            DialogBox.error(e.getClass().toString(), null,
                    e.getMessage());
        }

    }

    public void setSuperHome(GridPane superHome) {
        this.superHome = superHome;
    }

    private void increase(int[] validNumbers,
                           GridPane gridPane,
                           String id){
        TextField str = (TextField) gridPane.lookup("#" + id);
        if(str.getText().isBlank()|| str.getText().isEmpty()){
            str.setText(String.valueOf(validNumbers[0]));
        }
        else {
            int index = getIndexOf(validNumbers, Integer.parseInt(str.getText()));
            str.setText(String.valueOf(validNumbers[++index % validNumbers.length]));
        }
    }

    private void decrease(int[] validNumbers,
                          GridPane gridPane,
                          String id){
        TextField str = (TextField) gridPane.lookup("#" + id);
        if(str.getText().isBlank()|| str.getText().isEmpty()){
            str.setText(String.valueOf(validNumbers[0]));
        }
        else {
            try {
                int index = getIndexOf(validNumbers, Integer.parseInt(str.getText()));
                str.setText(String.valueOf(validNumbers[--index % validNumbers.length]));
            } catch (IndexOutOfBoundsException e){
                str.setText(String.valueOf(validNumbers[validNumbers.length - 1]));
            }
        }
    }

    private int getIndexOf(int[] array, int number){
        for(int i = 0 ; i < array.length ; i++){
            if(array[i] == number){
                return i;
            }
        }
        return array.length;
    }

    private String getString(Pane pane, String id){
        return ((TextField) pane.lookup("#" + id)).getText();
    }
    private int getInt(Pane pane, String id){
        try {
            return Integer.parseInt(((TextField) pane.lookup("#" + id)).getText());
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Could not convert this number: " + ((TextField) pane.lookup("#" + id)).getText());
        }
    }
    private double getDouble(Pane pane, String id){
        try {
            return Double.parseDouble(((TextField) pane.lookup("#" + id)).getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Could not convert this number: " + ((TextField) pane.lookup("#" + id)).getText());
        }
    }
    private void resetGui(Pane pane, String... id){
        for(String str : id){
            ((TextField) pane.lookup("#" + str)).setText("");
        }
    }

    private boolean validate(Pane pane, String... id){

        for (String str : id){
            if (((TextField) pane.lookup("#" + str )).getText().isEmpty()){


                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("All the fields must be filled");
                alert.showAndWait();
                return false;

            }
        }
        return true;
    }

    @FXML
    void close(ActionEvent event) {
        superHome.lookup("#addComponent").setDisable(false);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    public void disableGui(boolean disable){
        this.gui.setDisable(disable);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Limit.text2double(addMbGui, "price");
        Limit.text2int(addMbGui, "numSlots", "maxRamSize");

        Limit.text2int(addCpuGui, "core");
        Limit.text2double(addCpuGui,"coreClock", "boostClock", "power", "price");

        Limit.text2int(addGraphicCardGui, "memory");
        Limit.text2double(addGraphicCardGui, "price", "boostClock", "bussType");

        Limit.text2int(addMemoryGui, "RAM", "speed");
        Limit.text2double(addMemoryGui, "price");

        Limit.text2int(addStorageGui, "rpm", "capacity");
        Limit.text2double(addStorageGui, "price");

        Limit.text2int(addCoolerGui, "baseRpm", "maxRpm");
        Limit.text2double(addCoolerGui, "baseNoise", "maxNoise", "power", "price");

        Limit.text2double(addPowerSupplyGui, "price");
        Limit.text2int(addPowerSupplyGui, "powerCapacity");

        Limit.text2double(addCabinGui, "price");
        Limit.text2int(addMouseGui, "numButtons", "dpi");

        Limit.text2double(addMouseGui, "price");
        Limit.text2int(addMonitorGui, "refreshRate");

        Limit.text2double(addMonitorGui, "price", "displaySize");
        Limit.text2double(addKeyboardGui, "price");

        ramTech.getItems().addAll("DDR", "DDR2", "DDR3", "DDR4");
        ramTech.setValue(null);
        speedTech.getItems().addAll("DDR", "DDR2", "DDR3", "DDR4");
        speedTech.setValue(null);
    }
}

