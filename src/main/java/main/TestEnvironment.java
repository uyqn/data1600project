package main;

import components.CPU;
import components.Component;
import components.Cooler;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
        Component cooler = new Cooler("Cooler Master","MasterFan SF360R ARGB","36.0 x 12.0 x 2.5","650.0 - 1800.0",
                "8.0 - 30", 6.48,499.0);

        testList.add(null);

        TreeItem<String> cpuItem = new TreeItem<>(cpu.getName());
        System.out.println(cpuItem.getChildren().size());
        TreeItem<String> cpuLeaf1 = new TreeItem<>(cpu.getPrice() + "");
        cpuItem.getChildren().add(cpuLeaf1);

        TreeItem<String> test11 = new TreeItem<>(null);
        TreeItem<String> test12 = new TreeItem<>("Not null");
        cpuItem.getChildren().addAll(test11, test12);


        TreeView<String> tree = new TreeView<>(cpuItem);

        System.out.println(cpuItem.getChildren().contains(test11));

        StackPane layout = new StackPane();
        layout.getChildren().add(tree);
        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.show();
        //------------------------------------

        //Platform.exit(); //Ikke fjern denne!
    }
}
