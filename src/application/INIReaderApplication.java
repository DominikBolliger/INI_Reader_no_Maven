package application;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class INIReaderApplication extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    private static INIReader reader;
    public static Scene mainScene;
    public static Scene addSectionScene;
    public static Scene addKeyValueScene;
    public static Scene changeSectionScene;
    public static Scene changeKeyValueScene;
    public static Stage changeStage;
    public FXMLLoader fxmlMain;
    public FXMLLoader fxmlAddSection;
    public FXMLLoader fxmlAddKeyValue;
    public FXMLLoader fxmlChangeKeyValue;
    public static INIReaderController iniReaderController;
    public AddSectionController addSectionController;
    public ChangeSectionController changeSectionController;
    public AddKeyValueController addKeyValueController;
    public ChangeKeyValueController changeKeyValueController;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {

        loadScenes();
        loadControllers();
        setScenesTransparaent();

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(mainScene);
        primaryStage.show();

        changeStage = new Stage();
        changeStage.initModality(Modality.APPLICATION_MODAL);
        changeStage.initOwner(primaryStage);
        changeStage.initStyle(StageStyle.TRANSPARENT);

        setStageCenter(primaryStage);
        setWindowMovable(primaryStage);
    }

    public static void setStageCenter(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    private void setWindowMovable(Stage stage) {
        makeWindowMoveable(mainScene.getRoot(), stage);
        makeWindowMoveable(addSectionScene.getRoot(), changeStage);
        makeWindowMoveable(changeSectionScene.getRoot(), changeStage);
        makeWindowMoveable(addKeyValueScene.getRoot(), changeStage);
        makeWindowMoveable(changeKeyValueScene.getRoot(), changeStage);
    }

    private void setScenesTransparaent() {
        mainScene.setFill(Color.TRANSPARENT);
        addSectionScene.setFill(Color.TRANSPARENT);
        changeSectionScene.setFill(Color.TRANSPARENT);
        addKeyValueScene.setFill(Color.TRANSPARENT);
        changeKeyValueScene.setFill(Color.TRANSPARENT);
    }

    private void setReaders() {
//        iniReaderController.setReader(reader);
//        addSectionController.setReader(reader);
//        changeSectionController.setReader(reader);
//        addKeyValueController.setReader(reader);
//        changeKeyValueController.setReader(reader);
    }

    private void loadControllers() {
        iniReaderController = fxmlMain.getController();
        addSectionController = fxmlAddSection.getController();

        addKeyValueController = fxmlAddKeyValue.getController();
        changeKeyValueController = fxmlChangeKeyValue.getController();
    }

    private void loadScenes() {
        fxmlMain = new FXMLLoader(INIReaderApplication.class.getResource("/view/INIReader-view.fxml"));
        fxmlAddSection = new FXMLLoader(INIReaderApplication.class.getResource("/view/AddSection-view.fxml"));
        fxmlAddKeyValue = new FXMLLoader(INIReaderApplication.class.getResource("/view/AddKeyValue-view.fxml"));
        fxmlChangeKeyValue = new FXMLLoader(INIReaderApplication.class.getResource("/view/ChangeKeyValue-view.fxml"));

        try {
            mainScene = new Scene(fxmlMain.load());
            addSectionScene = new Scene(fxmlAddSection.load());

            addKeyValueScene = new Scene(fxmlAddKeyValue.load());
            changeKeyValueScene = new Scene(fxmlChangeKeyValue.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void makeWindowMoveable(Node root, Stage primaryStage) {
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
    }

    public static INIReader getReader() {
        return reader;
    }
}