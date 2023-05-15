package dk.easv.gui.controllers;

import dk.easv.be.User;
import dk.easv.gui.models.interfaces.IUserModel;
import dk.easv.gui.models.UserModel;
import dk.easv.gui.rootContoller.RootController;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WorkerCardController extends RootController {

    private final IUserModel userModel = new UserModel();



    private ConcurrentMap<Integer, User> users = new ConcurrentHashMap<>();

    private HBox hBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            exampleMika();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /*public void showWorker() throws IOException {
        populateHBox();
    }

     */

    public void exampleMika() throws SQLException {
        users = userModel.getAllUsers();
        System.out.println(users.get(users.size()).getFirstName());
    }

    public void setWorkerView(HBox hBox) {
        this.hBox = hBox;

    }


}