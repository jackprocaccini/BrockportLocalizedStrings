package edu.brockport.localization.interfaces;

public interface IQueryBuilder {

    public String insertIntoStatement(String tableName, String[] dbFields, String[] dbValues);
    public String deleteStatement(String tableName, String field, String value);
    public String createTableStatement(String tableName, String[] fields, String[] fieldTypes, String primaryKey, String[] foreignKey);
    public String updateStatement(String tableName, String[] updateFields, String[] updateValues, String whereClauseField, String whereClauseValue);
    public String selectQuery(String tableName, String operand, String field, String value);
    public String selectStarQuery(String tableName);

}
