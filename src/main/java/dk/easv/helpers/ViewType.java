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
    ADMIN {
        @Override
        public String getFXMLView(){return "views/admin-view.fxml";}
    };
    public abstract String getFXMLView();
}
