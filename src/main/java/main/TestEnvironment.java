package main;

import components.CPU;
import components.Component;
import components.Cooler;
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
        Component cpu = new CPU("AMD", "Ryzen 7 3800X", "AM4", 8, "3.9/4.5", 105, 4219);
        Cooler cooler = new Cooler("Cooler Master", "MasterFan SF360R ARGB", "36 12 2.5", "650 1800", "30 8", 6.48,
                499);

        App.componentList.add(cpu);
        App.componentList.add(cooler);

        Platform.exit(); //Ikke fjern denne
    }
}
