package controller;

import application.INIReaderApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import application.INIReader;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import model.Section;
import model.SectionData;

import java.io.IOException;

import static application.INIReaderApplication.mainScene;

public class INIReaderController {
    private static INIReader reader;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnMinimizeWindow;
    @FXML
    private Button btnAddSection;
    @FXML
    private Button btnResize;
    @FXML
    private Button btnDeleteSection;
    @FXML
    private Button btnAddKeyValue;
    @FXML
    private Button btnDeleteKeyValue;
    @FXML
    private ListView lvSection;
    @FXML
    private ListView lvKeyValue;

    @FXML
    protected void initialize() {
        createBindings();
        createListViewCellFactory();
        reader = new INIReader(this);
        reader.start();
    }

    private void createBindings() {
        btnAddKeyValue.disableProperty().bind(lvSection.getSelectionModel().selectedItemProperty().isNull());
        btnDeleteKeyValue.disableProperty().bind(lvKeyValue.getSelectionModel().selectedItemProperty().isNull());
        btnDeleteSection.disableProperty().bind(lvSection.getSelectionModel().selectedItemProperty().isNull());
    }

    @FXML
    protected void btnCloseClick() {
        if (reader.areFilesDifferent()){
            reader.saveFile();
        }
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void btnMinimizeWindowClick() {
        Stage stage = (Stage) btnMinimizeWindow.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    protected void btnAddSectionClick() throws IOException {
        Scene mainScene = INIReaderApplication.mainScene;
        Stage changeStage = INIReaderApplication.secondStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddSection-view.fxml"));
        Scene addSectionScene = new Scene(loader.load());
        ((AddSectionController) loader.getController()).setReader(reader);
        addSectionScene.setFill(Color.TRANSPARENT);
        changeStage.setScene(addSectionScene);
        changeStage.show();

        INIReaderApplication.makeWindowMoveable(addSectionScene.getRoot(), changeStage);
        INIReaderApplication.setStageCenter(changeStage);
        mainScene.getRoot().setEffect(new BoxBlur(5, 10, 10));
    }

    @FXML
    protected void btnDeleteSectionClick() {
        reader.deleteSection();
    }

    @FXML
    public void btnAddKeyValueClick() throws IOException {
        Scene mainScene = INIReaderApplication.mainScene;
        Stage changeStage = INIReaderApplication.secondStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddKeyValue-view.fxml"));
        Scene addKeyValueScene = new Scene(loader.load());
        addKeyValueScene.setFill(Color.TRANSPARENT);

        ((AddKeyValueController) loader.getController()).setReader(reader);

        changeStage.setScene(addKeyValueScene);
        changeStage.show();

        INIReaderApplication.makeWindowMoveable(addKeyValueScene.getRoot(), changeStage);
        ;
        INIReaderApplication.setStageCenter(changeStage);
        mainScene.getRoot().setEffect(new BoxBlur(5, 10, 10));
    }

    @FXML
    public void btnDeleteKeyValueClick() {
        reader.deleteKeyValue();
    }

    @FXML
    protected void lvSectionKeyPressed() {
        reader.addKeyValueToListView();
    }

    @FXML
    public void lvSectionClick(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {
            Scene mainScene = INIReaderApplication.mainScene;
            Stage changeStage = INIReaderApplication.secondStage;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ChangeSection-view.fxml"));
            Scene changeSectionScene = new Scene(loader.load());
            changeSectionScene.setFill(Color.TRANSPARENT);

            ((ChangeSectionController) loader.getController()).setSectionTextFieldText((Section) lvSection.getSelectionModel().getSelectedItem());
            ((ChangeSectionController) loader.getController()).setReader(reader);

            changeStage.setScene(changeSectionScene);
            changeStage.show();

            INIReaderApplication.makeWindowMoveable(changeSectionScene.getRoot(), changeStage);
            ;
            INIReaderApplication.setStageCenter(changeStage);
            mainScene.getRoot().setEffect(new BoxBlur(5, 10, 10));
        } else {
            reader.addKeyValueToListView();
        }
    }

    @FXML
    private void lvKeyValueClick(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {
            Scene mainScene = INIReaderApplication.mainScene;
            Stage changeStage = INIReaderApplication.secondStage;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ChangeKeyValue-view.fxml"));
            Scene changeKeyValueScene = new Scene(loader.load());
            changeKeyValueScene.setFill(Color.TRANSPARENT);

            ((ChangeKeyValueController) loader.getController()).setKeyValueTextFieldText((SectionData) lvKeyValue.getSelectionModel().getSelectedItem());
            ((ChangeKeyValueController) loader.getController()).setReader(reader);

            changeStage.setScene(changeKeyValueScene);
            changeStage.show();

            INIReaderApplication.makeWindowMoveable(changeKeyValueScene.getRoot(), changeStage);
            INIReaderApplication.setStageCenter(changeStage);
            mainScene.getRoot().setEffect(new BoxBlur(5, 10, 10));
        }
    }

    private void createListViewCellFactory() {
        lvSection.setCellFactory(cell -> new ListCell<Section>() {
            @Override
            protected void updateItem(Section data, boolean empty) {
                super.updateItem(data, empty);
                if (data == null || empty) {
                    setText(null);
                } else {
                    setText(data.getSectionName().substring(1, data.getSectionName().length() - 1));
                }
            }
        });
        lvKeyValue.setCellFactory(cell -> new ListCell<SectionData>() {
            final Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(SectionData data, boolean empty) {
                super.updateItem(data, empty);
                if (data == null || empty) {
                    setText(null);
                    setTooltip(null);

                } else {
                    setText(data.getKey() + "=" + data.getValue());
                    if (!data.getComment().equals("")) {
                        tooltip.setText(data.getComment().replaceAll("#", ""));
                        setTooltip(tooltip);
                    } else {
                        tooltip.setText("No Comment");
                        setTooltip(tooltip);
                    }
                }
            }
        });
    }

    public ListView getLvSection() {
        return lvSection;
    }

    public ListView getLvKeyValue() {
        return lvKeyValue;
    }

    public void btnResizeClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnResize.getScene().getWindow();
        if (stage.isMaximized()) {
            stage.setMaximized(false);
        } else {
            stage.setMaximized(true);
        }
    }

    @FXML
    public void menuBarSaveClick(ActionEvent actionEvent) {
        if (reader.areFilesDifferent()) {
            reader.saveFile();
        }
    }
}