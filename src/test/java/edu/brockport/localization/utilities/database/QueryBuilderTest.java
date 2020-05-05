package edu.brockport.localization.utilities.database;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryBuilderTest {

    QueryBuilder qb = new QueryBuilder();

    @Test
    void insertIntoStatement() {
        //Arrange
        String[] dbFields = {"Locale"};
        String[] dbValues = {"en_US"};
        String expected = "INSERT INTO Translations (Locale) VALUES ('en_US')";
        //Act
        String actual = qb.insertIntoStatement("Translations", dbFields, dbValues );
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void deleteStatement() {
        //Arrange
        String expected = "DELETE FROM Translations WHERE Locale='en_US'";
        //Act
        String actual = qb.deleteStatement("Translations", "Locale", "en_US");
        //Assert
        assertEquals(expected, actual);
    }


    @Test
    void updateStatement() {
        //Arrange
        String[] updateFields = {"TransKeyFK", "Locale", "Translation"};
        String[] updateValues = {"1", "en_US", "Time and Attendance"};
        String whereClauseField = "Status";
        String whereClauseValue = "Active";
        String expected = "UPDATE Translations SET TransKeyFK=1,Locale='en_US',Translation='Time and Attendance' WHERE Status='Active'";
        //Act
        String actual = qb.updateStatement("Translations", updateFields, updateValues, whereClauseField, whereClauseValue);
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void selectQuery() {
        //Arrange
        String expected = "SELECT Translation FROM Translations WHERE Locale='en_US'";
        //Act
        String actual = qb.selectQuery("Translations", "Translation", "Locale", "en_US");
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void selectStarQuery() {
        //Arrange
        String expected = "SELECT * FROM Translations";
        //Act
        String actual = qb.selectStarQuery("Translations");
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void selectJoinQuery() {
        //Arrange
        String expected = "SELECT translationkeys.TransKey, translations.Locale, translations.Translation, translations.Status, sourceresource.ResourceName " +
                "FROM translationkeys, translations, sourceresource " +
                "WHERE (translationkeys.ID = translations.TransKeyFK AND sourceresource.ID = translationkeys.SourceResourceKeyFK) " +
                "ORDER BY translationkeys.TransKey;";
        //Act
        String actual = qb.selectJoinQuery();
        //Assert
        assertEquals(expected, actual);
    }

}