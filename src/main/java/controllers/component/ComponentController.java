package controllers.component;

import controllers.guiManager.Limit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ComponentController implements Initializable {
    @FXML
    private GridPane superHome;

    @FXML
    private GridPane addCPUgui;

    @FXML
    void increaseCore(ActionEvent event){
        int[] validCoreCount = {1, 2, 3, 4, 6, 8, 10, 12, 14, 16,
                18, 20, 22, 24, 26, 28, 30, 32, 64};

        TextField str = (TextField) addCPUgui.lookup("#coreInput");
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

        TextField str = (TextField) addCPUgui.lookup("#coreInput");
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

    }

    @FXML
    void close(ActionEvent event) {
        superHome.lookup("#addComponent").setDisable(false);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Limit.text2int(addCPUgui, "coreInput", "powerInput");
        Limit.text2double(addCPUgui,"coreClockInput", "boostClockInput", "priceInput");
    }
}
