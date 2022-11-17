package application;

import controller.INIReaderController;
import model.Section;
import model.SectionData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class INIReader {
    private List<String> iniFile;
    private List<String> newFile;
    private INIReaderController controller;
    private String path;

    public INIReader(String path, INIReaderController controller) {
        this.path = path;
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

    private void createObjects() {
        String section = "";
        String comment = "";
        List<SectionData> dataList = new ArrayList<>();
        for (String line : iniFile) {
            line = line.trim();
            if (line.startsWith("[") && line.endsWith("]")) {
                if (!section.equals("")) {
                    new Section(section, dataList);
                    dataList = new ArrayList<>();
                }
                section = line;
            } else if (line.startsWith("#")) {
                comment += line + System.lineSeparator();
            } else if (!line.equals("")) {
                if (!comment.equals("")) {
                    comment = comment.substring(0, comment.length() - 2);
                }
                String[] splitLine = line.split("=");
                dataList.add(new SectionData(splitLine[0], splitLine[1], comment));
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

        for (SectionData data : sectionData) {
            controller.getLvKeyValue().getItems().add(data);
        }
    }

    public void addSection(String text) {
        List<SectionData> sectionList = new ArrayList<>();
        Section section = new Section("[" + text.substring(0, 1).toUpperCase() + text.substring(1) + "]", sectionList);
        addSectionsToListView();
    }

    public void deleteSection() {
        Section section = (Section) controller.getLvSection().getSelectionModel().getSelectedItem();
        Section.getSections().remove(section);
        addSectionsToListView();
    }

    public void updateSection(String updatetSectionName) {
        Section section = (Section) controller.getLvSection().getSelectionModel().getSelectedItem();
        section.setSectionName(updatetSectionName.substring(0, 1).toUpperCase() + updatetSectionName.substring(1));
        addSectionsToListView();
    }

    public void addKeyValue(String key, String value, String comment) {
        Section section = (Section) controller.getLvSection().getSelectionModel().getSelectedItem();
        section.getSectionData().add(new SectionData(key, value, comment));
        addKeyValueToListView();
    }

    public void deleteKeyValue() {
        Section section = (Section) controller.getLvSection().getSelectionModel().getSelectedItem();
        SectionData sectionData = (SectionData) controller.getLvKeyValue().getSelectionModel().getSelectedItem();
        section.getSectionData().remove(sectionData);
        addKeyValueToListView();
    }
}
