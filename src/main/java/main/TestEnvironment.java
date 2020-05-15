package main;

import components.CPU;
import components.Component;
import components.Cooler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TestEnvironment extends Application {
    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //All testing av koder og shit går her:
        primaryStage.setTitle("Test app");

        ObservableList<Component> testList = FXCollections.observableArrayList();
        Component cpu = new CPU("AMD","Ryzen 7 3800X","AM4",8,"3.9/4.5",105.0,4219.0);
        Component cooler = new Cooler("Cooler Master","MasterFan SF360R ARGB","650.0 - 1800.0",
                "8.0 - 30", 6.48,499.0);

        testList.add(null);

        String test1 = "ATX";
        String test2 = "Flex ATX";

        List<String> csvList = Files.readAllLines(Paths.get("test.csv"));

        System.out.println(csvList.isEmpty());

        //------------------------------------

        Platform.exit(); //Ikke fjern denne!
    }
}
