package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.LoginController;
import users.User;

import java.io.IOException;

public class RootUserController{

    @FXML
    private Label centerLabel;
    private User user;

    @FXML
    void signOut(ActionEvent event) throws IOException {
        GUI<LoginController> toLogin = new GUI<>(event, "login");
        toLogin.switchScene();
    }

    public void setUser(User user) {
        this.user = user;
    }
}