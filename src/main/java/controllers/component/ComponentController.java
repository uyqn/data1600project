package controllers.component;

import components.CPU;
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
import java.util.ResourceBundle;

public class ComponentController implements Initializable {

    @FXML
    private GridPane superHome;

    @FXML
    private GridPane addCpuGui, addCoolerGui;

    @FXML
    void increaseCore(ActionEvent event){
        int[] validCoreCount = {1, 2, 3, 4, 6, 8, 10, 12, 14, 16,
                18, 20, 22, 24, 26, 28, 30, 32, 64};

        TextField str = (TextField) addCpuGui.lookup("#coreInput");
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

        TextField str = (TextField) addCpuGui.lookup("#coreInput");
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
            String manufacturer = getString(addCpuGui, "manufacturerInput");
            String model = getString(addCpuGui, "modelInput");
            String socket = getString(addCpuGui, "socketInput");
            int coreCount = getInt(addCpuGui, "coreInput");
            String clockSpeed = getString(addCpuGui, "coreClockInput") + "/"
                    + getString(addCpuGui, "boostClockInput");
            int powerConsumption = getInt(addCpuGui, "powerInput");
            double price = getDouble(addCpuGui, "priceInput");

            CPU cpu = new CPU(manufacturer, model, socket, coreCount, clockSpeed, powerConsumption, price);

            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("CPU successfully added");
            info.setHeaderText("The following CPU was added:");
            info.setContentText(cpu.toString());
            info.showAndWait();

            resetGui(addCpuGui,
                    "manufacturerInput",
                    "modelInput",
                    "socketInput",
                    "coreInput",
                    "coreClockInput",
                    "boostClockInput",
                    "powerInput",
                    "priceInput");
        } catch (IllegalArgumentException e){
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
