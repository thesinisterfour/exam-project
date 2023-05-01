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

    SALESPERSON {
        @Override
        public String getFXMLView() {
            return "views/salesperson-view.fxml";
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
    },


    USERS_VIEW{
        @Override
        public String getFXMLView() {
            return "views/worker-users-view.fxml";
        }
    },

    CREATE_DOCUMENT {
        @Override
        public String getFXMLView() {
            return "views/create-document-view.fxml";
        }
    },
    DOCUMENT {
        @Override
        public String getFXMLView() {
            return "views/document-view.fxml";
        }
    };
    public abstract String getFXMLView();
}
