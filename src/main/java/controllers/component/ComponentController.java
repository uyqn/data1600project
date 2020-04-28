package controllers.component;

import components.CPU;
import components.Cooler;
import components.GraphicCard;
import components.Memory;
import controllers.guiManager.Limit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class ComponentController implements Initializable {

    @FXML
    private GridPane superHome;

    @FXML
    private GridPane addCpuGui, addCoolerGui, addGraphicCardGui, addMemoryGui;

    @FXML
    void increaseCore(ActionEvent event){
        int[] validCoreCount = {1, 2, 3, 4, 6, 8, 10, 12, 14, 16,
                18, 20, 22, 24, 26, 28, 30, 32, 64};

        TextField str = (TextField) addCpuGui.lookup("#core");
        if(str.getText().isBlank()|| str.getText().isEmpty()){
            str.setText("1");
        }
        else {
            try {
                int index = getIndexOf(validCoreCount, Integer.parseInt(str.getText()));
                str.setText(validCoreCount[++index] + "");
            }catch (IndexOutOfBoundsException e){
                str.setText("64");
            }
        }
    }
    @FXML
    void decreaseCore(ActionEvent event){
        int[] validCoreCount = {1, 2, 3, 4, 6, 8, 10, 12, 14, 16,
                18, 20, 22, 24, 26, 28, 30, 32, 64};

        TextField str = (TextField) addCpuGui.lookup("#core");
        if(str.getText().isBlank()|| str.getText().isEmpty()){
            str.setText("1");
        }
        else {
            try {
                int index = getIndexOf(validCoreCount, Integer.parseInt(str.getText()));
                str.setText(validCoreCount[--index] + "");
            }catch (IndexOutOfBoundsException e){
                str.setText("1");
            }
        }
    }

    @FXML
    void addCPU(ActionEvent event) {
        try {
            String manufacturer = getString(addCpuGui, "manufacturer");
            String model = getString(addCpuGui, "model");
            String socket = getString(addCpuGui, "socket");
            int coreCount = getInt(addCpuGui, "core");
            String clockSpeed =  getString(addCpuGui, "coreClock") + "/" + getString(addCpuGui, "boostClock");
            int powerConsumption = getInt(addCpuGui, "power");
            double price = getDouble(addCpuGui, "price");

            CPU cpu = new CPU(manufacturer, model, socket, coreCount, clockSpeed, powerConsumption, price);

            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("CPU successfully added");
            info.setHeaderText("The following CPU was added:");
            info.setContentText(cpu.toString());
            info.showAndWait();

            resetGui(addCpuGui,
                    "manufacturer",
                    "model",
                    "socket",
                    "core",
                    "coreClock",
                    "boostClock",
                    "power",
                    "price");
        } catch (IllegalArgumentException e){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle(e.getClass().toString());
            error.setContentText(e.getMessage());
            error.showAndWait();
        }
    }

    @FXML
    void addCooler(ActionEvent event) {
        try{
            String manufacturer = getString(addCoolerGui, "manufacturer");
            String model = getString(addCoolerGui, "model");
            String dimension = getString(addCoolerGui, "width") + " x " +
                    getString(addCoolerGui, "depth") + " x " +
                    getString(addCoolerGui, "height");
            String rpm = getString(addCoolerGui, "baseRpm") + " - " + getString(addCoolerGui, "maxRpm");
            String noise = getString(addCoolerGui, "baseNoise") + " - " + getString(addCoolerGui, "maxNoise");
            double power = getDouble(addCoolerGui, "power");
            double price = getDouble(addCoolerGui, "price");

            System.out.println(getString(addCoolerGui, "baseRpm"));
            System.out.println(getString(addCoolerGui, "maxRpm"));

            Cooler cooler = new Cooler(manufacturer, model, dimension, rpm, noise, power, price);

            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Cooler successfully added");
            info.setHeaderText("The following cooler was added:");
            info.setContentText(cooler.toString());
            info.showAndWait();

            resetGui(addCoolerGui,
                    "manufacturer",
                    "model",
                    "width",
                    "depth",
                    "height",
                    "baseRpm",
                    "maxRpm",
                    "baseNoise",
                    "maxNoise",
                    "power",
                    "price");
        } catch (IllegalArgumentException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle(e.getClass().toString());
            error.setContentText(e.getMessage());
            error.showAndWait();
        }
    }

    @FXML
    void addGraphicCard(ActionEvent event) {

        try{
            String manufacturer = getString(addGraphicCardGui, "manufacturer");
            String model=getString(addGraphicCardGui, "model");
            int memory=getInt(addGraphicCardGui,"memory");
            String memoryType=getString(addGraphicCardGui, "memoryType");
            String clockSpeed=getString(addGraphicCardGui,"baseClock") + "/" + getString(addGraphicCardGui, "boostClock");
            double price=getDouble(addGraphicCardGui, "price");

            GraphicCard graphicCard=new GraphicCard(manufacturer, model, price, memory, memoryType, clockSpeed);

            Alert info=new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Graphic card successfully added");
            info.setHeaderText("The following graphic card was added:");
            info.setContentText(graphicCard.toString());
            info.showAndWait();

            resetGui(addGraphicCardGui,
                    "manufacturer",
                    "model",
                    "memory",
                    "memoryType",
                    "baseClock",
                    "boostClock",
                    "price"
                    );
        }catch (IllegalArgumentException e){
            Alert error=new Alert(Alert.AlertType.ERROR);
            error.setTitle(e.getClass().toString());
            error.setContentText(e.getMessage());
            error.showAndWait();
        }


    }


    @FXML
    void addMemory(ActionEvent event) {

        try {
            String manufacturer= getString(addMemoryGui, "manufacturer");
            String model=getString(addMemoryGui, "model");
            int RAM=getInt(addMemoryGui, "RAM");
            String speedTech=getString(addMemoryGui, "speedTech");
            int speed=getInt(addMemoryGui, "speed");
            double price=getDouble(addMemoryGui, "price");


            Memory memory=new Memory(manufacturer,model,price, RAM, speedTech, speed);

            Alert info= new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Memory was successfully added");
            info.setHeaderText("The following component was added:");
            info.setContentText(memory.toString());
            info.showAndWait();

            resetGui(addMemoryGui,"manufacturer", "model", "RAM", "speed", "speedTech", "price");


        }catch (IllegalArgumentException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle(e.getClass().toString());
            error.setContentText(e.getMessage());
            error.showAndWait();

        }


    }


    public void setSuperHome(GridPane superHome) {
        this.superHome = superHome;
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
        return Integer.parseInt(((TextField) pane.lookup("#" + id)).getText());
    }
    private double getDouble(Pane pane, String id){
        return Double.parseDouble(((TextField) pane.lookup("#" + id)).getText());
    }
    private void resetGui(Pane pane, String... id){
        for(String str : id){
            ((TextField) pane.lookup("#" + str)).setText("");
        }
    }

    @FXML
    void close(ActionEvent event) {
        superHome.lookup("#addComponent").setDisable(false);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Limit.text2int(addCpuGui, "core");
        Limit.text2double(addCpuGui,"coreClock", "boostClock", "power", "price");

        Limit.text2int(addCoolerGui, "baseRpm", "maxRpm");
        Limit.text2double(addCoolerGui, "width", "depth", "baseNoise", "maxNoise", "height", "power", "price");
    }
}
