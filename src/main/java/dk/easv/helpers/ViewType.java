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
    }
    ,


    USERS_VIEW{
        @Override
        public String getFXMLView() {
            return "views/worker-users-view.fxml";
        }
    };
    public abstract String getFXMLView();
}
