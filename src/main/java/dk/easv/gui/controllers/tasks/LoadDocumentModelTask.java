package dk.easv.gui.controllers.tasks;

import dk.easv.gui.models.DocumentModel;
import dk.easv.gui.models.interfaces.IDocumentModel;
import javafx.concurrent.Task;

public class LoadDocumentModelTask extends Task<IDocumentModel> {
    @Override
    protected IDocumentModel call() throws Exception {
        return DocumentModel.getInstance();
    }
}
