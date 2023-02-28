package controller;

import logic.INIReader;
import application.INIReaderApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddKeyValueController {

    @FXML
    private Button btnClose;
    @FXML
    private Button btnAddKeyValue;
    @FXML
    protected TextField tfKey, tfValue;
    @FXML
    protected TextArea taComment;
    private INIReader reader;

    /**
     * Gets called before the stage shows
     */
    @FXML
    protected void initialize() {
        btnAddKeyValue.disableProperty().bind(tfKey.textProperty().isEmpty().or(tfValue.textProperty().isEmpty()));
        Platform.runLater(() -> tfKey.requestFocus());
    }

    /**
     * Gets fired when the Button AddKeyValue is clicked.
     */
    @FXML
    protected void btnAddKeyValueClick() {
        if (!reader.addKeyValue(tfKey.getText(), tfValue.getText(), taComment.getText())) {
            Stage stage = (Stage) btnClose.getScene().getWindow();
            Scene mainScene = INIReaderApplication.getMainScene();
            stage.close();
            mainScene.getRoot().setEffect(null);
        }
    }

    /**
     * Gets fired when the Close Button is clicked.
     */
    @FXML
    protected void btnCloseClick() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        Scene mainScene = INIReaderApplication.getMainScene();
        stage.close();
        mainScene.getRoot().setEffect(null);
    }

    /**
     * Getter's and Setter's'
     **/
    public void setReader(INIReader reader) {
        this.reader = reader;
    }

}
