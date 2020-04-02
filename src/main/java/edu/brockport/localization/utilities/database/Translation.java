package edu.brockport.localization.utilities.database;

public class Translation {
    private String transKey;
    private String transValue;
    private String locale;
    private String status;

    public Translation(String transKey, String locale, String transValue, String status) {
        this.transKey = transKey;
        this.locale = locale;
        this.transValue = transValue;
        this.status = status;
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
}