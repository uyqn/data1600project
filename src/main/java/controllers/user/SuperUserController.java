package controllers.user;

import controllers.GUI;
import controllers.LoginController;
import controllers.component.ComponentController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import users.User;

import java.io.IOException;

public class SuperUserController {

    User user;

    @FXML
    private GridPane superHome;

    @FXML
    void addComponent(ActionEvent event) throws IOException {
        GUI<ComponentController> addComponentWindow = new GUI<>(event, "user/component");
        addComponentWindow.newWindow();
    }

    @FXML
    void signOut(ActionEvent event) throws IOException {
        GUI<LoginController> toLogin = new GUI<>(event, "login");
        toLogin.switchScene();
    }
    public void setUser(User user) {
        this.user = user;
    }
}
