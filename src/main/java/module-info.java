module main {
    requires javafx.controls;
    requires javafx.fxml;

    opens main to javafx.fxml;
    exports main;

    exports users;
    opens controllers to javafx.fxml;
    exports controllers;
}