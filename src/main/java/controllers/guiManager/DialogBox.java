package controllers.guiManager;

import javafx.scene.control.Alert;

public class DialogBox {
    private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private static Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);

    public static void error(String title, String header, String message){
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    public static void info(String title, String header, String message){
        infoAlert.setTitle(title);
        infoAlert.setHeaderText(header);
        infoAlert.setContentText(message);
        infoAlert.showAndWait();
    }
}
