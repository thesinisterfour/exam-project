package dk.easv.gui.controllers.tasks;

import dk.easv.gui.models.CustomerModel;
import dk.easv.gui.models.interfaces.ICustomerModel;
import javafx.concurrent.Task;

public class LoadCustomerModelTask extends Task<ICustomerModel> {
    @Override
    protected ICustomerModel call() throws Exception {
        return new CustomerModel();
    }
}
