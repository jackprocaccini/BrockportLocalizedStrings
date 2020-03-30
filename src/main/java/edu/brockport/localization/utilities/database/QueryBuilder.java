package edu.brockport.localization.utilities.database;

public class QueryBuilder {

    private QueryBuilder(){

    }

    /**
     * Generates an 'INSERT INTO' query of the form "INSERT INTO tableName (dbFields) VALUES (dbValues)"
     * @param tableName String. The name of the table that you wish to insert data into.
     * @param dbFields String array. The names of the fields/columns in the table (for example ID, FirstName, LastName).
     * @param dbValues String array. The information that you wish to insert into the table (for example 22, Jack, Proc).
     * @return A valid SQL INSERT INTO query in the form of a String
     */
    public static String insertIntoQuery(String tableName, String[] dbFields, String[] dbValues){
        String fields;
        String values;

        fields = fieldsParenthesisMaker(dbFields);
        values = valuesParenthesisMaker(dbValues);

        return "INSERT INTO " + tableName + " " + fields + " VALUES " + values;
    }

    /**
     * Generates a 'DELETE' query of the form "DELETE FROM tableName WHERE field = value"
     * @param tableName String. The name of the table that you wish to insert data into.
     * @param field String. The name of the field that you would like to delete (for example FirstName)
     * @param value String. The value in the table that you would like to delete.
     * @return A valid SQL DELETE query in the form of a String.
     */
    public static String deleteQuery(String tableName, String field, String value){
        if(isInt(value)){
            return "DELETE FROM " + tableName + " WHERE " + field + "=" + value;
        } else {
            return "DELETE FROM " + tableName + " WHERE " + field + "='" + value + "'";
        }
    }

    /**
     * Generates a 'CREATE TABLE' query of the form "CREATE TABLE tableName (fields, fieldTypes, PRIMARY KEY(primaryKey))"
     * @param tableName String. The name of the table that you wish to generate.
     * @param fields String array. The names of the fields / column titles of your table. Each field should have a corresponding
     *               value in the 'fieldTypes' array at the same index. For example, if you wish to have a column in your table called 'ID'
     *               that is of type 'int', follow this format:
     *
     *               fields[0]="ID"       fieldTypes[0]="int"
     *               fields[1]="Name"       fieldTypes[1]="char(20)"
     * @param fieldTypes String array. The data types of your table's fields. Each value should have a corresponding field
     *                   in the 'fields' array at the same index.
     * @param primaryKey String. The primary key of your table. Should be a String that is present in the 'fields' array.
     * @param foreignKey String. The foreign key of your table. Should be a String that is present in the 'fields' array.
     *                   Pass 'null' as the foreignKey parameter if you do not intend for your table to have a foreign key.
     * @return String - A valid SQL CREATE TABLE query in the form of a String. Returns 'null' if fields.length != fieldTypes.length
     */
    public static String createTableQuery(String tableName, String[] fields, String[] fieldTypes, String primaryKey, String foreignKey){
        if(fields.length != fieldTypes.length){
            return null;
        }

        String query = "CREATE TABLE " + tableName + " (";

        for(int i = 0; i < fields.length; i++){
            query += fields[i] + " " + fieldTypes[i] + ", ";
        }

        if(foreignKey != null){
            query += "PRIMARY KEY(" + primaryKey + "), " + "FOREIGN KEY(" + foreignKey + "))";
        } else {
            query += "PRIMARY KEY(" + primaryKey + "))";
        }


        return query;
    }

    public static String updateQuery(String tableName, String[] updateFields, String[] updateValues, String whereClauseField, String whereClauseValue){
        if(updateFields.length != updateValues.length){
            return null;
        }

        String query = "UPDATE " + tableName + " SET ";

        for(int i = 0; i < updateFields.length; i++){
            if(isInt(updateValues[i])){
                query += updateFields[i] + "=" + updateValues[i] + ",";
            } else {
                query += updateFields[i] + "='" + updateValues[i] + "',";
            }
        }

        query = query.substring(0, query.length() - 1); //remove comma from end of line

        query += " WHERE " + whereClauseField + "=";

        if(isInt(whereClauseValue)){
            query += whereClauseValue;
        } else {
            query += "'" + whereClauseValue + "'";
        }

        return query;
    }

    public static String selectQuery(String tableName, String operand, String field, String value){
        String query = "SELECT " + operand + " FROM " + tableName + " WHERE " + field + "=";

        if(isInt(value)){
            query += value;
        } else {
            query += "'" + value + "'";
        }

        return query;
    }

    /**
     * Takes in user fields and formats values into a single SQL readable String. Used primarily as helper method, is able
     * to distinguish between Strings and numbers contained within the 'values' array.
     * Passing in an array containing ["Jack", "Procaccini", "122298"] yields "('Jack', 'Procaccini', 122209)"
     * @param values The values that you wish to use in your sql statement
     * @return A formatted SQL String which places values in the form (field1, field2, ...)
     */
    private static String valuesParenthesisMaker(String[] values){
        String str = "(";

        for(int i = 0; i < values.length; i++){

            if(isInt(values[i])){
                str += values[i] + ",";
            } else {
                str += "'" + values[i] + "',";
            }
        }

        str = str.substring(0, str.length() - 1) + ")";
        return str;
    }

    /**
     * Takes in user fields and formats fields into a single SQL readable String. Used primarily as a helper method.
     * Passing in an array containing ["FirstName", "LastName", "DOB"] yields "(FirstName, LastName, DOB)"
     * @param fields The fields of the table you are working with
     * @return A formatted SQL String which places fields in the form (field1, field2, ...)
     */
    private static String fieldsParenthesisMaker(String[] fields){
        String str = "(";

        for(int i = 0; i < fields.length; i++){
            str += fields[i] + ",";
        }

        str = str.substring(0, str.length() - 1) + ")";

        return str;
    }

    /**
     * Checks if a given String is an int
     * @param str The String you wish to check
     * @return True or False, depending if the passed String is just a number
     */
    private static boolean isInt(String str){
        try{
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static String selectStarQuery(String tableName){
        return "SELECT * FROM " + tableName;
    }
}
