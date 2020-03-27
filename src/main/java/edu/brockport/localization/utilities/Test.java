package edu.brockport.localization.utilities;

import edu.brockport.localization.utilities.database.DatabaseConnector;

import java.io.File;

public class Test {

    public static void main(String[] args) throws Exception {
        DatabaseConnector dbc = DatabaseConnector.getInstance();

        File exampleJS_EN_File = new File("src/main/resources/timeExample-en.js");
        File exampleJS_ES_File = new File("src/main/resources/timeExample-es.js");
        File exampleRESX_EN_File = new File("src/main/resources/EmployeeExample.resx");
        File exampleRESX_ES_File = new File("src/main/resources/EmployeeExample.es-ES.resx");

        dbc.insertJSIntoDatabase(exampleJS_EN_File);
        dbc.insertJSIntoDatabase(exampleJS_ES_File);
        dbc.insertRESXIntoDatabase(exampleRESX_EN_File);
        dbc.insertRESXIntoDatabase(exampleRESX_ES_File);
    }
}

//        String query = QueryBuilder.selectQuery("ID", "SourceResource", "ResourceName", "js");
//        System.out.println(query);
//        dbc.insertIntoTable("SourceResource", new String[]{"ResourceName"}, new String[]{"Test 1"});
//        dbc.insertIntoTable("SourceResource", new String[]{"ResourceName"}, new String[]{"Delete Me!"});
//        dbc.deleteFromTable("SourceResource", "ResourceName", "Delete Me!");
//        xmlPropertiesBuilder builder = new xmlPropertiesBuilder(new File("src/main/resources/EmployeeHome.es-ES.resx"));
//        builder.getProps().list(System.out);
//        System.out.println(builder.getLanguage());
//
//        System.out.println("\n-------------------------\n");
//
//        jsPropertiesBuilder jsBuilder = new jsPropertiesBuilder(new File("src/main/resources/time-en.js"));
//        jsBuilder.getProps().list(System.out);
