package controllers;

import controllers.guiManager.DialogBox;
import controllers.guiManager.GUI;
import controllers.user.EndUserController;
import controllers.user.SuperUserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import listManager.UserList;
import users.EndUser;
import users.SuperUser;
import users.User;

import java.io.IOException;

public class LoginController {
    @FXML
    private GridPane loginGUI;

    @FXML
    void signIn(ActionEvent event) throws IOException {
        User user = fetchUser();
        if (user != null) {
            if(user.getClass() == SuperUser.class){
                GUI<SuperUserController> toSuper = new GUI<>(event, "user/superUser");
                toSuper.getController().setUser((SuperUser) user);
                toSuper.switchScene();
            } else {
                GUI<EndUserController> toEnd = new GUI<>(event, "user/endUser");
                toEnd.getController().setUser((EndUser) user);
                toEnd.switchScene();
            }
        } else {
            DialogBox.error(
                    "Cannot sign in",
                    "Unable to sign in with " + getString("usernameInput"),
                    "The username or password is invalid."
            );
        }
    }

    @FXML
    void register(ActionEvent event) throws IOException {
        GUI<RegisterController> toRegister = new GUI<>(event, "register");
        toRegister.switchScene();
    }

    private String getString(String id) {
        return ((TextField) loginGUI.lookup("#" + id)).getText();
    }

    private User fetchUser() {
        String usernameInput = getString("usernameInput");
        String passwordInput = getString("passwordInput");

        for (User user : UserList.getMembers()) {
            if (usernameInput.equals(user.getUsername()) && passwordInput.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
