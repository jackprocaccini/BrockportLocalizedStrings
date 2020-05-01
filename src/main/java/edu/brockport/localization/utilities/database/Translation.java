package edu.brockport.localization.utilities.database;

import edu.brockport.localization.interfaces.IDatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Translation {
    private String transKey;
    private String transValue;
    private String locale;
    private String status;
    private String resourceType;
    private boolean flagged;

    private static final Logger log = LogManager.getLogger(Translation.class);

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
        flagged = false;
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

    public void setFlagged(boolean flagged){
        this.flagged = flagged;
    }

    public boolean isFlagged(){
        return flagged;
    }

    public static ArrayList<Translation> getTranslationList(ResultSet resultSet) throws SQLException {
        ArrayList<Translation> translations = new ArrayList<>();
        DatabaseConnector dbc = DatabaseConnector.getInstance();
//        ResultSet resultSet = selectJoinFromTable();
        while(resultSet.next()) {
            Translation translation = new Translation(resultSet.getString("TransKey"),
                    resultSet.getString("Locale"),
                    resultSet.getString("Translation"),
                    resultSet.getString("Status"),
                    resultSet.getString("ResourceName"));
            translation.setFlagged(translation.isFlaggedInDatabase(dbc, translation.getTransValue()));
            translations.add(translation);
        }
        return translations;
    }

    private boolean isFlaggedInDatabase(IDatabaseConnector dbc, String translationValue){
        ResultSet translationKeyIDResultSet;
        ResultSet translationIDResultSet;
        String translationKeyID;
        String translationID;

        try{
//            translationIDResultSet = dbc.selectFromTable(dbc.getConnection(), new QueryBuilder(),
//                    "translations", "ID", "Translation", translationValue);
            translationKeyIDResultSet = dbc.selectFromTable(dbc.getConnection(), new QueryBuilder(),
                    "translationkeys", "ID", "TransKey", transKey);
            translationKeyIDResultSet.next();
            translationKeyID = translationKeyIDResultSet.getString("ID");

            translationIDResultSet = dbc.selectQueryMultipleFields(dbc.getConnection(), new QueryBuilder(),
                    "translations", "ID", new String[]{"TransKeyFK", "Locale", "Translation"},
                    new String[]{translationKeyID, locale, transValue});

            if(translationIDResultSet.next()){
                translationID = translationIDResultSet.getString("ID");
                return dbc.existsInTable(dbc.getConnection(), new QueryBuilder(), "translationtracking", "DateFlagged", "TranslationKeyFK", translationID);
            } else {
                log.error("No ID found for translation: " + translationValue + " in translations table");
                return false;
            }
        } catch(SQLException e){
            log.error("Error in Translation.java: " + e.getMessage());
            return false;
        }


    }

    public String toString(){
        return transKey + "#" + transValue + "#" + locale + "#" + status + "#" + resourceType;
    }
}