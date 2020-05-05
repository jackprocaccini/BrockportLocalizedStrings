package edu.brockport.localization.utilities.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public static ArrayList<Translation> getTranslationList(ResultSet resultSet) throws SQLException {
        ArrayList<Translation> translations = new ArrayList<>();
//        ResultSet resultSet = selectJoinFromTable();
        while(resultSet.next()) {
            Translation translation = new Translation(resultSet.getString("TransKey"),
                    resultSet.getString("Locale"),
                    resultSet.getString("Translation"),
                    resultSet.getString("Status"),
                    resultSet.getString("ResourceName"));
            translations.add(translation);
        }
        return translations;
    }

    public String toString(){
        return transKey + "#" + transValue + "#" + locale + "#" + status + "#" + resourceType;
    }
}