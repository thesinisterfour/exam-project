package dk.easv.gui.models.tasks;

import dk.easv.bll.DocumentLogic;
import dk.easv.bll.IDocumentLogic;
import javafx.concurrent.Task;

import java.sql.SQLException;

public class RetrieveContentTask extends Task<Object> {
    private final IDocumentLogic documentLogic = new DocumentLogic();
    private final int id;

    public RetrieveContentTask(int id) {
        this.id = id;
    }

    public Object getContent(Integer contentId) throws SQLException {
        return documentLogic.getContent(contentId);
    }

    @Override
    protected Object call() throws Exception {
        return this.getContent(id);
    }
}
