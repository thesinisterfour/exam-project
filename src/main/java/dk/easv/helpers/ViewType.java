package dk.easv.helpers;

public enum ViewType {
    MAIN {
        @Override
        public String getFXMLView() {
            return "views/hello-view.fxml";
        }
    },

    ADMIN {
        @Override
        public String getFXMLView() {
            return "views/admin-view.fxml";
        }
    },

    PROJECT_MANAGER {
        @Override
        public String getFXMLView() {
            return "views/project-manager-view.fxml";
        }
    },

    SALES_PERSON {
        @Override
        public String getFXMLView() {
            return "views/sales-person.fxml";
        }
    },

    TECHNICIAN {
        @Override
        public String getFXMLView() {
            return "views/technician-view.fxml";
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
