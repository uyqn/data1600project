package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import users.User;
import users.UserList;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        initiateCoreUsers();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        stage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void initiateCoreUsers(){
        User admin = new User("admin", "super",1);
        UserList.add(admin);
    }
}