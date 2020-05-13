package controllers.user;

import components.Component;
import controllers.LoginController;
import controllers.component.ComponentController;
import controllers.guiManager.DialogBox;
import controllers.guiManager.GUI;
import controllers.views.ComponentView;
import fileManager.FileOpenerBin;
import fileManager.FileSaverBin;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import main.App;
import users.SuperUser;

import java.io.IOException;
import java.nio.file.Paths;

public class SuperUserController {
    FileOpenerBin opener;
    GUI<ComponentController> addComponentWindow;
    GUI<ComponentView> addViewComponentWindow;
    SuperUser user;

    @FXML
    private BorderPane gui;

    @FXML
    private Menu userAccount;

    @FXML
    private GridPane superHome;

    @FXML
    private Label messageLabel;

    @FXML
    void open(ActionEvent event) {
        user.open();
        if(user.getPath() != null) {
            opener = new FileOpenerBin();
            opener.setPath(user.getPath());
            opener.setOnSucceeded(this::openSuccess);
            opener.setOnFailed(this::openFailed);
            Thread thread = new Thread(opener);
            thread.setDaemon(true);
            messageLabel.setText("Please wait while we load your data...");
            disableGui(true);
            thread.start();
        }
    }

    private void disableGui(boolean disable){
        gui.setDisable(disable);
        if(addComponentWindow != null) {
            if (addComponentWindow.isShowing()) {
                addComponentWindow.getController().disableGui(disable);
            }
        }
        if(addViewComponentWindow != null) {
            if (addViewComponentWindow.isShowing()) {
                addViewComponentWindow.getController().disableGui(disable);
            }
        }
    }

    private void openFailed(WorkerStateEvent workerStateEvent) {
        DialogBox.error(
                "Unable to open file!",
                "Error caused by: ",
                workerStateEvent.getSource().getException().getMessage());
        messageLabel.setText(null);
        disableGui(false);
    }

    private void openSuccess(WorkerStateEvent workerStateEvent) {
        App.listableList.setList(opener.getValue());
        messageLabel.setText(null);
        disableGui(false);
    }

    @FXML
    void save(ActionEvent event) {
        user.save(App.listableList);
    }

    @FXML
    void saveAs(ActionEvent event) {
        user.saveAs(App.listableList);
    }

    @FXML
    void addComponent(ActionEvent event) throws IOException {
        addComponentWindow = new GUI<>(event, "user/component");
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
        addViewComponentWindow = new GUI<>(event, "views/components");
        addViewComponentWindow.newWindow();
        addViewComponentWindow.getController().setSuperHome(superHome);
        addViewComponentWindow.getStage().setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            addViewComponentWindow.getStage().close();
            superHome.lookup("#viewComponents").setDisable(false);
        });
        superHome.lookup("#viewComponents").setDisable(true);
    }

    @FXML
    void signOut(ActionEvent event) throws IOException {
        if(addComponentWindow != null) {
            if (addComponentWindow.isShowing()) {
                addComponentWindow.getStage().close();
            }
        }
        if(addViewComponentWindow != null) {
            if (addViewComponentWindow.isShowing()) {
                addViewComponentWindow.getStage().close();
            }
        }

        FileSaverBin<Component> tempSaver = new FileSaverBin<>();
        try {
            tempSaver.save(Paths.get("temp.bin"), App.listableList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GUI<LoginController> toLogin = new GUI<>(event, "login");
        toLogin.switchScene();
    }

    public void setUser(SuperUser user){
        this.user = user;
        this.userAccount.setText(user.getUsername());
    }
}
