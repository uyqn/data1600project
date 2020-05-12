package controllers.user;

import controllers.LoginController;
import controllers.component.ComponentController;
import controllers.guiManager.GUI;
import controllers.views.ComponentView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.layout.GridPane;
import main.App;
import users.User;

import java.io.IOException;

public class SuperUserController {

    User user;

    @FXML
    private Menu userAccount;

    @FXML
    private GridPane superHome;

    @FXML
    void open(ActionEvent event) {
        App.fileManager.open();
    }

    @FXML
    void save(ActionEvent event) {
        App.fileManager.save(App.componentList);
    }

    @FXML
    void saveAs(ActionEvent event) {
        App.fileManager.saveAs(App.componentList);
    }

    @FXML
    void addComponent(ActionEvent event) throws IOException {
        GUI<ComponentController> addComponentWindow = new GUI<>(event, "user/component");
        addComponentWindow.newWindow();
        addComponentWindow.getController().setSuperHome(superHome);
        addComponentWindow.getStage().setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            addComponentWindow.getStage().close();
            superHome.lookup("#addComponent").setDisable(false);
        });
        superHome.lookup("#addComponent").setDisable(true);
    }

    @FXML
    void viewComponents(ActionEvent event) throws IOException {
        GUI<ComponentView> addViewComponentWindow = new GUI<>(event, "views/components");
        addViewComponentWindow.newWindow();
        addViewComponentWindow.getStage().setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            addViewComponentWindow.getStage().close();
            superHome.lookup("#viewComponents").setDisable(false);
        });
        superHome.lookup("#viewComponents").setDisable(true);
    }

    @FXML
    void signOut(ActionEvent event) throws IOException {
        GUI<LoginController> toLogin = new GUI<>(event, "login");
        toLogin.switchScene();
    }
    public void setUser(User user) {
        this.user = user;
        userAccount.setText(this.user.getUsername());
    }
}
