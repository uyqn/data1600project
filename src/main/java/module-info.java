module main {
    requires javafx.controls;
    requires javafx.fxml;

    opens main to javafx.fxml;
    exports main;

    exports users;

    opens components;
    exports components;

    opens controllers to javafx.fxml;
    exports controllers;

    opens controllers.user to javafx.fxml;
    exports controllers.user;

    opens controllers.component to javafx.fxml;
    exports controllers.component;

    opens controllers.views to javafx.fxml;
    exports controllers.views;

    opens fileManager to javafx.fxml;
    exports fileManager;

    opens listManager to javafx.fxml;
    exports listManager;
}