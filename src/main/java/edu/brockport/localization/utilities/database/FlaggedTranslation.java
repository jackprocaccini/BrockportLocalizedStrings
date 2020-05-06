package edu.brockport.localization.utilities.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FlaggedTranslation extends Translation{
    private String dateFlagged;
    private String dateResolved;
    private String notes;

    public FlaggedTranslation(String transKey, String locale, String transValue, String status, String resourceType, String dateFlagged, String dateResolved, String notes){
        super(transKey, locale, transValue, status, resourceType);
        this.dateFlagged = dateFlagged;
        this.dateResolved = dateResolved;
        this.notes = notes;
    }

    public String getDateFlagged() {
        return dateFlagged;
    }

    public String getDateResolved() {
        return dateResolved;
    }

    public String getNotes() {
        return notes;
    }

    public static ArrayList<FlaggedTranslation> getFlaggedTranslationList(ResultSet resultSet) throws SQLException {
        ArrayList<FlaggedTranslation> translations = new ArrayList<FlaggedTranslation>();
        while(resultSet.next()) {
            FlaggedTranslation translation = new FlaggedTranslation(resultSet.getString("TransKey"),
                    resultSet.getString("Locale"),
                    resultSet.getString("Translation"),
                    resultSet.getString("Status"),
                    resultSet.getString("ResourceName"),
                    resultSet.getString("DateFlagged"),
                    resultSet.getString("DateResolved"),
                    resultSet.getString("Notes"));
            translations.add(translation);
        }
        return translations;
    }

    public String toString(){
        return super.toString() + "#" + dateFlagged + "#" + dateResolved + "#" + notes + "#";
    }
}
