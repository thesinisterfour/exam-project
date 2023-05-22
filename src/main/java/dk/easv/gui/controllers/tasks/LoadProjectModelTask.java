package dk.easv.gui.controllers.tasks;

import dk.easv.gui.models.ProjectModel;
import dk.easv.gui.models.interfaces.IProjectModel;
import javafx.concurrent.Task;

public class LoadProjectModelTask extends Task<IProjectModel> {
    @Override
    protected IProjectModel call() throws Exception {
        return ProjectModel.getInstance();
    }
}
