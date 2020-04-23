package edu.brockport.localization.utilities.database;

import edu.brockport.localization.interfaces.IQueryBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectorTest {

    @Test
    void testGetInstance() {
        assertNotNull(DatabaseConnector.getInstance());
    }

//    @Test
//    void testInsertIntoTable() throws SQLException {
//        String sqlStatement = "insert into TestTable(Field1) values (\'Value1\')";
//        Connection mockConnection = Mockito.mock(Connection.class);
//        IQueryBuilder mockQueryBuilder = Mockito.mock(IQueryBuilder.class);
//        Statement mockStatement = Mockito.mock(Statement.class);
//        Mockito.when(mockStatement.executeUpdate(Mockito.anyString())).thenReturn(1);
//        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
//        Mockito.doNothing().when(mockStatement).close();
//        Mockito.doNothing().when(mockConnection).close();
//        Mockito.when(mockQueryBuilder.insertIntoStatement(Mockito.anyString(), Mockito.any(), Mockito.any())).thenReturn(sqlStatement);
//
//        DatabaseConnector dbc = DatabaseConnector.getInstance();
//        dbc.insertIntoTable(dbc.getConnection(), mockQueryBuilder, "TestTable", new String[]{"Field1"}, new String[]{"Value1"});
//    }

    @Test
    void testGetTranslationList() throws SQLException {
        ResultSet mockResultSet = Mockito.mock(ResultSet.class);
        Mockito.when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(mockResultSet.getString("TransKey")).thenReturn("TestTransKey");
        Mockito.when(mockResultSet.getString("Locale")).thenReturn("en_US");
        Mockito.when(mockResultSet.getString("Translation")).thenReturn("test translation");
        Mockito.when(mockResultSet.getString("Status")).thenReturn("Active");
        Mockito.when(mockResultSet.getString("ResourceName")).thenReturn("Javascript");
        DatabaseConnector dbc = Mockito.mock(DatabaseConnector.class);
        Mockito.when(dbc.selectJoinFromTable(Mockito.any(Connection.class), Mockito.any(AbstractQueryBuilder.class))).thenReturn(mockResultSet);

        ResultSet rs = dbc.selectJoinFromTable(Mockito.mock(Connection.class), new QueryBuilder());

        ArrayList<Translation> translations = Translation.getTranslationList(rs);
        assertTrue(translations.size() != 0 && translations.get(0).getTransValue().equals("test translation"));
    }

    @Test
    void testInsertJSIntoDatabase() {
        DatabaseConnector dbc = DatabaseConnector.getInstance();
        DatabaseConnector dbcSpy = Mockito.spy(dbc);
        Connection mockConnection = Mockito.mock(Connection.class);
        IQueryBuilder mockQueryBuilder = Mockito.mock(IQueryBuilder.class);

        File jsFile = new File("src\\main\\resources\\timeExample-en.js");

    }
}