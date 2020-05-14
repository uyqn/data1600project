module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens main to javafx.fxml;
    exports main;

    exports users;

    opens components to javafx.fxml;
    exports components;

    opens Exceptions to javafx.fxml;
    exports Exceptions;

    opens controllers.guiManager to javafx.fxml;
    exports controllers.guiManager;

    opens controllers to javafx.fxml;
    exports controllers;

    opens controllers.user to javafx.fxml;
    exports controllers.user;

    opens controllers.user.endUsers to javafx.fxml;
    exports controllers.user.endUsers;

    opens controllers.component to javafx.fxml;
    exports controllers.component;

    opens controllers.views to javafx.fxml;
    exports controllers.views;

    opens components.Storage to javafx.fxml;
    exports components.Storage;

    opens fileManager to javafx.fxml;
    exports fileManager;

    opens listManager to javafx.fxml;
    exports listManager;
}