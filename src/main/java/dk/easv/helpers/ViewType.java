package dk.easv.helpers;

public enum ViewType {
    MAIN("views/main-view.fxml"),
    LOGIN("views/login-view.fxml"),
    ADD_CUSTOMER("views/add-customer-view.fxml"),
    ADD_DOCUMENT("views/add-document-view.fxml"),
    ADD_PROJECT("views/add-project-view.fxml"),
    ADD_WORKER("views/add-worker-view.fxml"),
    ASSIGN_PROJECT("views/assign-project-view.fxml"),
    CANVAS("views/canvas.fxml"),
    CUSTOMERS_VIEW("views/customers-view.fxml"),
    DOCUMENT("views/document-view.fxml"),
    DOCUMENTS_VIEW("views/documents-view.fxml"),
    WORKERS("views/workers-view.fxml"),
    PROJECTS_VIEW("views/projects-view.fxml"),
    ;

    private final String path;

    ViewType(String path) {
        this.path = path;
    }

    public String getFXMLView() {
        return path;
    }
}
