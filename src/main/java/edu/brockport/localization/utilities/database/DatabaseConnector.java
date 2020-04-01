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
    private Connection myDbConn;
    private String url;
    private static DatabaseConnector myInstance = null;

    private DatabaseConnector() throws SQLException {
        url ="jdbc:mysql://brockportpaychex.mysql.database.azure.com:3306/translations?useSSL=true&requireSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e){
            System.out.println("Class Not Found");
            System.exit(0);
        }
        myDbConn = DriverManager.getConnection(url, "Brockport@brockportpaychex", "Paychex2020");
    }

    public static DatabaseConnector getInstance() throws Exception {
        if(myInstance == null){
            myInstance = new DatabaseConnector();
        }
        return myInstance;
    }

    public boolean insertIntoTable(String tableName, String[] dbFields, String[] dbFieldsValues) throws SQLException {
        try {
            String query = QueryBuilder.insertIntoStatement(tableName, dbFields, dbFieldsValues);
            Statement st = myDbConn.createStatement();
            st.executeUpdate(query);
            st.close();
            return true;
        } catch(Exception e){
            return false;
        }
    }

    public boolean deleteFromTable(String tableName, String field, String value) throws SQLException {
        try{
            String query = QueryBuilder.deleteStatement(tableName, field, value);
            Statement st = myDbConn.createStatement();
            st.executeUpdate(query);
            st.close();
            return true;
        } catch(Exception e){
            return false;
        }

    }

    public boolean insertJSIntoDatabase(File jsFile) throws IOException, SQLException {
        jsPropertiesBuilder jsBuilder = new jsPropertiesBuilder(jsFile);
        Properties jsProperties = jsBuilder.getProps();
        String locale = jsBuilder.getLanguage();

        try {
            for(String key : jsProperties.stringPropertyNames()){
                if(existsInTable("TranslationKeys", "ID", "TransKey", key)){
                    String keyIDQuery = QueryBuilder.selectQuery("TranslationKeys", "ID", "TransKey", key);
                    Statement st = myDbConn.createStatement();
                    ResultSet rs = st.executeQuery(keyIDQuery);
                    rs.next();
                    String keyID = rs.getString("ID");
                    st.close();
                    insertIntoTable("Translations", new String[]{"TransKeyFK", "Locale", "Translation", "Status"}, new String[]{keyID, locale, jsProperties.getProperty(key), "Active"});
                } else {
                    insertIntoTable("TranslationKeys", new String[]{"TransKey", "SourceResourceKeyFK", "Status"}, new String[]{key, "1", "Active"});
                    String keyIDQuery = QueryBuilder.selectQuery("TranslationKeys", "ID", "TransKey", key);
                    Statement st = myDbConn.createStatement();
                    ResultSet rs = st.executeQuery(keyIDQuery);
                    rs.next();
                    String keyID = rs.getString("ID");
                    st.close();
                    insertIntoTable("Translations", new String[]{"TransKeyFK", "Locale", "Translation", "Status"}, new String[]{keyID, locale, jsProperties.getProperty(key), "Active"});
                }
            }
        } catch(Exception e){
            return false;
        }

        return true;
    }

    public boolean insertRESXIntoDatabase(File resxFile) throws ParserConfigurationException, SAXException, IOException {
        xmlPropertiesBuilder xmlBuilder = new xmlPropertiesBuilder(resxFile);
        Properties xmlProperties = xmlBuilder.getProps();
        String locale = xmlBuilder.getLanguage();

        try{
            for(String key : xmlProperties.stringPropertyNames()){
                if(existsInTable("TranslationKeys", "ID", "TransKey", key)){
                    String keyIDQuery = QueryBuilder.selectQuery("TranslationKeys", "ID", "TransKey", key);
                    Statement st = myDbConn.createStatement();
                    ResultSet rs = st.executeQuery(keyIDQuery);
                    rs.next();
                    String keyID = rs.getString("ID");
                    st.close();
                    insertIntoTable("Translations", new String[]{"TransKeyFK", "Locale", "Translation", "Status"}, new String[]{keyID, locale, xmlProperties.getProperty(key), "Active"});
                } else {
                    insertIntoTable("TranslationKeys", new String[]{"TransKey", "SourceResourceKeyFK", "Status"}, new String[]{key, "2", "Active"});
                    String keyIDQuery = QueryBuilder.selectQuery("TranslationKeys", "ID", "TransKey", key);
                    Statement st = myDbConn.createStatement();
                    ResultSet rs = st.executeQuery(keyIDQuery);
                    rs.next();
                    String keyID = rs.getString("ID");
                    st.close();
                    insertIntoTable("Translations", new String[]{"TransKeyFK", "Locale", "Translation", "Status"}, new String[]{keyID, locale, xmlProperties.getProperty(key), "Active"});
                }
            }
        } catch(Exception e){
            return false;
        }
        return true;
    }

    private boolean existsInTable(String tableName, String operand, String field, String value) throws SQLException {
        String query = QueryBuilder.selectQuery(tableName, operand, field, value);
        Statement st = myDbConn.createStatement();
        ResultSet rs = st.executeQuery(query);
        return rs.next();
    }

    public ResultSet selectFromTable(String tableName, String operand, String field, String value) throws SQLException{
        String query = QueryBuilder.selectQuery(tableName, operand, field, value);
        Statement st = myDbConn.createStatement();
        ResultSet rs = st.executeQuery(query);
        return rs;
    }

    public ResultSet selectStarFromTable(String tableName) throws SQLException{
        String query = QueryBuilder.selectStarQuery(tableName);
        Statement st = myDbConn.createStatement();
        ResultSet rs = st.executeQuery(query);
        return rs;
    }

    public ResultSet selectJoinFromTable() throws SQLException {
        String query = QueryBuilder.selectJoinQuery();
        Statement st = myDbConn.createStatement();
        ResultSet rs = st.executeQuery(query);
        return rs;
    }



    public ArrayList<Translation> getTranslationList() throws Exception {
        DatabaseConnector dbc = DatabaseConnector.getInstance();
        ArrayList<Translation> translations = new ArrayList<>();
        ResultSet resultSet = dbc.selectJoinFromTable();
        while(resultSet.next()) {
            Translation translation = new Translation(resultSet.getString("TransKey"), resultSet.getString("Locale"), resultSet.getString("Translation"));
            translations.add(translation);
        }
        return translations;
    }

}
