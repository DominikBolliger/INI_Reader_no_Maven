package controller;

import application.INIReaderApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DialogController {
    @FXML
    private Button btnClose;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblInfo;
    @FXML
    private Label lblInfoDouble;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private VBox vBoxSingleButton;
    @FXML
    private VBox vBoxDoubleButton;


    public void setTitleLabelText(String text) {
        lblTitle.setText(text);
    }

    public void setInfoLabelText(String text) {
        lblInfo.setText(text);
        lblInfoDouble.setText(text);
    }

    public void setButtonText(String text) {
        btnOk.setText(text);
    }

    public void setVBoxDoubleButtonVisible(boolean isVisibleAndManaged) {
        vBoxDoubleButton.setVisible(isVisibleAndManaged);
        vBoxDoubleButton.setManaged(isVisibleAndManaged);
    }

    public void setVBoxSingleButtonVisible(boolean isVisibleAndManaged) {
        vBoxSingleButton.setVisible(isVisibleAndManaged);
        vBoxSingleButton.setManaged(isVisibleAndManaged);
    }

    public void btnCloseClick() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        Scene mainScene = INIReaderApplication.mainScene;
        stage.close();
        mainScene.getRoot().setEffect(null);
    }

    public void btnSaveClick() throws IOException {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        Scene mainScene = INIReaderApplication.mainScene;
        stage.close();
        mainScene.getRoot().setEffect(null);
        FXMLLoader loader = INIReaderApplication.fxmlMain;
        ((INIReaderController) loader.getController()).saveFile();
    }

    public void btnCancelClick() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        Scene mainScene = INIReaderApplication.mainScene;
        stage.close();
        mainScene.getRoot().setEffect(null);
        FXMLLoader loader = INIReaderApplication.fxmlMain;
        Stage mainStage = (Stage) ((INIReaderController) loader.getController()).getBtnClose().getScene().getWindow();
        mainStage.close();
    }

}
