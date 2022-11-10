package controller;

import application.INIReader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    protected void btnCloseClick() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void setReader(INIReader reader) {
            this.reader = reader;
    }
}
