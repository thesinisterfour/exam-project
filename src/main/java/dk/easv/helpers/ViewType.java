package dk.easv.helpers;

public enum ViewType {
    MAIN("views/main-view.fxml"),
    LOGIN("views/login-view.fxml"),
    CREATE_CUSTOMERS("views/add-customer-view.fxml"),
    HBOX_CARD("views/HboxCard.fxml"),
    WORKERS("views/workers-view.fxml"),
    CARD("views/Card.fxml"),
    PROJECT_CARD("views/ProjectCard.fxml"),
    USERS_VIEW("views/worker-users-view.fxml"),
    CREATE_DOCUMENT("views/create-document-view.fxml"),
    CREATE_WORKER("views/add-worker-view.fxml"),

    BUSINESS_VIEW("views/customers-view.fxml"),
    DOCUMENT("views/document-view.fxml"),
    PROJECTS_VIEW("views/projects-view.fxml"),
    DOCUMENTS_VIEW("views/documents-view.fxml"),
    ADD_PROJECT("views/add-project-view.fxml"),
    ASSIGN_PROJECT("views/assign-project-view.fxml"),
    CANVAS("views/Canvas.fxml");

    private final String path;
    ViewType(String path){
        this.path = path;
    }

    public String getFXMLView(){
        return path;
    }
}
