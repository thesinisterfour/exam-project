package dk.easv.helpers;

public enum ViewType {
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

    WORKER_CARD{
        @Override
        public String getFXMLView() {
            return "views/HboxCard.fxml";
        }
    },

    LOGIN {
        @Override
        public String getFXMLView() {
            return "views/login-view.fxml";
        }
    },
    CUSTOMERS{
        @Override
        public String getFXMLView() {
            return "views/customers-view.fxml";
        }
    }
    ,
    CREATE_CUSTOMERS{
        @Override
        public String getFXMLView() {
            return "views/add-customer-view.fxml";
        }
    },
    HBOXCARD {
        @Override
        public String getFXMLView() {
            return "views/HboxCard.fxml";
        }
    },

    CARD{
        @Override
        public String getFXMLView() {
            return "views/Card.fxml";
        }
    },
    PROJECTCARD{
        @Override
        public String getFXMLView(){
            return "views/ProjectCard.fxml";
        }
    }
    , USERS_VIEW{
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
