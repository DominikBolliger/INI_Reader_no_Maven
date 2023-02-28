package model;

public class SectionData {
    private String key;
    private String value;
    private String comment;

    /**
     * Constructor for the NoGui.SectionData Object.
     *
     * @param key     The key.
     * @param value   The value.
     * @param comment The comment.
     */
    public SectionData(String key, String value, String comment) {
        this.key = key;
        this.value = value;
        this.comment = comment;
    }

    /**
     * Getter's and Setter's'
     **/
    public void setValue(String value) {
        this.value = value;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getComment() {
        return comment;
    }
}
