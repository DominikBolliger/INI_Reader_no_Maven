package controller;

import application.INIReader;
import application.INIReaderApplication;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.script.Bindings;

public class AddSectionController {

    @FXML
    private Button btnClose;
    @FXML
    private Button btnAddSection;
    @FXML
    private TextField tfAddSection;
    private INIReader reader;

    @FXML
    protected void initialize() {
        btnAddSection.disableProperty().bind(tfAddSection.textProperty().isEmpty());
    }

    @FXML
    protected void btnAddSectionClick() {
        reader.addSection(tfAddSection.getText());
        Stage stage = (Stage) btnClose.getScene().getWindow();
        Scene mainScene = INIReaderApplication.mainScene;
        stage.close();
        mainScene.getRoot().setEffect(null);
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
}
