package dk.easv.helpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ViewTypeTest {

    @Test
    void getFXMLView() {
        assertEquals("views/login-view.fxml", ViewType.LOGIN.getFXMLView());
        assertEquals("views/add-customer-view.fxml", ViewType.CREATE_CUSTOMERS.getFXMLView());
        assertEquals("views/HboxCard.fxml", ViewType.HBOX_CARD.getFXMLView());
        assertEquals("views/Card.fxml", ViewType.CARD.getFXMLView());
        assertEquals("views/ProjectCard.fxml", ViewType.PROJECT_CARD.getFXMLView());
        assertEquals("views/worker-users-view.fxml", ViewType.USERS_VIEW.getFXMLView());
        assertEquals("views/create-document-view.fxml", ViewType.CREATE_DOCUMENT.getFXMLView());
        assertEquals("views/document-view.fxml", ViewType.DOCUMENT.getFXMLView());
    }
}