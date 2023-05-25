package dk.easv.helpers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ViewTypeTest {

    @Test
    void getFXMLView() {
        Assertions.assertEquals("views/login-view.fxml", ViewType.LOGIN.getFXMLView());
        Assertions.assertEquals("views/add-customer-view.fxml", ViewType.ADD_CUSTOMER.getFXMLView());
        Assertions.assertEquals("views/workers-view.fxml", ViewType.WORKERS.getFXMLView());
        Assertions.assertEquals("views/add-document-view.fxml", ViewType.ADD_DOCUMENT.getFXMLView());
        Assertions.assertEquals("views/add-worker-view.fxml", ViewType.ADD_WORKER.getFXMLView());
        Assertions.assertEquals("views/customers-view.fxml", ViewType.CUSTOMERS_VIEW.getFXMLView());
        Assertions.assertEquals("views/document-view.fxml", ViewType.DOCUMENT.getFXMLView());
        Assertions.assertEquals("views/projects-view.fxml", ViewType.PROJECTS_VIEW.getFXMLView());
        Assertions.assertEquals("views/documents-view.fxml", ViewType.DOCUMENTS_VIEW.getFXMLView());
        Assertions.assertEquals("views/add-project-view.fxml", ViewType.ADD_PROJECT.getFXMLView());
        Assertions.assertEquals("views/assign-project-view.fxml", ViewType.ASSIGN_PROJECT.getFXMLView());
        Assertions.assertEquals("views/canvas.fxml", ViewType.CANVAS.getFXMLView());
    }
}