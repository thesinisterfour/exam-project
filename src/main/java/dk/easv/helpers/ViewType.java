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
    };
    public abstract String getFXMLView();
}
