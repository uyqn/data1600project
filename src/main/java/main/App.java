package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import users.User;
import users.UserList;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        initializeCoreFunctions();
        scene = new Scene(loadFXML("login"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    private void initializeCoreFunctions(){
        User root = new User("root", "r00t", 0);
        User uy = new User("uyqn", "s341864", 1);
        User andreas = new User("andreasth", "s338851", 1);
        User helene = new User("helenebp", "s341873", 1);
        User kunde = new User("kunde", "regular", 2);

        UserList.add(root);
        UserList.add(uy);
        UserList.add(andreas);
        UserList.add(helene);
        UserList.add(kunde);
    }
}