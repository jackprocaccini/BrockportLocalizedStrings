package edu.brockport.localization.utilities;

public class Test {

    public static void main(String[] args){
        String[] dbFields = {"ID", "Source"};
        String[] dbValues = {"1", "js"};
        String[] dbFieldsTypes = {"int", "char(20)"};
        System.out.println(QueryBuilder.insertIntoQuery("test", dbFields, dbValues));
        System.out.println(QueryBuilder.deleteQuery("test", "ID", "1"));
        System.out.println(QueryBuilder.updateQuery("test", dbFields, dbValues, "ID", "69"));
        System.out.println(QueryBuilder.createTableQuery("test", dbFields, dbFieldsTypes, "ID", null));
    }
}
