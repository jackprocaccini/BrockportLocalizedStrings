package edu.brockport.localization.utilities.database;

import edu.brockport.localization.interfaces.IQueryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectorTest {
    private ResultSet mockIDResultSet;
    private Connection mockConnection;
    private Statement mockStatement;

    @BeforeEach
    void setUp(){
        //Arrange
        mockConnection = Mockito.mock(Connection.class);
        mockStatement = Mockito.mock(Statement.class);
        mockIDResultSet = Mockito.mock(ResultSet.class);
    }

    @Test
    void testGetInstance() {
        assertNotNull(DatabaseConnector.getInstance());
    }

    @Test
    void testInsertJSIntoDatabase() throws SQLException, IOException {
        //Arrange
        DatabaseConnector dbc = DatabaseConnector.getInstance();
        DatabaseConnector dbcSpy = Mockito.spy(dbc); // allows us to use real logic of class


        Mockito.doReturn(mockStatement).when(mockConnection).createStatement();
        Mockito.when(mockStatement.executeQuery(Mockito.anyString())).thenReturn(mockIDResultSet);

        Mockito.when(mockIDResultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(mockIDResultSet.getString("ID")).thenReturn("" + (int)(Math.random() * (100 - 1) + 1));

        File jsFile = new File("src\\main\\resources\\timeExample-en.js");

        Mockito.doReturn(true).when(dbcSpy).existsInTable(Mockito.any(Connection.class),
                Mockito.any(IQueryBuilder.class), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyString());

        Mockito.doNothing().when(dbcSpy).insertIntoTable(Mockito.any(Connection.class),
                Mockito.any(IQueryBuilder.class), Mockito.anyString(), Mockito.any(String[].class),
                Mockito.any(String[].class));

        Mockito.doReturn(mockConnection).when(dbcSpy).getConnection();

        //Act and Assert
        dbcSpy.insertJSIntoDatabase(mockConnection, new QueryBuilder(), jsFile);
    }

    @Test
    void testInsertRESXIntoDatabase() throws SQLException, IOException, ParserConfigurationException, SAXException {
        //Arrange
        DatabaseConnector dbc = DatabaseConnector.getInstance();
        DatabaseConnector dbcSpy = Mockito.spy(dbc); // allows us to use real logic of class

        Mockito.doReturn(mockStatement).when(mockConnection).createStatement();
        Mockito.when(mockStatement.executeQuery(Mockito.anyString())).thenReturn(mockIDResultSet);

        Mockito.when(mockIDResultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(mockIDResultSet.getString("ID")).thenReturn("" + (int)(Math.random() * (100 - 1) + 1));

        File resxFile = new File("src\\main\\resources\\employeeExample.resx");

        Mockito.doReturn(true).when(dbcSpy).existsInTable(Mockito.any(Connection.class),
                Mockito.any(IQueryBuilder.class), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyString());

        Mockito.doNothing().when(dbcSpy).insertIntoTable(Mockito.any(Connection.class),
                Mockito.any(IQueryBuilder.class), Mockito.anyString(), Mockito.any(String[].class),
                Mockito.any(String[].class));

        Mockito.doReturn(mockConnection).when(dbcSpy).getConnection();

        //Act and Assert
        dbcSpy.insertRESXIntoDatabase(mockConnection, new QueryBuilder(), resxFile);
    }
}