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

public class ChangeSectionController {

    @FXML
    private Button btnClose;
    @FXML
    private Button btnUpdateSection;
    @FXML
    protected TextField tfSectionName;
    private INIReader reader;

    /**
     * Gets called before the stage shows
     */
    @FXML
    protected void initialize() {
        btnUpdateSection.disableProperty().bind(tfSectionName.textProperty().isEmpty());
        Platform.runLater(() -> tfSectionName.requestFocus());
    }

    /**
     * Gets fired when the Update Button is clicked.
     */
    public void btnUpdateSectionClick() {
        Section changedSection = reader.updateSection(tfSectionName.getText());
        if (changedSection != null) {
            Stage stage = (Stage) btnClose.getScene().getWindow();
            Scene mainScene = INIReaderApplication.getMainScene();
            stage.close();
            mainScene.getRoot().setEffect(null);
            reader.getController().getLvSection().getSelectionModel().select(changedSection);
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
    public void setSectionTextFieldText(Section section) {
        tfSectionName.setText(section.getSectionName());
    }

    public void setReader(INIReader reader) {
        this.reader = reader;
    }
}
