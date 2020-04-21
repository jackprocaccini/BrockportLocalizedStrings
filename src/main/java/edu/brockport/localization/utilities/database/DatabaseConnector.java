package edu.brockport.localization.utilities.database;

import edu.brockport.localization.interfaces.IDatabaseConnector;
import edu.brockport.localization.interfaces.IQueryBuilder;
import edu.brockport.localization.utilities.js.jsPropertiesBuilder;
import edu.brockport.localization.utilities.xml.xmlPropertiesBuilder;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnector implements IDatabaseConnector {
    private String url;
    private String username;
    private String password;
    private static DatabaseConnector myInstance = null;

    /**
     * Used to connect to the MySQL database and provide utility methods to manipulate the database's information.
     * Use the getInstance() method to invoke this class.
     * @throws SQLException Throws SQL exception if the mysql driver class cannot be found.
     */
    private DatabaseConnector(){
        url ="jdbc:mysql://brockportpaychex.mysql.database.azure.com:3306/translations?useSSL=true&requireSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        username = "Brockport@brockportpaychex";
        password = "Paychex2020";
    }

    /**
     * Gets an instance of DatabaseConnector. Used to implement the singleton pattern.
     * @return A DatabaseConnector object
     * @throws Exception
     */
    public static DatabaseConnector getInstance(){
        if(myInstance == null){
            myInstance = new DatabaseConnector();
        }
        return myInstance;
    }

    /**
     * Inserts any number of values into a given table. Returns true if data was successfully inserted into the given
     * table, returns false otherwise. Makes use of the QueryBuilder class.
     * @param tableName String. The name of the table that you wish to insert data into.
     * @param dbFields String Array. Contains the field (or column) headers of the table.
     * @param dbFieldsValues String Array. Contains the values that you wish to insert. Values at position i must correspond to an entry in dbFields at the same index.
     * @return True if passed data was inserted into the given table, false if otherwise.
     */
    public void insertIntoTable(Connection connection, IQueryBuilder queryBuilder, String tableName, String[] dbFields, String[] dbFieldsValues) throws SQLException {
        String query = queryBuilder.insertIntoStatement(tableName, dbFields, dbFieldsValues);
        Statement st = connection.createStatement();
        st.executeUpdate(query);
    }

    /**
     * Deletes a single entry from a given table. Makes use of the QueryBuilder class.
     * @param tableName String. The name of the table that you wish to delete data from.
     * @param field String. The field (or column) header within the table.
     * @param value String. Contains the value from the field (or column) that you wish to delete.
     * @return True if data was successfully deleted from the given table, returns false otherwise.
     * @throws SQLException
     */
    public void deleteFromTable(Connection connection, IQueryBuilder queryBuilder, String tableName, String field, String value) throws SQLException {
        String query = queryBuilder.deleteStatement(tableName, field, value);
        Statement st = connection.createStatement();
        st.executeUpdate(query);
    }

    /**
     * Inserts all information contained within a js file into the database. Makes use of QueryBuilder and jsPropertiesBuilder.
     * @param jsFile The javascript file that you would like to insert
     * @return True if all information was inserted into the database, false otherwise.
     * @throws IOException
     * @throws SQLException
     */
    public void insertJSIntoDatabase(Connection connection, IQueryBuilder queryBuilder, File jsFile) throws IOException, SQLException {
        jsPropertiesBuilder jsBuilder = new jsPropertiesBuilder(jsFile);
        Properties jsProperties = jsBuilder.getProps();
        String locale = jsBuilder.getLanguage();

        for(String key : jsProperties.stringPropertyNames()) {
            if (existsInTable(getConnection(),queryBuilder, "TranslationKeys", "ID", "TransKey", key)) {
                String keyIDQuery = queryBuilder.selectQuery("TranslationKeys", "ID", "TransKey", key);
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(keyIDQuery);
                rs.next();
                String keyID = rs.getString("ID");
//              insertIntoTable("Translations", new String[]{"TransKeyFK", "Locale", "Translation", "Status"}, new String[]{keyID, locale, jsProperties.getProperty(key), "Active"});
                insertIntoTable(getConnection(), queryBuilder, "Translations", new String[]{"TransKeyFK", "Locale", "Translation", "Status",},
                        new String[]{keyID, locale, jsProperties.getProperty(key), "Active"});
            } else {
                insertIntoTable(getConnection(), queryBuilder, "TranslationKeys", new String[]{"TransKey", "SourceResourceKeyFK", "Status"}, new String[]{key, "1", "Active"});
                String keyIDQuery = queryBuilder.selectQuery("TranslationKeys", "ID", "TransKey", key);
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(keyIDQuery);
                rs.next();
                String keyID = rs.getString("ID");
                insertIntoTable(getConnection(), queryBuilder, "Translations", new String[]{"TransKeyFK", "Locale", "Translation", "Status"}, new String[]{keyID, locale, jsProperties.getProperty(key), "Active"});
            }
        }
    }

    /**
     * Inserts all information contained within a resx file into the database. Makes use of QueryBuilder and xmlPropertiesBuilder.
     * @param resxFile The resx file that you wish to insert into the database.
     * @return True if all information was inserted into the database, false otherwise.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public void insertRESXIntoDatabase(Connection connection, IQueryBuilder queryBuilder, File resxFile) throws ParserConfigurationException, SAXException, IOException, SQLException {
        xmlPropertiesBuilder xmlBuilder = new xmlPropertiesBuilder(resxFile);
        Properties xmlProperties = xmlBuilder.getProps();
        String locale = xmlBuilder.getLanguage();

        for(String key : xmlProperties.stringPropertyNames()){
            if(existsInTable(getConnection(),queryBuilder,"TranslationKeys", "ID", "TransKey", key)){
                String keyIDQuery = queryBuilder.selectQuery("TranslationKeys", "ID", "TransKey", key);
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(keyIDQuery);
                rs.next();
                String keyID = rs.getString("ID");
                insertIntoTable(getConnection(), queryBuilder, "Translations", new String[]{"TransKeyFK", "Locale", "Translation", "Status"},
                        new String[]{keyID, locale, xmlProperties.getProperty(key), "Active"});
            } else {
                insertIntoTable(getConnection(), queryBuilder,"TranslationKeys", new String[]{"TransKey", "SourceResourceKeyFK", "Status"}, new String[]{key, "2", "Active"});
                String keyIDQuery = queryBuilder.selectQuery("TranslationKeys", "ID", "TransKey", key);
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(keyIDQuery);
                rs.next();
                String keyID = rs.getString("ID");
                insertIntoTable(getConnection(), queryBuilder,"Translations", new String[]{"TransKeyFK", "Locale", "Translation", "Status"}, new String[]{keyID, locale, xmlProperties.getProperty(key), "Active"});
            }
        }
    }

    /**
     * Utility method. Checks if a given entry exists within a given table. Makes use of the QueryBuilder class.
     * @param tableName String. The name of the table that you wish to select data from.
     * @param operand String. What value you want to retrieve from the table.
     * @param field String. The field (or column) header within the table that you are comparing.
     * @param value String. Contains the value from the field (or column) that you wish to compare to.
     * @return True if the specified entry exists, false otherwise.
     * @throws SQLException
     */
    public boolean existsInTable(Connection connection, IQueryBuilder queryBuilder, String tableName, String operand, String field, String value) throws SQLException {
        String query = queryBuilder.selectQuery(tableName, operand, field, value);
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        return rs.next();
    }

    /**
     * Selects entries from a table based on given parameters. Returns a ResultSet containing all information retrieved
     * from the database. Makes use of the QueryBuilder class.
     * @param tableName String. The name of the table that you wish to select data from.
     * @param operand String. What value you want to retrieve from the table.
     * @param field String. The field (or column) header within the table that you are comparing.
     * @param value String. Contains the value from the field (or column) that you wish to compare to.
     * @return A ResultSet containing all entries based on the search criteria. Could possibly be empty.
     * @throws SQLException
     */
    public ResultSet selectFromTable(Connection connection, IQueryBuilder queryBuilder, String tableName, String operand, String field, String value) throws SQLException{
        String query = queryBuilder.selectQuery(tableName, operand, field, value);
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        return rs;
    }

    /**
     * Selects every entrie from a given table. Makes use of the QueryBuilder class.
     * @param tableName The name of the table that you wish to select data from.
     * @return A ResultSet containing all information retrieved from the database.
     * @throws SQLException
     */
    public ResultSet selectStarFromTable(Connection connection, IQueryBuilder queryBuilder, String tableName) throws SQLException{
        String query = queryBuilder.selectStarQuery(tableName);
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        return rs;
    }

    public ResultSet selectJoinFromTable(Connection connection, AbstractQueryBuilder queryBuilder) throws SQLException {
        String query = queryBuilder.selectJoinQuery();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        return rs;
    }

    public Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch(Exception e){
            return null;
        }
    }

}
