package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class INIReaderApplication extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    private Scene mainScene;
    public static Scene addSectionScene;
    public static Scene addKeyValueScene;
    public static Scene changeSectionScene;
    public static Scene changeKeyValueScene;
    public static Stage changeStage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlMain = new FXMLLoader(INIReaderApplication.class.getResource("view/INIReader-view.fxml"));
        FXMLLoader fxmlAddSection = new FXMLLoader(INIReaderApplication.class.getResource("view/AddSection-view.fxml"));
        FXMLLoader fxmlAddKeyValue = new FXMLLoader(INIReaderApplication.class.getResource("view/AddKeyValue-view.fxml"));
        FXMLLoader fxmlChangeSection = new FXMLLoader(INIReaderApplication.class.getResource("view/ChangeSection-view.fxml"));
        FXMLLoader fxmlChangeKeyValue = new FXMLLoader(INIReaderApplication.class.getResource("view/ChangeKeyValue-view.fxml"));

        mainScene = new Scene(fxmlMain.load());
        addSectionScene = new Scene(fxmlAddSection.load());
        addKeyValueScene = new Scene(fxmlAddKeyValue.load());
        changeSectionScene = new Scene(fxmlChangeSection.load());
        changeKeyValueScene = new Scene(fxmlChangeKeyValue.load());
        addSectionScene.setFill(Color.TRANSPARENT);
        addKeyValueScene.setFill(Color.TRANSPARENT);
        changeSectionScene.setFill(Color.TRANSPARENT);
        changeKeyValueScene.setFill(Color.TRANSPARENT);
        mainScene.setFill(Color.TRANSPARENT);

        changeStage = new Stage();
        changeStage.initStyle(StageStyle.TRANSPARENT);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(mainScene);
        stage.show();
        makeWindowMoveable(mainScene.getRoot(), stage);
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

}