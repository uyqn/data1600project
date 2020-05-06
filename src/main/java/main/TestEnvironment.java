package main;

import components.Component;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TestEnvironment extends Application {
    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //All testing av koder og shit går her:

        String name = "java.lang.String";

        TableColumn<?, ?> test = new TableColumn<Component, Integer>("Test");
        test.setCellValueFactory(new PropertyValueFactory<>("price"));

        //------------------------------------
        Platform.exit(); //Ikke fjern denne!
    }
}
