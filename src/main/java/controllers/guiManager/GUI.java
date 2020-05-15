package controllers.guiManager;

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
    private Stage stage;

    public GUI(String location) throws IOException {
        this.fxmlLoader = new FXMLLoader(getClass().getResource("/main/"+ location + ".fxml"));
        this.parent = fxmlLoader.load();
    }

    public GUI(ActionEvent event, String location) throws IOException {
        this.event = event;
        this.fxmlLoader = new FXMLLoader(getClass().getResource("/main/"+ location + ".fxml"));
        this.parent = fxmlLoader.load();
    }

    public void setEvent(ActionEvent event) {
        this.event = event;
    }

    public T getController(){
        return fxmlLoader.getController();
    }

    public void newWindow() {
        stage = new Stage();
        stage.setScene(scene());
        stage.show();
    }

    public Stage getStage(){
        return stage;
    }
    public boolean isShowing(){
        return stage.isShowing();
    }

    public void switchScene() {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene());
        stage.centerOnScreen();
    }

    private Scene scene() {
        return new Scene(parent);
    }
}
