package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class TestEnvironment extends Application {
    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //All testing av koder og shit går her:

        System.out.println(App.fileManager.open());

        Platform.exit(); //Ikke fjern denne
    }
}
