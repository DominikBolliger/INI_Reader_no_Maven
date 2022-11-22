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

    private static double xOffset = 0;
    private static double yOffset = 0;
    public static Scene mainScene;

    public static FXMLLoader fxmlMain;
    public static Stage secondStage;
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        fxmlMain = new FXMLLoader(INIReaderApplication.class.getResource("/view/INIReader-view.fxml"));
        mainScene = new Scene(fxmlMain.load());
        mainScene.setFill(Color.TRANSPARENT);

        secondStage = new Stage();
        secondStage.initModality(Modality.WINDOW_MODAL);
        secondStage.initOwner(primaryStage);
        secondStage.initStyle(StageStyle.TRANSPARENT);

        makeWindowMoveable(mainScene.getRoot(), primaryStage);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(mainScene);
        primaryStage.show();

        setStageCenter(primaryStage);
    }

    public static void setStageCenter(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    public static void makeWindowMoveable(Node root, Stage primaryStage) {
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