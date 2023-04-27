package dk.easv.helpers;

public enum ViewType {
    MAIN {
        @Override
        public String getFXMLView() {
            return "views/hello-view.fxml";
        }
    },

    ADMINVIEW {
        @Override
        public String getFXMLView() {
            return "views/admin-view.fxml";
        }

    },

    LOGIN {
        @Override
        public String getFXMLView() {
            return "views/login-view.fxml";
        }
    };
    public abstract String getFXMLView();
}
