package main;

import components.CPU;
import components.Component;
import components.Cooler;
import fileManager.FileOpener;
import fileManager.FileOpenerBin;
import fileManager.FileSaver;
import fileManager.FileSaverBin;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import listManager.ComponentList;

import java.nio.file.Paths;

public class TestEnvironment extends Application {
    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //All testing av koder og shit går her:

        ComponentList<Component> testList = new ComponentList<>();

        CPU cpu = new CPU("AMD","Ryzen 7 3800X","AM4",8,"3.9/4.5",105.0,4219.0);
        Component cooler = new Cooler("Cooler Master","MasterFan SF360R ARGB","36.0 x 12.0 x 2.5","650.0 - 1800.0",
                "8.0 - 30", 6.48,499.0);

        testList.add(cpu);
        testList.add(cooler);

        FileSaver<Component> testSaver = new FileSaverBin<>();
        testSaver.save(Paths.get("test.bin"), testList);

        FileOpener testOpener = new FileOpenerBin<>();
        ComponentList<Component> readTest = new ComponentList<>();
        readTest.setList(testOpener.open(Paths.get("test.bin")));

        System.out.println(readTest);
        //------------------------------------

        Platform.exit(); //Ikke fjern denne!
    }
}
