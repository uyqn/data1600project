package controllers;

import controllers.guiManager.GUI;
import controllers.user.EndUserController;
import controllers.user.RootUserController;
import controllers.user.SuperUserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import users.User;
import users.UserList;

import java.io.IOException;

public class LoginController {
    @FXML
    private GridPane loginGUI;

    @FXML
    void signIn(ActionEvent event) throws IOException {
        User user = fetchUser();
        if (user != null) {
            switch (user.getAccessLevel()) {
                case 0:
                    GUI<RootUserController> toRoot = new GUI<>(event, "user/rootUser");
                    toRoot.getController().setUser(user);
                    toRoot.switchScene();
                    break;
                case 1:
                    GUI<SuperUserController> toSuper = new GUI<>(event, "user/superUser");
                    toSuper.getController().setUser(user);
                    toSuper.switchScene();
                    break;
                case 2:
                    GUI<EndUserController> toEnd = new GUI<>(event, "user/endUser");
                    toEnd.getController().setUser(user);
                    toEnd.switchScene();
                    break;
            }
        } else {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Cannot sign in");
            error.setHeaderText("Unable to sign in with " + getString("usernameInput"));
            error.setContentText("The username or password is invalid.");
            error.showAndWait();
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
