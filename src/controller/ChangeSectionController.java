package controller;

import application.INIReader;
import application.INIReaderApplication;
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
    }

    @FXML
    protected void btnCloseClick() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        Scene mainScene = INIReaderApplication.mainScene;
        stage.close();
        mainScene.getRoot().setEffect(null);
    }

    public void setSectionTextFieldText(Section section) {
        String sectionName = section.getSectionName();
        tfSectionName.setText(sectionName.substring(1, sectionName.length() - 1));
    }

    public void setReader(INIReader reader) {
        this.reader = reader;
    }

    public void btnUpdateSectionClick() {
        reader.updateSection(tfSectionName.getText());
        Stage stage = (Stage) btnClose.getScene().getWindow();
        Scene mainScene = INIReaderApplication.mainScene;
        stage.close();
        mainScene.getRoot().setEffect(null);
    }
}
