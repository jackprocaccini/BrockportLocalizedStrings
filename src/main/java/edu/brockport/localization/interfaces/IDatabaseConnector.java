package edu.brockport.localization.interfaces;

import edu.brockport.localization.utilities.database.DatabaseConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDatabaseConnector {

    public Connection getConnection();

    public void insertIntoTable(Connection connection, IQueryBuilder queryBuilder, String tableName, String[] dbFields, String[] dbFieldsValues) throws SQLException;
    public void deleteFromTable(Connection connection, IQueryBuilder queryBuilder, String tableName, String dbFields, String dbFieldsValue) throws SQLException;
    public boolean existsInTable(Connection connection, IQueryBuilder queryBuilder, String tableName, String operand, String field, String value) throws SQLException;
    public ResultSet selectFromTable(Connection connection, IQueryBuilder queryBuilder, String tableName, String operand, String field, String value) throws SQLException;
    public ResultSet selectStarFromTable(Connection connection, IQueryBuilder queryBuilder, String tableName) throws SQLException;
    public ResultSet selectQueryMultipleFields(Connection connection, IQueryBuilder queryBuilder, String tableName, String operand, String[] fields, String[] values) throws SQLException;
}
