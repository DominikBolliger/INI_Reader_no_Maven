package controller;

import application.INIReader;
import application.INIReaderApplication;
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

    @FXML
    protected void initialize() {
        btnAddKeyValue.disableProperty().bind(tfKey.textProperty().isEmpty().or(tfValue.textProperty().isEmpty()));
    }

    @FXML
    protected void btnAddKeyValueClick(){
        reader.addKeyValue(tfKey.getText(), tfValue.getText(), taComment.getText());
        Stage stage = (Stage) btnClose.getScene().getWindow();
        Scene mainScene = INIReaderApplication.mainScene;
        stage.close();
        mainScene.getRoot().setEffect(null);
    }

    public void setReader(INIReader reader) {
        this.reader = reader;
    }

    @FXML
    protected void btnCloseClick() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        Scene mainScene = INIReaderApplication.mainScene;
        stage.close();
        mainScene.getRoot().setEffect(null);
    }

}
