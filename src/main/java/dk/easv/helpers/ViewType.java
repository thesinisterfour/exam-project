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

    BUSINESS_VIEW("views/business-view.fxml"),
    DOCUMENT("views/document-view.fxml");

    private final String path;
    ViewType(String path){
        this.path = path;
    }

    public String getFXMLView(){
        return path;
    }
}
