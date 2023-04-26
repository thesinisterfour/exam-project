package dk.easv.gui.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class WorkerViewController implements Initializable {

    @FXML
    private MFXButton newUser, edit, delete, back;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonsTest();

    }

    /***
     * Test controller initial commit
     */
    public void buttonsTest (){
        newUser.setOnAction(e -> System.out.println("new user"));
        edit.setOnAction(e -> System.out.println("edit"));
        delete.setOnAction(e -> System.out.println("delete"));
        back.setOnAction(e -> System.out.println("go back"));
    }
}
