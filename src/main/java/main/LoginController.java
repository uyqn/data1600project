package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import users.User;
import users.UserList;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {
    @FXML
    private GridPane loginGUI;

    private User signedInUser = null;

    @FXML
    void signIn(ActionEvent event) throws IOException {
        signedInUser = fetchUser();
        if(signedInUser != null){
            switch (signedInUser.getAccessLevel()) {
                case 0: App.setRoot("rootUser"); break;
                case 1: App.setRoot("superUser"); break;
                case 2: App.setRoot("endUser"); break;
            }
        }
        else{
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Cannot sign in");
            error.setHeaderText("Unable to sign in to " + getString("usernameInput"));
            error.setContentText("The user either does not exist or the password is invalid");
        }
    }

    @FXML
    void register(ActionEvent event) throws IOException {
        App.setRoot("register");
    }

    private String getString(String id){
        return ((TextField) loginGUI.lookup("#"+id)).getText();
    }

    private User fetchUser(){
        String usernameInput = getString("usernameInput");
        String passwordInput = getString("passwordInput");

        for(User user : UserList.getMembers()){
            if(usernameInput.equals(user.getUsername()) && passwordInput.equals(user.getPassword())){
                return user;
            }
        }
        return null;
    }

    public User getSignedInUser(){
        return signedInUser;
    }
}
