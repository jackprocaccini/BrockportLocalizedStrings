package edu.brockport.localization.utilities.xml;

public class Data {

    private String name;
    private String value;
    private String comment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getComment() {
        if(comment == null){
            return "";
        } else {
            return comment;
        }
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
