package application;

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
    private static Scene mainScene;
    private static FXMLLoader fxmlMain;
    private static Stage secondStage;

    /**
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws IOException
     */
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

    /**
     * Sets any stage given to the center of the Screen
     *
     * @param stage the Stage to center
     */
    public static void setStageCenter(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    /**
     * Makes it possible to move the Stages by clicking and dragging the root node
     *
     * @param root         The root node
     * @param primaryStage The stage
     */
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

    /**
     * Getter's and Setter's'
     **/

    public static Scene getMainScene() {
        return mainScene;
    }

    public static FXMLLoader getFxmlMain() {
        return fxmlMain;
    }

    public static Stage getSecondStage() {
        return secondStage;
    }
}