package dk.easv.helpers;

public enum ViewType {
    MAIN("views/main-view.fxml"),
    LOGIN("views/login-view.fxml"),
    CREATE_CUSTOMER("views/add-customer-view.fxml"),
    WORKERS("views/workers-view.fxml"),
    CREATE_DOCUMENT("views/create-document-view.fxml"),
    CREATE_WORKER("views/add-worker-view.fxml"),
    CUSTOMERS_VIEW("views/customers-view.fxml"),
    DOCUMENT("views/document-view.fxml"),
    PROJECTS_VIEW("views/projects-view.fxml"),
    DOCUMENTS_VIEW("views/documents-view.fxml"),
    ADD_PROJECT("views/add-project-view.fxml"),
    ASSIGN_PROJECT("views/assign-project-view.fxml"),
    CANVAS("views/Canvas.fxml");

    private final String path;

    ViewType(String path) {
        this.path = path;
    }

    public String getFXMLView() {
        return path;
    }
}
