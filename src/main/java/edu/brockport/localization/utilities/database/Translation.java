package edu.brockport.localization.utilities.database;

public class Translation {
    private String transKey;
    private String transValue;
    private String locale;
    private String status;
    private String resourceType;

    /**
     * Used for storing generic translation data. Each tuple of a table equates to one Translation object
     * @param transKey String. The key of your translation.
     * @param locale String. The locale of your translation.
     * @param transValue String. The translation associated with 'transKey'
     * @param status String. The status of your translation, either 'Active' or 'Inactive'
     * @param resourceType String. The resource type of your translation (.js, .NET, etc.)
     */
    public Translation(String transKey, String locale, String transValue, String status, String resourceType) {
        this.transKey = transKey;
        this.locale = locale;
        this.transValue = transValue;
        this.status = status;
        if(resourceType.equalsIgnoreCase("js")){
            this.resourceType = "javascript";
        } else if(resourceType.equalsIgnoreCase(".NET")){
            this.resourceType = "resx";
        } else {
            this.resourceType = resourceType;
        }
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