package dk.easv.gui.models;

import dk.easv.be.Project;
import dk.easv.bll.CRUDLogic;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

public class ProjectModel {

    private CRUDLogic crudLogic = new CRUDLogic();

    public ConcurrentMap<Integer, Project> getCustomerPoject() throws SQLException {
        return crudLogic.getCustomerProject();
    }

}
