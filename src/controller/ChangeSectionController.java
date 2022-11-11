package controller;

import application.INIReader;
import application.INIReaderApplication;
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
    private INIReader reader;
    @FXML
    protected TextField tfSectionName;

    @FXML
    protected void btnCloseClick() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        Scene mainScene = INIReaderApplication.mainScene;
        stage.close();
        mainScene.getRoot().setEffect(null);
    }

    public void setSectionTextFieldText(Section sectionName){
        tfSectionName.setText(sectionName.getSectionName());
    }

}
