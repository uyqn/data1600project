package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import users.User;

import java.io.IOException;

public class RootUserController {

    @FXML
    private Label centerLabel;

    @FXML
    void signOut(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("login.fxml"));
        LoginController loginController = loader.getController();
        loader.load();

        centerLabel.setText("Hello" + loginController.getSignedInUser().getUsername());
    }

}