package main;

import components.Component;
import fileManager.FileManager;
import fileManager.FileOpenerCSV;
import fileManager.FileSaverCSV;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import listManager.ComponentList;
import users.User;
import users.UserList;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * JavaFX App
 */
public class App extends Application {
    public static FileManager fileManager = new FileManager();
    public static ComponentList<Component> componentList = new ComponentList<>();

    @Override
    public void start(Stage stage) throws IOException {
        initiateCoreUsers();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        FileOpenerCSV tempOpener = new FileOpenerCSV();

        componentList.setList(tempOpener.open(Paths.get("temp.csv")));

        stage.setOnCloseRequest(windowEvent -> {
            FileSaverCSV<Component> tempSaver = new FileSaverCSV<>();
            try {
                tempSaver.save(Paths.get("temp.csv"), componentList);
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
        User admin = new User("admin", "super",1);
        User uyqn = new User("uyqn", "s341864", 1);
        User helene = new User("hele", "s341873", 1);
        /*User end=new User("end", "passord", 2);*/
        UserList.add(uyqn);
        UserList.add(admin);
        UserList.add(helene);
        /*UserList.add(end);*/
    }
}