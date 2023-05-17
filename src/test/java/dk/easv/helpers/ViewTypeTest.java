package dk.easv.helpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ViewTypeTest {

    @Test
    void getFXMLView() {
        assertEquals("views/admin-view.fxml", ViewType.ADMIN.getFXMLView());
        assertEquals("views/project-manager-view.fxml", ViewType.PROJECT_MANAGER.getFXMLView());
        assertEquals("views/salesperson-view.fxml", ViewType.SALESPERSON.getFXMLView());
        assertEquals("views/technician-view.fxml", ViewType.TECHNICIAN.getFXMLView());
        assertEquals("views/HboxCard.fxml", ViewType.WORKER_CARD.getFXMLView());
        assertEquals("views/login-view.fxml", ViewType.LOGIN.getFXMLView());
        assertEquals("views/customers-view.fxml", ViewType.CUSTOMERS.getFXMLView());
        assertEquals("views/add-customer-view.fxml", ViewType.CREATE_CUSTOMERS.getFXMLView());
        assertEquals("views/HboxCard.fxml", ViewType.HBOX_CARD.getFXMLView());
        assertEquals("views/Card.fxml", ViewType.CARD.getFXMLView());
        assertEquals("views/ProjectCard.fxml", ViewType.PROJECT_CARD.getFXMLView());
        assertEquals("views/worker-users-view.fxml", ViewType.USERS_VIEW.getFXMLView());
        assertEquals("views/create-document-view.fxml", ViewType.CREATE_DOCUMENT.getFXMLView());
        assertEquals("views/document-view.fxml", ViewType.DOCUMENT.getFXMLView());
    }
}