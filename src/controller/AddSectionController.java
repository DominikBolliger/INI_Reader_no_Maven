package controller;

import logic.INIReader;
import application.INIReaderApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Section;

public class AddSectionController {

    @FXML
    private Button btnClose;
    @FXML
    private Button btnAddSection;
    @FXML
    private TextField tfAddSection;
    private INIReader reader;

    /**
     * Gets called before the stage shows
     */
    @FXML
    protected void initialize() {
        btnAddSection.disableProperty().bind(tfAddSection.textProperty().isEmpty());
        Platform.runLater(() -> tfAddSection.requestFocus());
    }

    /**
     * Gets fired when the Button AddKeySection is clicked.
     */
    @FXML
    protected void btnAddSectionClick() {
        Section newSection = reader.addSection(tfAddSection.getText());
        if (newSection != null) {
            Stage stage = (Stage) btnClose.getScene().getWindow();
            Scene mainScene = INIReaderApplication.getMainScene();
            stage.close();
            mainScene.getRoot().setEffect(null);
            reader.getController().getLvSection().getSelectionModel().select(newSection);
            reader.addKeyValueToListView();
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
