package controllers.user;

import controllers.guiManager.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import controllers.LoginController;
import users.User;

import java.io.IOException;

public class EndUserController{

    @FXML
    private Label centerLabel;

    @FXML
    void signOut(ActionEvent event) throws IOException {
        GUI<LoginController> toLogin = new GUI<>(event, "login");
        toLogin.switchScene();
    }

    public void setUser(User user) {
        centerLabel.setText("Signed in as " + user.getUsername());
    }
}