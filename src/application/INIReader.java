package application;

import controller.INIReaderController;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import model.Section;
import model.SectionData;
import util.Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class INIReader {


    private List<String> iniFile;
    private List<String> newFile;
    private INIReaderController controller;
    private String path;

    public INIReader(INIReaderController controller) {
        this.path = Util.getIniFilePath();
        this.controller = controller;
    }

    public void start() {
        readFile();
        createObjects();
        addSectionsToListView();
    }

    private void readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            iniFile = new ArrayList<>();
            String line = br.readLine();

            while (line != null) {
                iniFile.add(line);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFile() {
        try {
            FileWriter writer = new FileWriter(path, false);
            for (String line : newFile) {
                writer.write(line);
                writer.write(System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        readFile();
    }

    //TODO: Are files different is not working while using bigger files

    public boolean areFilesDifferent() {
        boolean hasChanged = false;
        createNewFile();
        if (!iniFile.equals(newFile)) {
            hasChanged = true;
        }
        return hasChanged;
    }

    private void createNewFile() {
        newFile = new ArrayList<>();
        List<Section> sections = Section.getSections();
        for (Section section : sections) {
            newFile.add("[" + section.getSectionName() + "]");
            List<SectionData> sectionData = section.getSectionData();
            for (int i = 0; i < sectionData.size(); i++) {
                String comment = sectionData.get(i).getComment();
                if (!comment.equals("")) {
                    String comSplit[] = comment.split(System.lineSeparator());
                    for (String com : comSplit) {
                        newFile.add("#" + com);
                    }
                }
                newFile.add(sectionData.get(i).getKey() + "=" + sectionData.get(i).getValue());
            }
            newFile.add("");
        }
        while (newFile.get(newFile.size() - 1).isEmpty()) {
            newFile.remove(newFile.size() - 1);
        }
    }


    private void createObjects() {
        String section = "";
        String comment = "";
        List<SectionData> dataList = FXCollections.observableArrayList();
        for (String line : iniFile) {
            line = line.trim();
            if (line.startsWith("[") && line.endsWith("]")) {
                if (!section.equals("")) {
                    new Section(section, dataList);
                    dataList = new ArrayList<>();
                }
                section = line.substring(1, line.length() - 1);
            } else if (line.startsWith("#")) {
                comment += line.substring(1) + System.lineSeparator();
            } else if (!line.equals("")) {
                if (!comment.equals("")) {
                    comment = comment.substring(0, comment.length() - 2);
                }
                String[] splitLine = line.split("=");
                try {
                    dataList.add(new SectionData(splitLine[0], splitLine[1], comment));
                } catch (Exception e) {
                    System.out.println(line);
                    System.out.println(e);
                }
                comment = "";
            }
        }
        new Section(section, dataList);
    }

    public void addSectionsToListView() {
        List<Section> sectionList = Section.getSections();

        controller.getLvSection().getItems().clear();

        for (Section section : sectionList) {
            controller.getLvSection().getItems().add(section);
        }
    }

    public void addKeyValueToListView() {
        Section section = (Section) controller.getLvSection().getSelectionModel().getSelectedItem();
        List<SectionData> sectionData = section.getSectionData();
        controller.getLvKeyValue().getItems().clear();
        section.sortSectionData();
        for (SectionData data : sectionData) {
            controller.getLvKeyValue().getItems().add(data);
        }

    }

    public Section addSection(String text) {
        List<SectionData> sectionDataList = new ArrayList<>();
        List<Section> sectionList = Section.getSections();
        Section newSection = null;
        boolean sectionAlreadyExists = false;
        if (text.matches("[a-zA-Z0-9]*")) {
            for (Section section : sectionList) {
                if (section.getSectionName().equalsIgnoreCase(text)) {
                    sectionAlreadyExists = true;
                    break;
                }
            }
            if (!sectionAlreadyExists) {
                newSection = new Section(text.substring(0, 1).toUpperCase() + text.substring(1), sectionDataList);
                addSectionsToListView();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Section Not Unique");
                alert.setHeaderText("The Section " + text + " already exists..");
                alert.setContentText("Please choose a Unique Section Name!");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Not Allowed Characters");
            alert.setHeaderText("The Section Name: " + text + " consists not allowed characters");
            alert.setContentText("Please only use letters or numbers as value");
            alert.showAndWait();
        }
        return newSection;
    }

    public void deleteSection() {
        Section section = (Section) controller.getLvSection().getSelectionModel().getSelectedItem();
        if (Section.getSections().size() > 1) {
            Section.getSections().remove(section);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cant Remove last Section");
            alert.setHeaderText("Last Section cant be removed");
            alert.setContentText("The file cant be empty!");
            alert.showAndWait();
        }
        addSectionsToListView();
    }

    public Section updateSection(String updatetSectionName) {
        Section section = (Section) controller.getLvSection().getSelectionModel().getSelectedItem();
        List<Section> sectionList = Section.getSections();
        boolean sectionAlreadyExists = false;
        if (updatetSectionName.matches("[a-zA-Z0-9]*")) {
            for (Section tempSection : sectionList) {
                if (tempSection.getSectionName().equalsIgnoreCase(updatetSectionName)) {
                    sectionAlreadyExists = true;
                }
            }
            if (!sectionAlreadyExists) {
                section.setSectionName(updatetSectionName.substring(0, 1).toUpperCase() + updatetSectionName.substring(1));
                addSectionsToListView();
            } else {
                if (sectionAlreadyExists) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Section Not Unique");
                    alert.setHeaderText("The Section " + updatetSectionName + " already exists..");
                    alert.setContentText("Please choose a Unique Section Name or change the Value from the original one!");
                    alert.showAndWait();
                    section = null;
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Not Allowed Characters");
            alert.setHeaderText("The Section Name: " + updatetSectionName + " consists not allowed characters");
            alert.setContentText("Please only use letters or numbers as value");
            alert.showAndWait();
        }
        return section;
    }

    public boolean addKeyValue(String key, String value, String comment) {
        Section section = (Section) controller.getLvSection().getSelectionModel().getSelectedItem();
        List<SectionData> sectionDataList = section.getSectionData();
        boolean keyAlreadyExistsOrStringContainsFalseChars = false;
        if (key.matches("[a-zA-Z0-9]*") && value.matches("[a-zA-Z0-9]*") && comment.matches("[a-zA-Z0-9]*")) {
            for (SectionData data : sectionDataList) {
                if (data.getKey().equalsIgnoreCase(key)) {
                    keyAlreadyExistsOrStringContainsFalseChars = true;
                }
            }
            if (!keyAlreadyExistsOrStringContainsFalseChars) {
                section.getSectionData().add(new SectionData(key.substring(0, 1).toUpperCase() + key.substring(1), value, comment));
                section.sortSectionData();
                addKeyValueToListView();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Key Not Unique");
                alert.setHeaderText("The Key " + key + " already exists..");
                alert.setContentText("Please choose a Unique Key Name!");
                alert.showAndWait();
            }
        } else {
            keyAlreadyExistsOrStringContainsFalseChars = true;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Not Allowed Characters");
            alert.setHeaderText("One of your Values consists not allowed characters");
            alert.setContentText("Please only use letters or numbers as values");
            alert.showAndWait();
        }
        return keyAlreadyExistsOrStringContainsFalseChars;
    }

    public void deleteKeyValue() {
        Section section = (Section) controller.getLvSection().getSelectionModel().getSelectedItem();
        SectionData sectionData = (SectionData) controller.getLvKeyValue().getSelectionModel().getSelectedItem();
        section.getSectionData().remove(sectionData);
        addKeyValueToListView();
    }

    public boolean updateKeyValue(String key, String value, String comment) {
        Section section = (Section) controller.getLvSection().getSelectionModel().getSelectedItem();
        SectionData sectionData = (SectionData) controller.getLvKeyValue().getSelectionModel().getSelectedItem();
        List<SectionData> sectionDataList = section.getSectionData();
        boolean keyAlreadyExistsOrStringContainsFalseChars = false;
        if (key.matches("[a-zA-Z0-9]*") && value.matches("[a-zA-Z0-9]*") && comment.matches("[a-zA-Z0-9]*")) {
            for (SectionData data : sectionDataList) {
                if (data.getKey().equalsIgnoreCase(key)) {
                    keyAlreadyExistsOrStringContainsFalseChars = true;
                }
            }
            if (!keyAlreadyExistsOrStringContainsFalseChars) {
                sectionData.setKey(key.substring(0, 1).toUpperCase() + key.substring(1));
                sectionData.setValue(value);
                sectionData.setComment(comment);
                section.sortSectionData();
                addKeyValueToListView();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Key Not Unique");
                alert.setHeaderText("The Key " + key + " already exists..");
                alert.setContentText("Please choose a Unique Key Name or change the Value from the original one!");
                alert.showAndWait();
            }
        } else {
            keyAlreadyExistsOrStringContainsFalseChars = true;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Not Allowed Characters");
            alert.setHeaderText("One of your Values consists not allowed characters");
            alert.setContentText("Please only use letters or numbers as values");
            alert.showAndWait();
        }
        return keyAlreadyExistsOrStringContainsFalseChars;
    }

    public INIReaderController getController() {
        return controller;
    }
}
