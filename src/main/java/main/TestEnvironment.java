package main;

import components.Component;
import components.Computer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TestEnvironment extends Application {
    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //All testing av koder og shit går her:

        App.componentList.getList().addAll(App.fileManager.open());
        ArrayList<Component> content = new ArrayList<>(App.componentList.getList());

        Computer testComputer = new Computer("Best Computer", 1555.34);

        //Name, [comp1, comp2, comp3]


        //------------------------------------
        Platform.exit(); //Ikke fjern denne!
    }
}
