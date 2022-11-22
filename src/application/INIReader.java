package application;

import controller.INIReaderController;
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
        List<SectionData> dataList = new ArrayList<>();
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

        for (SectionData data : sectionData) {
            controller.getLvKeyValue().getItems().add(data);
        }
    }

    public void addSection(String text) {
        List<SectionData> sectionList = new ArrayList<>();
        new Section(text.substring(0, 1).toUpperCase() + text.substring(1), sectionList);
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

    public void updateKeyValue(String key, String value, String comment) {
        SectionData sectionData = (SectionData) controller.getLvKeyValue().getSelectionModel().getSelectedItem();
        sectionData.setKey(key);
        sectionData.setValue(value);
        sectionData.setComment(comment);
        addKeyValueToListView();
    }

}
