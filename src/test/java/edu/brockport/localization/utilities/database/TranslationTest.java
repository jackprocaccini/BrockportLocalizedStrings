package edu.brockport.localization.utilities.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TranslationTest {

    private Translation t1 = new Translation("app.time.header", "en_US", "Time and Attendance", "Active", ".js");
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        mockResultSet = Mockito.mock(ResultSet.class);
        Mockito.when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(mockResultSet.getString("TransKey")).thenReturn("TestTransKey");
        Mockito.when(mockResultSet.getString("Locale")).thenReturn("en_US");
        Mockito.when(mockResultSet.getString("Translation")).thenReturn("test translation");
        Mockito.when(mockResultSet.getString("Status")).thenReturn("Active");
        Mockito.when(mockResultSet.getString("ResourceName")).thenReturn("Javascript");
    }

    @Test
    public void testGetTransKey(){
        Translation t2 = t1;
        assertEquals("app.time.header", t1.getTransKey());
    }

    @Test
    public void testGetLocale(){
        assertEquals("en_US", t1.getLocale());
    }

    @Test
    public void testGetTransValue(){
        assertEquals("Time and Attendance", t1.getTransValue());
    }

    @Test
    public void testGetStatus(){
        assertEquals("Active", t1.getStatus());
    }

    @Test
    public void testGetResourceType(){
        assertEquals(".js", t1.getResourceType());
    }

    @Test
    public void testGetTranslationList() throws SQLException {
        DatabaseConnector dbc = Mockito.mock(DatabaseConnector.class);
        Mockito.when(dbc.selectJoinFromTable(Mockito.any(Connection.class), Mockito.any(AbstractQueryBuilder.class))).thenReturn(mockResultSet);

        ResultSet rs = dbc.selectJoinFromTable(Mockito.mock(Connection.class), new QueryBuilder());

        ArrayList<Translation> translations = Translation.getTranslationList(rs);
        assertTrue(translations.size() != 0 && translations.get(0).getTransValue().equals("test translation"));
    }

}