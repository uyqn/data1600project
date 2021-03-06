package main;

import components.Component;
import components.Computer;
import fileManager.FileOpenerBin;
import fileManager.FileSaverBin;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import listManager.ListableList;
import listManager.UserList;
import users.EndUser;
import users.SuperUser;
import users.User;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * JavaFX App
 * */
public class App extends Application {
    public static ListableList<Component> listableList = new ListableList<>();
    public static User user;
    public static Computer computer;

    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        initiateCoreUsers();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        FileOpenerBin tempOpener = new FileOpenerBin();
        listableList.setList(tempOpener.open(Paths.get("temp.bin")));

        stage.setOnCloseRequest(windowEvent -> {
            FileSaverBin<Component> tempSaver = new FileSaverBin<>();
            try {
                tempSaver.save(Paths.get("temp.bin"), listableList);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Platform.exit();
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void initiateCoreUsers(){
        User superUser = new SuperUser("admin", "admin");
        User endUser = new EndUser("bruker", "bruker");

        UserList.add(superUser);
        UserList.add(endUser);
    }
}