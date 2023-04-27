package dk.easv.helpers;

public enum ViewType {
    MAIN {
        @Override
        public String getFXMLView() {
            return "views/hello-view.fxml";
        }
    },

    LOGIN {
        @Override
        public String getFXMLView() {
            return "views/login-view.fxml";
        }
    },

    PROJECT_MANAGER {
        @Override
        public String getFXMLView() {
            return "views/project-manager-view.fxml";
        }
    };
    public abstract String getFXMLView();
}
