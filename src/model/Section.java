package model;

import javafx.collections.FXCollections;

import java.util.*;

public class Section {

    private String sectionName;
    private List<SectionData> sectionData;
    private static List<Section> sections = FXCollections.observableArrayList();

    /**
     * Constructor for the NoGui.Section Object.
     *
     * @param sectionName The name of the new Sections.
     * @param sectionData A List which holds all the Data(Key-Value, Comments) for the NoGui.Section.
     */
    public Section(String sectionName, List<SectionData> sectionData) {
        this.sectionName = sectionName;
        this.sectionData = sectionData;
        setSections(this);
    }

    /**
     * Sorts the SectionData
     */
    public void sortSectionData() {
        sectionData.sort(Comparator.comparing(SectionData::getKey));
    }

    /**
     * Getter's and Setter's'
     **/
    public static List<Section> getSections() {
        return sections;
    }

    public void setSections(Section section) {
        sections.add(section);
        Collections.sort(sections, Comparator.comparing((Section o) -> o.getSectionName().toLowerCase()));
        sections.sort(Comparator.comparing(Section::getSectionName));
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
        sections.sort(Comparator.comparing(Section::getSectionName));
    }

    public List<SectionData> getSectionData() {
        return sectionData;
    }

}
