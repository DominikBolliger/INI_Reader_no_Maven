package controller;

import application.INIReader;
import application.INIReaderApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    @FXML
    protected void initialize() {
        btnUpdateSection.disableProperty().bind(tfSectionName.textProperty().isEmpty());
        Platform.runLater(() -> tfSectionName.requestFocus());
    }

    @FXML
    protected void btnCloseClick() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        Scene mainScene = INIReaderApplication.mainScene;
        stage.close();
        mainScene.getRoot().setEffect(null);
    }

    public void setSectionTextFieldText(Section section) {
        tfSectionName.setText(section.getSectionName());
    }

    public void setReader(INIReader reader) {
        this.reader = reader;
    }

    public void btnUpdateSectionClick() {
        Section changedSection = reader.updateSection(tfSectionName.getText());
        if (changedSection != null) {
            Stage stage = (Stage) btnClose.getScene().getWindow();
            Scene mainScene = INIReaderApplication.mainScene;
            stage.close();
            mainScene.getRoot().setEffect(null);
            reader.getController().getLvSection().getSelectionModel().select(changedSection);
        }
    }
}
