package controllers.guiManager;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class Limit {
    public static void text2int(Pane pane, String... id){
        for(String str : id){
            TextField textField = (TextField) pane.lookup("#" + str);
            textField.textProperty().addListener((observableValue, s, t1) -> {
                if(!t1.matches("[+-]?\\d*")){
                    textField.setText(s);
                }
            });
        }
    }

    public static void text2double(Pane pane, String... id){
        for(String str : id){
            TextField textField = (TextField) pane.lookup("#" + str);
            textField.textProperty().addListener((observableValue, s, t1) -> {
                if(!t1.matches("\\d*\\.?\\d{0,2}")){
                    textField.setText(s);
                }
            });
        }
    }
}
