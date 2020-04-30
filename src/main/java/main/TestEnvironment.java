package main;

import components.CPU;
import components.Component;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestEnvironment extends Application {
    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //All testing av koder og shit går her:
        Component test = new CPU("AMD", "Ryzen 7 3800X", "AM4", 8, "3.9/4.5", 105, 4219);

        Path path = Paths.get("binary.bin");

        try (OutputStream os = Files.newOutputStream(path);
             ObjectOutputStream out = new ObjectOutputStream(os))
        {
            out.writeObject(test);
        }

        try (InputStream in = Files.newInputStream(path);
             ObjectInputStream oin = new ObjectInputStream(in))
        {
            CPU kari = (CPU) oin.readObject(); // kan kastes til Person
            System.out.println(kari);
        }

        Platform.exit(); //Ikke fjern denne
    }
}
