package edu.brockport.localization.utilities.database;

public class Translation {
    private String transKey;
    private String transValue;
    private String locale;
    private String status;
    private String resourceType;

    public Translation(String transKey, String locale, String transValue, String status, String resourceType) {
        this.transKey = transKey;
        this.locale = locale;
        this.transValue = transValue;
        this.status = status;
        this.resourceType = resourceType;
    }

    public String getTransKey() {
        return transKey;
    }

    public String getTransValue() {
        return transValue;
    }

    public String getLocale() {
        return locale;
    }

    public String getStatus() {
        return status;
    }

    public String getResourceType() {
        return resourceType;
    }
}