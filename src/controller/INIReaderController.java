package controller;

import application.INIReaderApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import logic.INIReader;
import model.Section;
import model.SectionData;
import util.Util;

import java.io.IOException;

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

    /**
     * Gets called before the stage shows
     */
    @FXML
    protected void initialize() {
        createBindings();
        createListViewCellFactory();
        reader = new INIReader(this);
        reader.start();
    }

    /**
     * Creates all bindings for this stage
     */
    private void createBindings() {
        btnAddKeyValue.disableProperty().bind(lvSection.getSelectionModel().selectedItemProperty().isNull());
        btnDeleteKeyValue.disableProperty().bind(lvKeyValue.getSelectionModel().selectedItemProperty().isNull());
        btnDeleteSection.disableProperty().bind(lvSection.getSelectionModel().selectedItemProperty().isNull());
    }

    /**
     * Saves the File
     *
     * @throws IOException
     */
    public void saveFile() throws IOException {
        Scene mainScene = INIReaderApplication.getMainScene();
        Stage changeStage = INIReaderApplication.getSecondStage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dialog-view.fxml"));
        Scene dialogScene = new Scene(loader.load());
        dialogScene.setFill(Color.TRANSPARENT);
        changeStage.setScene(dialogScene);
        INIReaderApplication.makeWindowMoveable(dialogScene.getRoot(), changeStage);
        mainScene.getRoot().setEffect(Util.BLURRFACTOR);
        ((DialogController) loader.getController()).setVBoxDoubleButtonVisible(false);
        ((DialogController) loader.getController()).setVBoxSingleButtonVisible(true);
        ((DialogController) loader.getController()).setInfoLabelText("The files has been saved.\nThanks and Good bye..");
        ((DialogController) loader.getController()).setTitleLabelText("Saved changes");
        ((DialogController) loader.getController()).setButtonText("Ok");
        INIReaderApplication.setStageCenter(changeStage);
        reader.saveFile();
        changeStage.showAndWait();
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the Keyboard input for the Section Listview.
     */
    @FXML
    protected void lvSectionKeyPressed() {
        reader.addKeyValueToListView();
    }

    /**
     * To show a different comment for every Key+Value this CellFactory has been created
     */
    private void createListViewCellFactory() {
        lvSection.setCellFactory(cell -> new ListCell<Section>() {
            @Override
            protected void updateItem(Section data, boolean empty) {
                super.updateItem(data, empty);
                if (data == null || empty) {
                    setText(null);
                } else {
                    setText(data.getSectionName());
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
                        tooltip.setText(data.getComment());
                        setTooltip(tooltip);
                    } else {
                        tooltip.setText("No Comment");
                        setTooltip(tooltip);
                    }
                }
            }
        });
    }

    /**
     * Handles what happens when the Listview is clicked once and twice
     *
     * @param mouseEvent
     * @throws IOException
     */
    @FXML
    public void lvSectionClick(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {
            Scene mainScene = INIReaderApplication.getMainScene();
            Stage changeStage = INIReaderApplication.getSecondStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ChangeSection-view.fxml"));
            Scene changeSectionScene = new Scene(loader.load());
            changeSectionScene.setFill(Color.TRANSPARENT);

            ((ChangeSectionController) loader.getController()).setSectionTextFieldText((Section) lvSection.getSelectionModel().getSelectedItem());
            ((ChangeSectionController) loader.getController()).setReader(reader);

            changeStage.setScene(changeSectionScene);
            changeStage.show();

            INIReaderApplication.makeWindowMoveable(changeSectionScene.getRoot(), changeStage);
            INIReaderApplication.setStageCenter(changeStage);
            mainScene.getRoot().setEffect(Util.BLURRFACTOR);
        } else {
            reader.addKeyValueToListView();
        }
    }

    /**
     * Handles what happens when the Listview is clicked twice
     *
     * @param mouseEvent
     * @throws IOException
     */
    @FXML
    private void lvKeyValueClick(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {
            Scene mainScene = INIReaderApplication.getMainScene();
            Stage changeStage = INIReaderApplication.getSecondStage();
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

    /**
     * Minimizes the window to the taskbar
     */
    @FXML
    protected void btnMinimizeWindowClick() {
        Stage stage = (Stage) btnMinimizeWindow.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Opens the Add Section Scene
     *
     * @throws IOException
     */
    @FXML
    protected void btnAddSectionClick() throws IOException {
        Scene mainScene = INIReaderApplication.getMainScene();
        Stage changeStage = INIReaderApplication.getSecondStage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddSection-view.fxml"));
        Scene addSectionScene = new Scene(loader.load());
        ((AddSectionController) loader.getController()).setReader(reader);
        addSectionScene.setFill(Color.TRANSPARENT);
        changeStage.setScene(addSectionScene);
        changeStage.show();

        INIReaderApplication.makeWindowMoveable(addSectionScene.getRoot(), changeStage);
        INIReaderApplication.setStageCenter(changeStage);
        mainScene.getRoot().setEffect(Util.BLURRFACTOR);
    }

    /**
     * Deletes a Section
     *
     * @throws IOException
     */
    @FXML
    protected void btnDeleteSectionClick() throws IOException {
        reader.deleteSection();
        if (Section.getSections().size() != 0) {
            reader.getController().getLvSection().getSelectionModel().select(Section.getSections().get(0));
            reader.addKeyValueToListView();
        }
    }

    /**
     * Opens the Add Key Value Scene
     *
     * @throws IOException
     */
    @FXML
    public void btnAddKeyValueClick() throws IOException {
        Scene mainScene = INIReaderApplication.getMainScene();
        Stage changeStage = INIReaderApplication.getSecondStage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddKeyValue-view.fxml"));
        Scene addKeyValueScene = new Scene(loader.load());
        addKeyValueScene.setFill(Color.TRANSPARENT);

        ((AddKeyValueController) loader.getController()).setReader(reader);

        changeStage.setScene(addKeyValueScene);
        changeStage.show();

        INIReaderApplication.makeWindowMoveable(addKeyValueScene.getRoot(), changeStage);
        INIReaderApplication.setStageCenter(changeStage);
        mainScene.getRoot().setEffect(Util.BLURRFACTOR);
    }

    /**
     * Deletes a Key+Value
     */
    @FXML
    public void btnDeleteKeyValueClick() {
        reader.deleteKeyValue();
    }

    /**
     * Resize's the stage to either normal or Fullscreen
     */
    public void btnResizeClick() {
        Stage stage = (Stage) btnResize.getScene().getWindow();
        if (stage.isMaximized()) {
            stage.setMaximized(false);
        } else {
            stage.setMaximized(true);
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(primaryScreenBounds.getMinX());
            stage.setY(primaryScreenBounds.getMinY());

            stage.setMaxWidth(primaryScreenBounds.getWidth());
            stage.setMinWidth(primaryScreenBounds.getWidth());

            stage.setMaxHeight(primaryScreenBounds.getHeight());
            stage.setMinHeight(primaryScreenBounds.getHeight());
        }
    }

    /**
     * Handles what happens when the save button from the Menubar is clicked
     *
     * @throws IOException
     */
    @FXML
    public void menuBarSaveClick() throws IOException {

        Scene mainScene = INIReaderApplication.getMainScene();
        Stage changeStage = INIReaderApplication.getSecondStage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dialog-view.fxml"));
        Scene dialogScene = new Scene(loader.load());
        dialogScene.setFill(Color.TRANSPARENT);
        changeStage.setScene(dialogScene);


        INIReaderApplication.makeWindowMoveable(dialogScene.getRoot(), changeStage);
        mainScene.getRoot().setEffect(Util.BLURRFACTOR);

        if (reader.areFilesDifferent()) {
            reader.saveFile();
            ((DialogController) loader.getController()).setTitleLabelText("File saved");
            ((DialogController) loader.getController()).setInfoLabelText("The file has been successfully saved..");
            ((DialogController) loader.getController()).setButtonText("Ok");
            ((DialogController) loader.getController()).setVBoxDoubleButtonVisible(false);
            ((DialogController) loader.getController()).setVBoxSingleButtonVisible(true);
            changeStage.show();
            INIReaderApplication.setStageCenter(changeStage);
        } else {
            ((DialogController) loader.getController()).setTitleLabelText("No changes");
            ((DialogController) loader.getController()).setInfoLabelText("The file has not been changed!");
            ((DialogController) loader.getController()).setButtonText("Ok");
            ((DialogController) loader.getController()).setVBoxDoubleButtonVisible(false);
            ((DialogController) loader.getController()).setVBoxSingleButtonVisible(true);
            changeStage.show();
            INIReaderApplication.setStageCenter(changeStage);
        }
    }

    /**
     * Closes the stage
     *
     * @throws IOException
     */
    @FXML
    protected void btnCloseClick() throws IOException {
        if (reader.areFilesDifferent()) {
            Scene mainScene = INIReaderApplication.getMainScene();
            Stage changeStage = INIReaderApplication.getSecondStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dialog-view.fxml"));
            Scene dialogScene = new Scene(loader.load());
            dialogScene.setFill(Color.TRANSPARENT);
            changeStage.setScene(dialogScene);
            INIReaderApplication.makeWindowMoveable(dialogScene.getRoot(), changeStage);
            mainScene.getRoot().setEffect(Util.BLURRFACTOR);
            ((DialogController) loader.getController()).setVBoxDoubleButtonVisible(true);
            ((DialogController) loader.getController()).setVBoxSingleButtonVisible(false);
            ((DialogController) loader.getController()).setInfoLabelText("The files has been changed.\nDo you want to save the changes?");
            ((DialogController) loader.getController()).setTitleLabelText("Changes detected");
            changeStage.show();
            INIReaderApplication.setStageCenter(changeStage);
        } else {
            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Getter's and Setter's'
     **/
    public Button getBtnClose() {
        return btnClose;
    }

    public ListView getLvSection() {
        return lvSection;
    }

    public ListView getLvKeyValue() {
        return lvKeyValue;
    }
}