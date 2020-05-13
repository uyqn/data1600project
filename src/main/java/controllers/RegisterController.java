package controllers;

import controllers.guiManager.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import listManager.UserList;
import users.*;

import java.io.IOException;

public class RegisterController {

    @FXML
    private GridPane registrationGUI;

    @FXML
    void signUp(ActionEvent event) {
        User user = createUser();
        if(user != null) {
            try {
                UserList.add(user);
                resetFields();

                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Registration complete");
                success.setHeaderText("Successfully registered " + user.getUsername());
                success.setContentText("Please sign into the app");
                success.showAndWait();

                GUI<LoginController> toLogin = new GUI<>(event, "login");
                toLogin.switchScene();
            } catch (IllegalArgumentException | IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Username taken");
                alert.setHeaderText("A problem occurred when creating person");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    void cancel(ActionEvent event) throws IOException {
        GUI<LoginController> toLogin = new GUI<>(event, "login");
        toLogin.switchScene();
    }

    User createUser(){
        String username = getString("chosenUsername");
        String password;
        if(getString("chosenPassword").equals(getString("confirmedPassword"))){
            password = getString("chosenPassword");
            try{
                return new EndUser(username, password);
            }catch (IllegalArgumentException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Unable to create user!");
                alert.setHeaderText("A problem occurred when creating person");
                alert.setContentText("cause: " + e.getMessage());
                alert.showAndWait();
                return null;
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Mismatch password");
            alert.setHeaderText("Your chosen password and confirmed password does not match");
            alert.setContentText("Please try re-entering your password");
            alert.showAndWait();
            return null;
        }
    }

    private String getString(String id){
        return ((TextField) registrationGUI.lookup("#"+id)).getText();
    }

    private void resetFields(){
        ((TextField) registrationGUI.lookup("#chosenUsername")).setText("");
        ((TextField) registrationGUI.lookup("#chosenPassword")).setText("");
        ((TextField) registrationGUI.lookup("#confirmedPassword")).setText("");
    }
}
