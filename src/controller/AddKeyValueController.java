package controller;

import application.INIReader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AddKeyValueController {

    @FXML
    private Button btnClose;
    private INIReader reader;

    @FXML
    protected void btnCloseClick() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void setReader(INIReader reader) {
        this.reader = reader;
    }
}
