package dk.easv.helpers;

public enum ViewType {
    ADMIN("views/admin-view.fxml"),

    PROJECT_MANAGER("views/project-manager-view.fxml"),

    SALESPERSON ("views/salesperson-view.fxml"),

    TECHNICIAN ("views/technician-view.fxml"),

    WORKER_CARD("views/HboxCard.fxml"),

    LOGIN("views/login-view.fxml"),
    CUSTOMERS("views/customers-view.fxml"),
    CREATE_CUSTOMERS("views/add-customer-view.fxml"),
    HBOX_CARD("views/HboxCard.fxml"),

    CARD("views/Card.fxml"),
    PROJECT_CARD("views/ProjectCard.fxml"),
    USERS_VIEW("views/worker-users-view.fxml"),

    CREATE_DOCUMENT("views/create-document-view.fxml"),
    DOCUMENT("views/document-view.fxml"),

    CANVAS("views/Canvas.fxml");

    private final String path;
    ViewType(String path){
        this.path = path;
    }

    public String getFXMLView(){
        return path;
    }
}
