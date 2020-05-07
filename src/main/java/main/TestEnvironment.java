package main;

import components.CPU;
import components.Component;
import components.Cooler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import listManager.ComponentList;

public class TestEnvironment extends Application {
    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //All testing av koder og shit går her:

        ComponentList<Component> list1 = new ComponentList<>();
        ComponentList<CPU> list2 = new ComponentList<>();

        Component cpu = new CPU("AMD","Ryzen 7 3800X","AM4",8,"3.9/4.5",105.0,4219.0);
        Component cooler = new Cooler("Cooler Master","MasterFan SF360R ARGB","36.0 x 12.0 x 2.5","650.0 - 1800.0",
                "8.0 - 30", 6.48,499.0);

        //------------------------------------

        list1.addAll(cpu, cooler);

        System.out.println(list1);

        Platform.exit(); //Ikke fjern denne!
    }
}
