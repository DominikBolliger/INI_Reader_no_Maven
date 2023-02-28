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

    /**
     * Gets called before the stage shows
     */
    @FXML
    protected void initialize() {
        Platform.runLater(() -> tfKey.requestFocus());
    }

    /**
     * Sets the values from the SectionData Object into the Text fields
     *
     * @param selectedItem The SectionData Object to take the data from
     */
    public void setKeyValueTextFieldText(SectionData selectedItem) {
        tfKey.setText(selectedItem.getKey());
        tfValue.setText(selectedItem.getValue());
        tfaComment.setText(selectedItem.getComment());
    }

    /**
     * Gets fired when the Change Button is clicked.
     */
    public void btnChangeClick() {
        if (!reader.updateKeyValue(tfKey.getText(), tfValue.getText(), tfaComment.getText())) {
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
