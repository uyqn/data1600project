package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import users.EndUser;
import users.SuperUser;
import users.User;
import users.UserList;

import java.io.IOException;

public class LoginController {
    @FXML
    private GridPane loginGUI;

    @FXML
    void signIn(ActionEvent event) {

    }

    @FXML
    void register(ActionEvent event) throws IOException {
        App.setRoot("register");
    }

    private String getString(String id){
        return ((TextField) loginGUI.lookup("#"+id)).getText();
    }
}
