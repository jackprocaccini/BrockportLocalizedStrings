package edu.brockport.localization.utilities.database;

public class Translation {
    private String transKey;
    private String transValue;
    private String locale;

    public Translation(String transKey, String locale, String transValue) {
        this.transKey = transKey;
        this.locale = locale;
        this.transValue = transValue;
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
}