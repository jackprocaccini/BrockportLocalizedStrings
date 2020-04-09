package edu.brockport.localization.utilities.database;

import edu.brockport.localization.utilities.js.jsPropertiesBuilder;
import edu.brockport.localization.utilities.xml.xmlPropertiesBuilder;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DatabaseConnector {
//    private Connection myDbConn;
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
    public boolean insertIntoTable(String tableName, String[] dbFields, String[] dbFieldsValues) throws SQLException {
        try {
            String query = QueryBuilder.insertIntoStatement(tableName, dbFields, dbFieldsValues);
            Statement st = getConnection().createStatement();
            st.executeUpdate(query);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    /**
     * Deletes a single entry from a given table. Makes use of the QueryBuilder class.
     * @param tableName String. The name of the table that you wish to delete data from.
     * @param field String. The field (or column) header within the table.
     * @param value String. Contains the value from the field (or column) that you wish to delete.
     * @return True if data was successfully deleted from the given table, returns false otherwise.
     * @throws SQLException
     */
    public boolean deleteFromTable(String tableName, String field, String value) throws SQLException {
        try{
            String query = QueryBuilder.deleteStatement(tableName, field, value);
            Statement st = getConnection().createStatement();
            st.executeUpdate(query);
            return true;
        } catch(Exception e){
            return false;
        }

    }

    /**
     * Inserts all information contained within a js file into the database. Makes use of QueryBuilder and jsPropertiesBuilder.
     * @param jsFile The javascript file that you would like to insert
     * @return True if all information was inserted into the database, false otherwise.
     * @throws IOException
     * @throws SQLException
     */
    public boolean insertJSIntoDatabase(File jsFile) throws IOException, SQLException {
        jsPropertiesBuilder jsBuilder = new jsPropertiesBuilder(jsFile);
        Properties jsProperties = jsBuilder.getProps();
        String locale = jsBuilder.getLanguage();

        try {
            for(String key : jsProperties.stringPropertyNames()){
                if(existsInTable("TranslationKeys", "ID", "TransKey", key)){
                    String keyIDQuery = QueryBuilder.selectQuery("TranslationKeys", "ID", "TransKey", key);
                    Statement st = getConnection().createStatement();
                    ResultSet rs = st.executeQuery(keyIDQuery);
                    rs.next();
                    String keyID = rs.getString("ID");
                    insertIntoTable("Translations", new String[]{"TransKeyFK", "Locale", "Translation", "Status"}, new String[]{keyID, locale, jsProperties.getProperty(key), "Active"});
                } else {
                    insertIntoTable("TranslationKeys", new String[]{"TransKey", "SourceResourceKeyFK", "Status"}, new String[]{key, "1", "Active"});
                    String keyIDQuery = QueryBuilder.selectQuery("TranslationKeys", "ID", "TransKey", key);
                    Statement st = getConnection().createStatement();
                    ResultSet rs = st.executeQuery(keyIDQuery);
                    rs.next();
                    String keyID = rs.getString("ID");
                    insertIntoTable("Translations", new String[]{"TransKeyFK", "Locale", "Translation", "Status"}, new String[]{keyID, locale, jsProperties.getProperty(key), "Active"});
                }
            }
        } catch(Exception e){
            return false;
        }

        return true;
    }

    /**
     * Inserts all information contained within a resx file into the database. Makes use of QueryBuilder and xmlPropertiesBuilder.
     * @param resxFile The resx file that you wish to insert into the database.
     * @return True if all information was inserted into the database, false otherwise.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public boolean insertRESXIntoDatabase(File resxFile) throws ParserConfigurationException, SAXException, IOException {
        xmlPropertiesBuilder xmlBuilder = new xmlPropertiesBuilder(resxFile);
        Properties xmlProperties = xmlBuilder.getProps();
        String locale = xmlBuilder.getLanguage();

        try{
            for(String key : xmlProperties.stringPropertyNames()){
                if(existsInTable("TranslationKeys", "ID", "TransKey", key)){
                    String keyIDQuery = QueryBuilder.selectQuery("TranslationKeys", "ID", "TransKey", key);
                    Statement st = getConnection().createStatement();
                    ResultSet rs = st.executeQuery(keyIDQuery);
                    rs.next();
                    String keyID = rs.getString("ID");
                    insertIntoTable("Translations", new String[]{"TransKeyFK", "Locale", "Translation", "Status"}, new String[]{keyID, locale, xmlProperties.getProperty(key), "Active"});
                } else {
                    insertIntoTable("TranslationKeys", new String[]{"TransKey", "SourceResourceKeyFK", "Status"}, new String[]{key, "2", "Active"});
                    String keyIDQuery = QueryBuilder.selectQuery("TranslationKeys", "ID", "TransKey", key);
                    Statement st = getConnection().createStatement();
                    ResultSet rs = st.executeQuery(keyIDQuery);
                    rs.next();
                    String keyID = rs.getString("ID");
                    insertIntoTable("Translations", new String[]{"TransKeyFK", "Locale", "Translation", "Status"}, new String[]{keyID, locale, xmlProperties.getProperty(key), "Active"});
                }
            }
        } catch(Exception e){
            return false;
        }
        return true;
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
    public boolean existsInTable(String tableName, String operand, String field, String value) throws SQLException {
        String query = QueryBuilder.selectQuery(tableName, operand, field, value);
        Statement st = getConnection().createStatement();
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
    public ResultSet selectFromTable(String tableName, String operand, String field, String value) throws SQLException{
        String query = QueryBuilder.selectQuery(tableName, operand, field, value);
        Statement st = getConnection().createStatement();
        ResultSet rs = st.executeQuery(query);
        return rs;
    }

    /**
     * Selects every entrie from a given table. Makes use of the QueryBuilder class.
     * @param tableName The name of the table that you wish to select data from.
     * @return A ResultSet containing all information retrieved from the database.
     * @throws SQLException
     */
    public ResultSet selectStarFromTable(String tableName) throws SQLException{
        String query = QueryBuilder.selectStarQuery(tableName);
        Statement st = getConnection().createStatement();
        ResultSet rs = st.executeQuery(query);
        return rs;
    }

    public ResultSet selectJoinFromTable() throws SQLException {
        String query = QueryBuilder.selectJoinQuery();
        Statement st = getConnection().createStatement();
        ResultSet rs = st.executeQuery(query);
        return rs;
    }

    public ArrayList<Translation> getTranslationList() throws Exception {
        ArrayList<Translation> translations = new ArrayList<>();
        ResultSet resultSet = selectJoinFromTable();
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

    private Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch(Exception e){
            return null;
        }
    }

}
