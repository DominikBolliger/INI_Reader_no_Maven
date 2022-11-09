package controller;

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

    @FXML
    protected void initialize() {
        btnAddSection.disableProperty().bind(tfAddSection.textProperty().isEmpty());
    }

    @FXML
    protected void btnCloseClick() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

}
