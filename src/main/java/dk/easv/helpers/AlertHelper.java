package dk.easv.helpers;

import dk.easv.be.Doc;
import dk.easv.gui.models.interfaces.IDocumentModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ButtonType;
import javafx.scene.control.Alert;
import javafx.stage.Modality;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AlertHelper{
    //TODO add icons to alerts
    private static boolean isAlertShown = false;
    private static IDocumentModel documentModel;
    private static MFXGenericDialog dialogContent;
    private static MFXStageDialog dialog;

    public static void setDocumentModel(IDocumentModel model){
        documentModel = model;
    }

    public static void showDefaultAlert(String content, Alert.AlertType alertType) {

        if (isAlertShown()) {
            return;
        }
            dialogContent = MFXGenericDialogBuilder.build()
                    .setContentText(content)
                    .makeScrollable(true)
                    .get();
            dialog = MFXGenericDialogBuilder.build(dialogContent)
                    .toStageDialogBuilder()
                    .initModality(Modality.APPLICATION_MODAL)
                    .setAlwaysOnTop(true)
                    .setDraggable(true)
                    .setTitle(alertType.name())
                    .get();


            MFXButton confirmButton = new MFXButton("Confirm");
            confirmButton.setButtonType(ButtonType.RAISED);
            MFXButton cancelButton = new MFXButton("Cancel");
            cancelButton.setButtonType(ButtonType.RAISED);
            dialogContent.addActions(
                    Map.entry(confirmButton, event -> {
                        try {
                            List<Doc> oldDocuments = documentModel.getOldDocuments();
                            for (Doc oldDocument : oldDocuments) {
                                documentModel.deleteDocument(oldDocument.getId());
                                documentModel.setObsAllDocuments();
                            }
                            dialog.close();
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    }),
                    Map.entry(cancelButton, event -> dialog.close())
            );

            dialogContent.setMaxSize(400, 200);

        switch (alertType) {
            case INFORMATION -> openInfo();
            case WARNING -> openWarning();
            case ERROR -> openError();
            default -> openGeneric();
        }
        isAlertShown = true;
    }

    private static void openInfo() {
//        MFXFontIcon infoIcon = new MFXFontIcon("fas-circle-info", 18);
//        dialogContent.setHeaderIcon(infoIcon);
        dialogContent.setHeaderText("Information");
        convertDialogTo("mfx-info-dialog");
        dialog.showDialog();
    }

    private static void openWarning() {
//        MFXFontIcon warnIcon = new MFXFontIcon("fas-circle-exclamation", 18);
//        dialogContent.setHeaderIcon(warnIcon);
        dialogContent.setHeaderText("Warning!");
        convertDialogTo("mfx-warn-dialog");
        dialog.showDialog();
    }


    private static void openError() {
//        MFXFontIcon errorIcon = new MFXFontIcon("fas-circle-xmark", 18);
//        dialogContent.setHeaderIcon(errorIcon);
        dialogContent.setHeaderText("Error");
        convertDialogTo("mfx-error-dialog");
        dialog.showDialog();
    }


    private static void openGeneric() {
        dialogContent.setHeaderIcon(null);
        dialogContent.setHeaderText("");
        convertDialogTo(null);
        dialog.showDialog();
    }

    private static void convertDialogTo(String styleClass) {
        dialogContent.getStyleClass().removeIf(
                s -> s.equals("mfx-info-dialog") || s.equals("mfx-warn-dialog") || s.equals("mfx-error-dialog")
        );

        if (styleClass != null)
            dialogContent.getStyleClass().add(styleClass);
    }
    public static boolean isAlertShown() {
        return isAlertShown;
    }

    public static void resetIsAlertShown() {
        isAlertShown = false;
    }
}
