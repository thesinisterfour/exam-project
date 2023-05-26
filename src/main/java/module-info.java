module dk.easv {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;
    requires com.microsoft.sqlserver.jdbc;
    requires MaterialFX;
    requires VirtualizedFX;
    requires AnimateFX;
    requires kernel;
    requires layout;
    requires io;
    requires org.apache.commons.io;


    opens dk.easv to javafx.fxml;
    exports dk.easv;
    exports dk.easv.gui.controllers;
    opens dk.easv.gui.controllers to javafx.fxml;
    exports dk.easv.helpers;
    opens dk.easv.helpers to javafx.fxml;
    exports dk.easv.gui.controllers.helpers;
    opens dk.easv.gui.controllers.helpers to javafx.fxml;
}