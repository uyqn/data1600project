package controllers.user;

import controllers.LoginController;
import controllers.guiManager.GUI;
import controllers.user.endUsers.BuildPcController;
import controllers.user.endUsers.PrevPcController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.layout.GridPane;
import main.App;
import users.EndUser;

import java.io.IOException;

public class EndUserController{

    private EndUser user;

    @FXML
    private Menu userAccountMenu;

    @FXML
    private Label centerLabel;

    @FXML
    private Button btnBuild;

    @FXML
    private Button btnPrev;

    @FXML
    private Label lblWelcome;

    @FXML
    private GridPane endHome;

    @FXML
    void open(ActionEvent event) {
        try {
            App.user.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void save(ActionEvent event) {
        App.user.save(App.user.getComputers());
    }

    @FXML
    void saveAs(ActionEvent event) {
        App.user.saveAs(App.user.getComputers());
    }

    @FXML
    void buildPc(ActionEvent event) throws IOException {
        GUI<BuildPcController> buildPc = new GUI<>(event, "user/endUsers/buildPc");
        buildPc.switchScene();
    }


    @FXML
    void showPrev(ActionEvent event) throws IOException {
        GUI<PrevPcController> prevPc = new GUI<>(event, "user/endUsers/prevPC");
        prevPc.switchScene();
    }

    @FXML
    void signOut(ActionEvent event) throws IOException {
        GUI<LoginController> toLogin = new GUI<>(event, "login");
        toLogin.switchScene();
    }

    public void setUser(EndUser user) {
        this.user = user;
        this.userAccountMenu.setText(user.getUsername());
    }
}