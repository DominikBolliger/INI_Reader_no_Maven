package model;

import javafx.collections.FXCollections;

import java.util.*;

/**
 * The NoGui.Section class Holds the Data for each NoGui.Section in the ini FIle. For every NoGui.Section a new
 * Object is created and saved into a static List to handla all the Objects.
 * @author  Dominik Bolliget
 * @version 1.0
 */
public class Section {

    private String sectionName;
    private List<SectionData> sectionData;
    private static List<Section> sections = FXCollections.observableArrayList();

    /**
     * Constructor for the NoGui.Section Object.
     * @param sectionName   The name of the new Sections.
     * @param sectionData   A List which holds all the Data(Key-Value, Comments) for the NoGui.Section.
     */
    public Section(String sectionName, List<SectionData> sectionData){
        this.sectionName = sectionName;
        this.sectionData = sectionData;
        setSections(this);
    }

    /**
     * Getter for the List containing all the NoGui.Section objects.
     * @return THe list of all Sections
     */
    public static List<Section> getSections() {
        return sections;
    }

    public void setSections(Section section) {
        sections.add(section);
        Collections.sort(sections, Comparator.comparing((Section o) -> o.getSectionName().toLowerCase()));
        sections.sort(Comparator.comparing(Section::getSectionName));
    }

    /**
     * Getter for the NoGui.Section name.
     * @return The NoGui.Section Name.
     */
    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
        sections.sort(Comparator.comparing(Section::getSectionName));
    }

    /**
     * Gett for the Data List of a NoGui.Section
     * @return The list of Data from a NoGui.Section
     */
    public List<SectionData> getSectionData() {
        return sectionData;
    }

    public void sortSectionData() {
        sectionData.sort(Comparator.comparing(SectionData::getKey));
    }
}
