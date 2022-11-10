package controller;

import application.INIReaderApplication;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import application.INIReader;
import javafx.stage.Window;
import model.Section;
import model.SectionData;

public class INIReaderController {

    private static INIReader reader;

    @FXML
    private Button btnClose;
    @FXML
    private Button btnMinimizeWindow;
    @FXML
    private Button btnAddKeyValue;
    @FXML
    private Button btnDeleteKeyValue;
    @FXML
    private ListView lvSection;
    @FXML
    private ListView lvKeyValue;

    public static INIReader createIni(INIReaderController controller) {
        reader = new INIReader("resource/data/opms.ini", controller);

        return reader;
    }

    @FXML
    protected void initialize() {
        createListViewCellFactory();
        createBindings();
        reader.start();
    }

    private void createBindings() {
        btnAddKeyValue.disableProperty().bind(lvKeyValue.getSelectionModel().selectedItemProperty().isNotNull());
        btnDeleteKeyValue.disableProperty().bind(lvKeyValue.getSelectionModel().selectedItemProperty().isNotNull());

    }

    @FXML
    protected void btnCloseClick() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void btnMinimizeWindowClick() {
        Stage stage = (Stage) btnMinimizeWindow.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    protected void lvSectionSetOnMousePressed(){
        reader.addKeyValueToListView();
    }

    @FXML
    private void createListViewCellFactory() {
        lvSection.setCellFactory(cell -> new ListCell<Section>() {
            @Override
            protected void updateItem(Section data, boolean empty) {
                super.updateItem(data, empty);
                if (data == null || empty) {
                    setText(null);
                } else {
                    setOnMouseClicked(event -> {
                        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                            Stage primaryStage = (Stage)lvSection.getScene().getWindow();
                            Stage changeStage = INIReaderApplication.changeStage;
                            changeStage.setScene(INIReaderApplication.addSectionScene);
                            changeStage.initModality(Modality.WINDOW_MODAL);
                            changeStage.initOwner(primaryStage);
                            changeStage.show();
                        }
                    });
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

    public void setLvSection(ListView lvSection) {
        this.lvSection = lvSection;
    }

    public ListView getLvKeyValue() {
        return lvKeyValue;
    }

    public void setLvKeyValue(ListView lvKeyValue) {
        this.lvKeyValue = lvKeyValue;
    }

    public void setReader(INIReader reader){
        this.reader = reader;
    }

}