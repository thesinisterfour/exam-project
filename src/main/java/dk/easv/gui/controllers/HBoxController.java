package dk.easv.gui.controllers;

import dk.easv.Main;
import dk.easv.be.Card;
import dk.easv.be.Customer;
import dk.easv.be.Project;
import dk.easv.be.User;
import dk.easv.gui.rootContoller.RootController;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HBoxController extends RootController {

    @FXML
    private HBox mainHboxCard;
    private ConcurrentMap<Integer, User> users = new ConcurrentHashMap<>();
    private User selectedUser = null;
    EventHandler<MouseEvent> mouseClickHandler = new EventHandler<>() {
        @Override
        public void handle(MouseEvent event) {
            GridPane gPane = (GridPane) event.getSource();
            gPane = (GridPane) gPane.getChildren().get(0);
            javafx.scene.control.Label userName = (javafx.scene.control.Label) gPane.getChildren().get(0);

            for (User user : users.values()) {
                if (user.getFirstName().equals(userName.getText())) {
                    selectedUser = user;
                    // sout only for testing, can be removed later after all functions are added and tested.
                    // OPTIONAL: change style/color when selecting a card, so the user can tell that the card is selected.
                    System.out.println("FirstName:" + selectedUser.getFirstName() + ", LastName:" + selectedUser.getLastName());
                    break;
                }
            }
        }
    };
    private ConcurrentMap<Integer, Customer> customers = new ConcurrentHashMap<>();
    private ConcurrentMap<Integer, Project> projects = new ConcurrentHashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setUserBoxes(ConcurrentMap<Integer, User> user) {
        this.users = user;
        populateUserHBox();
    }

    public void setCustomerBoxes(ConcurrentMap<Integer, Customer> customers) {
        this.customers = customers;
        populateCustomersHBox();
    }

    public void setProjectBoxes(ConcurrentMap<Integer, Project> projects) {
        this.projects = projects;
        populateProjectHBox();
    }

    private void populateUserHBox() {
        try {
            ObservableList<Node> children = mainHboxCard.getChildren();
            Set<Integer> keys = users.keySet();
            for (Integer key : keys) {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("views/Card.fxml")));
                Parent parent = loader.load();
                CardController cardController = loader.getController();
                cardController.receiveUserData(users);
                cardController.createCards(new Card(users.get(key).getFirstName(), users.get(key).getLastName(), users.get(key).getRole().toString()));
                children.addAll(parent);
                for (Node child : children) {
                    child.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClickHandler);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void populateCustomersHBox() {
        try {
            ObservableList<Node> children = mainHboxCard.getChildren();
            Set<Integer> keys = customers.keySet();
            for (Integer key : keys) {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("views/Card.fxml")));
                Parent parent = loader.load();
                CardController cardController = loader.getController();
                cardController.receiveCustomerData(customers);
                cardController.createCards(new Card(customers.get(key).getCustomerName(), customers.get(key).getCustomerAddress(), customers.get(key).getZipCode(), customers.get(key).getCustomerEmail()));
                children.addAll(parent);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    private void populateProjectHBox() {
        try {
            ObservableList<Node> children = mainHboxCard.getChildren();
            Set<Integer> keys = projects.keySet();
            for (Integer key : keys) {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("views/ProjectCard.fxml")));
                Parent parent = loader.load();
                ProjectCardController projectCardController = loader.getController();
                projectCardController.receiveProjectData(projects);
                projectCardController.createProCards(new Project(projects.get(key).getProjectID(), projects.get(key).getProjectName(), projects.get(key).getProjectAddress(), projects.get(key).getProjectZipcode()));
                children.addAll(parent);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}

