package edu.brockport.localization.utilities.database;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryBuilderTest {

    QueryBuilder qb = new QueryBuilder();

    @Test
    void insertIntoStatement() {
        String[] dbFields = {"Locale"};
        String[] dbValues = {"en_US"};
        String expected = "INSERT INTO Translations (Locale) VALUES ('en_US')";
        String actual = qb.insertIntoStatement("Translations", dbFields, dbValues );
        assertEquals(expected, actual);
    }

    @Test
    void deleteStatement() {
        String expected = "DELETE FROM Translations WHERE Locale='en_US'";
        String actual = qb.deleteStatement("Translations", "Locale", "en_US");
        assertEquals(expected, actual);
    }


    @Test
    void updateStatement() {
        String[] updateFields = {"TransKeyFK", "Locale", "Translation"};
        String[] updateValues = {"1", "en_US", "Time and Attendance"};
        String whereClauseField = "Status";
        String whereClauseValue = "Active";
        String expected = "UPDATE Translations SET TransKeyFK=1,Locale='en_US',Translation='Time and Attendance' WHERE Status='Active'";
        String actual = qb.updateStatement("Translations", updateFields, updateValues, whereClauseField, whereClauseValue);
        assertEquals(expected, actual);
    }

    @Test
    void selectQuery() {
        String expected = "SELECT Translation FROM Translations WHERE Locale='en_US'";
        String actual = qb.selectQuery("Translations", "Translation", "Locale", "en_US");
        assertEquals(expected, actual);
    }

    @Test
    void selectStarQuery() {
        String expected = "SELECT * FROM Translations";
        String actual = qb.selectStarQuery("Translations");
        assertEquals(expected, actual);
    }

    @Test
    void selectJoinQuery() {
        String expected = "SELECT translationkeys.TransKey, translations.Locale, translations.Translation, translations.Status, sourceresource.ResourceName " +
                "FROM translationkeys, translations, sourceresource " +
                "WHERE (translationkeys.ID = translations.TransKeyFK AND sourceresource.ID = translationkeys.SourceResourceKeyFK) " +
                "ORDER BY translationkeys.TransKey;";
        String actual = qb.selectJoinQuery();
        assertEquals(expected, actual);
    }

}