package model;

/**
 * The NoGui.SectionData Class creates NoGui.Section Data Objects for a NoGui.Section
 * @author  Dominik Bolliget
 * @version 1.0
 */
public class SectionData {
    private String key;
    private String value;
    private String comment;

    /**
     * Constructor for the NoGui.SectionData Object.
     * @param key       The key.
     * @param value     The value.
     * @param comment   The comment.
     */
    public SectionData(String key, String value, String comment){
        this.key = key;
        this.value = value;
        this.comment = comment;
    }

    /**
     * Setter for the value
     * @param value The value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Setter for the comment
     * @param comment The value to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Getter for the Key
     * @return The Key
     */
    public String getKey() {
        return key;
    }

    /**
     * Getter for the Value
     * @return The Value
     */
    public String getValue() {
        return value;
    }

    /**
     * Getter for the Comment
     * @return The Comment
     */
    public String getComment() {
        return comment;
    }
}
