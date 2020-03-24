package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI<T> {
    private ActionEvent event;
    private FXMLLoader fxmlLoader;
    private Parent parent;

    public GUI(ActionEvent event, String location) throws IOException {
        this.event = event;
        this.fxmlLoader = new FXMLLoader(getClass().getResource("/main/"+ location + ".fxml"));
        this.parent = fxmlLoader.load();
    }

    public T getController(){
        return fxmlLoader.getController();
    }

    public void newWindow() throws IOException {
        Stage stage = new Stage();
        stage.setScene(scene());
        stage.show();
    }

    public void switchScene() throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene());
    }

    private Scene scene() throws IOException {
        return new Scene(parent);
    }
}
