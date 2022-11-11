package controller;

import application.INIReader;
import application.INIReaderApplication;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ChangeKeyValueController {
    @FXML
    private Button btnClose;
    private INIReader reader;

    @FXML
    protected void btnCloseClick() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        Scene mainScene = INIReaderApplication.mainScene;
        stage.close();
        mainScene.getRoot().setEffect(null);
    }

    public void setReader(INIReader reader) {
        this.reader = reader;
    }
}
