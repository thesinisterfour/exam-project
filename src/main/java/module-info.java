module dk.easv {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;
    requires com.microsoft.sqlserver.jdbc;
    requires MaterialFX;
    requires AnimateFX;


    opens dk.easv to javafx.fxml;
    exports dk.easv;
    exports dk.easv.gui.controllers;
    opens dk.easv.gui.controllers to javafx.fxml;
    exports dk.easv.helpers;
    opens dk.easv.helpers to javafx.fxml;
}