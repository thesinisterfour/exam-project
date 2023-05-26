package dk.easv.gui.controllers.helpers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.dialogs.MFXDialogs;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ButtonType;
import javafx.scene.control.Alert;

import java.util.Map;

public class AlertHelper {
    private final MFXStageDialog dialog;
    private MFXGenericDialog dialogContent;
    private boolean confirmed;

    public AlertHelper(String content, Alert.AlertType alertType) {

        confirmed = false;

        switch (alertType) {
            case INFORMATION -> createInfo();
            case WARNING -> createWarning();
            case ERROR -> createError();
            default -> createGeneric();
        }

        dialogContent.setHeaderText(content);
        dialog = new MFXStageDialog(dialogContent);
        dialog.setAlwaysOnTop(true);
        dialog.setDraggable(true);
        dialogContent.setShowClose(false);
        dialogContent.setShowMinimize(false);
        dialogContent.setShowAlwaysOnTop(false);


        MFXButton confirmButton = new MFXButton("Confirm");
        confirmButton.setButtonType(ButtonType.RAISED);
        MFXButton cancelButton = new MFXButton("Cancel");
        cancelButton.setButtonType(ButtonType.RAISED);
        dialogContent.addActions(
                Map.entry(confirmButton, event -> {
                    confirmed = true;
                    dialog.close();
                }),
                Map.entry(cancelButton, event -> dialog.close())
        );


        dialogContent.setMaxSize(400, 200);


    }

    public AlertHelper(String content, Exception e) {
        this(content, Alert.AlertType.ERROR);
        dialogContent.setContentText(e.getMessage());
        dialog.setResizable(true);
    }

    /**
     * Shows dialog and returns a boolean after a button is pressed
     *
     * @return true if confirm button was pressed
     */
    public boolean showAndWait() {
        dialog.showAndWait();
        return confirmed;
    }

    private void createInfo() {
        dialogContent = MFXDialogs.info().get();
        dialogContent.setHeaderText("Information");
        convertDialogTo("mfx-info-dialog");
    }

    private void createWarning() {
        dialogContent = MFXDialogs.warn().get();
        dialogContent.setHeaderText("Warning!");
        convertDialogTo("mfx-warn-dialog");
    }


    private void createError() {
        dialogContent = MFXDialogs.error().get();
        dialogContent.setHeaderText("Error");
        convertDialogTo("mfx-error-dialog");
    }


    private void createGeneric() {
        dialogContent = MFXGenericDialogBuilder.build().get();
        dialogContent.setHeaderIcon(null);
        dialogContent.setHeaderText("");
        convertDialogTo(null);
    }

    private void convertDialogTo(String styleClass) {
        dialogContent.getStyleClass().removeIf(
                s -> s.equals("mfx-info-dialog") || s.equals("mfx-warn-dialog") || s.equals("mfx-error-dialog")
        );
        if (styleClass != null)
            dialogContent.getStyleClass().add(styleClass);
    }
}
