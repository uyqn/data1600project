module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens main to javafx.fxml;
    exports main;

    exports users;

    opens components to javafx.fxml;
    exports components;

    opens exceptions to javafx.fxml;
    exports exceptions;

    opens controllers.guiManager to javafx.fxml;
    exports controllers.guiManager;

    opens controllers to javafx.fxml;
    exports controllers;

    opens controllers.user to javafx.fxml;
    exports controllers.user;

    opens controllers.user.endUsers to javafx.fxml;
    exports controllers.user.endUsers;

    opens components.Storage to javafx.fxml;
    exports components.Storage;

    opens fileManager to javafx.fxml;
    exports fileManager;

    opens listManager to javafx.fxml;
    exports listManager;
}