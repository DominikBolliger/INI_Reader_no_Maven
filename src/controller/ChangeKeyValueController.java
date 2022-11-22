package controller;

import application.INIReader;
import application.INIReaderApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Section;
import model.SectionData;

public class ChangeKeyValueController {
    @FXML
    private Button btnClose;
    @FXML
    private Button btnChange;
    @FXML
    private TextField tfKey;
    @FXML
    private TextField tfValue;
    @FXML
    private TextArea tfaComment;
    private INIReader reader;

    @FXML
    protected void initialize(){
        Platform.runLater(() -> tfKey.requestFocus());
    }

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

    public void setKeyValueTextFieldText(SectionData selectedItem) {
        tfKey.setText(selectedItem.getKey());
        tfValue.setText(selectedItem.getValue());
        tfaComment.setText(selectedItem.getComment());
    }

    public void btnChangeClick() {
        reader.updateKeyValue(tfKey.getText(), tfValue.getText(), tfaComment.getText());
        Stage stage = (Stage) btnClose.getScene().getWindow();
        Scene mainScene = INIReaderApplication.mainScene;
        stage.close();
        mainScene.getRoot().setEffect(null);
    }
}
