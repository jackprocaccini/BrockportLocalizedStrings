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

    public void setDateFlagged(String dateFlagged) {
        this.dateFlagged = dateFlagged;
    }

    public void setDateResolved(String dateResolved) {
        this.dateResolved = dateResolved;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
        return super.toString() + dateFlagged + "#" + dateResolved + "#" + notes + "#";
    }

    public String toStringHTML(){
        /*https://stackoverflow.com/questions/2428572/how-do-i-escape-a-single-quote
         * Needed for when a "Notes" section contains double quotes. For example,
         * if someone types this as a note for a flagged translation: Hello in spanish is "Hola" not "ola"
         */
        String str = this.toString();
        str = str.replace("\"", "&#34");
        return str;
    }
}
