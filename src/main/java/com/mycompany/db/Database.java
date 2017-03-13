/**
 * Copyright (c) 2016 Company.
 * All rights reserved.
 */
package com.mycompany.db;

import static com.mycompany.db.MyDatabaseLogger.*;
import static java.lang.System.lineSeparator;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

/**
 * Always start derby database in order to use!
 *
 * NetBeans -> Services -> Databases -> right click jdbc:derby://localhost:1527/sample, Connect...
 *
 * Uses Database Connection Pool internally.
 *
 * More info: OCA/OCP Java SE7 Programmer 1 & 2 Study Guide, Chapter 15: JDBC.
 *
 */
public enum Database {

  DERBY("jdbc:derby:memory:myDB;create=true", "app", "app");

  private final String url;
  private final String username;
  private final String password;
  private final DatabaseConnectionPool pool;

  Database(final String jdbcUrl, final String username, final String password) {
    this.url = jdbcUrl;
    this.username = username;
    this.password = password;
    this.pool = new HikariCpConnectionPool(jdbcUrl, username, password);
  }

  public void execute(final String query) {
    try (Connection conn = pool.getDatabaseConnection();
            Statement stmt = conn.createStatement();) {
      stmt.execute(query);
    } catch (SQLException ex) {
      logSQLException(ex);
    }
  }

  public String submitQueryAndGetResult(final String query) {
    StringBuilder sb = new StringBuilder();
    try (Connection conn = pool.getDatabaseConnection();
        Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {
      while (rs.next()) {
        sb.append(rs.getInt("Customer_ID")).append(" ");
        sb.append(rs.getString("NAME")).append(" ");
        sb.append(rs.getString("EMAIL")).append(" ");
        sb.append(rs.getString("PHONE")).append(" ");
      }
    } catch (SQLException ex) {
      logSQLException(ex);
    }
    return sb.toString().trim();
  }

  public String constructAndUseStatement(final String query) {
    StringBuilder sb = new StringBuilder();
    try (Connection conn = pool.getDatabaseConnection();
            Statement stmt = conn.createStatement()) {
      ResultSet rs;
      int numRows;
      boolean status = stmt.execute(query); // True if there is a ResulSet
      if (status) {
        rs = stmt.getResultSet();
        sb.append(processResultSet(rs));
      } else {
        numRows = stmt.getUpdateCount(); // Get the update count
        if (numRows == -1) { // If -1, there are no results
          sb.append("No results");
        } else { // else, print the number of
          // rows affected
          sb.append(numRows).append(" rows affected.");
        }
      }
    } catch (SQLException ex) {
      logSQLException(ex);
    }
    return sb.toString().trim();
  }

  private String processResultSet(final ResultSet rs) throws SQLException {
    StringBuilder sb = new StringBuilder();
    while (rs.next()) {
      sb.append(rs.getInt("Customer_ID")).append(" ");
      sb.append(rs.getString("NAME")).append(" ");
      sb.append(rs.getString("EMAIL")).append(" ");
      sb.append(rs.getString("PHONE")).append(" ");
    }
    return sb.toString().trim();
  }

  public void getInformationAboutResultSet() {
    String query = "SELECT Customer_ID FROM Customer";
    try (Connection conn = pool.getDatabaseConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)) {
      ResultSetMetaData rsmd = rs.getMetaData();
      // How many columns in this ResultSet?
      int colCount = rsmd.getColumnCount();
      System.out.println("Column Count: " + colCount);
      for (int i = 1; i <= colCount; i++) {
        System.out.println("Table Name:   " + rsmd.getTableName(i));
        System.out.println("Column Name:  " + rsmd.getColumnName(i));
        System.out.println("Column Size:  " + rsmd.getColumnDisplaySize(i));
      }
    } catch (SQLException ex) {
      logSQLException(ex);
    }
  }

  public void printReport() {
    String query = "SELECT Customer_ID, NAME, CITY FROM Customer";
    try (Connection conn = pool.getDatabaseConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)) {
      ResultSetMetaData rsmd = rs.getMetaData();
      int colCount = rsmd.getColumnCount();
      String col;
      String colData;

      for (int i = 1; i <= colCount; i++) {
        // Left justify column name padded with size spaces
        col = leftJustify(rsmd.getColumnName(i), rsmd.getColumnDisplaySize(i));
        System.out.print(col);
      }

      System.out.println(); // Print a linefeed

      while (rs.next()) {
        for (int i = 1; i <= colCount; i++) {
          if (rs.getObject(i) != null) {
            // Get the data in the column as a String
            colData = rs.getObject(i).toString();
          } else {
            // If the column is null use "NULL"
            colData = "NULL";
          }
          col = leftJustify(colData, rsmd.getColumnDisplaySize(i));
          System.out.print(col);
        }
        System.out.println();
      }
    } catch (SQLException ex) {
      logSQLException(ex);
    }
  }

  private String leftJustify(final String s, int n) {
    int padSpace = n;
    if (s.length() <= n) {
      // Add an extra space if the length of the String s is less than or
      // equal to the length of the column n
      padSpace++;
    }
    // Pad to the right of the String
    return String.format("%1$-" + padSpace + "s", s);
  }

  public void moveAroundResultSets() {
    try (Connection conn = pool.getDatabaseConnection()) {
      DatabaseMetaData dbmd = conn.getMetaData();
      if (dbmd.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY)) {
        System.out.print("Supports TYPE_FORWARD_ONLY");
        if (dbmd.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY,
            ResultSet.CONCUR_UPDATABLE)) {
          System.out.println(" and supports CONCUR_UPDATABLE");
        }
      }

      if (dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE)) {
        System.out.print("Supports TYPE_SCROLL_INSENSITIVE");
        if (dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_UPDATABLE)) {
          System.out.println(" and supports CONCUR_UPDATABLE");
        }
      }

      if (dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE)) {
        System.out.print("Supports TYPE_SCROLL_SENSITIVE");
        if (dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE,
            ResultSet.CONCUR_UPDATABLE)) {
          System.out.println(" and supports CONCUR_UPDATABLE");
        }
      }
    } catch (SQLException ex) {
      logSQLException(ex);
    }
  }

  public int demonstrateGetRowCount() {
    String query = "SELECT Customer_ID, NAME, CITY FROM Customer";
    try (Connection conn = pool.getDatabaseConnection();
        Statement stmt =
            conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery(query)) {
      int rowCount = Database.getRowCount(rs);
      System.out.println("There are " + rowCount + " rows in CUSTOMER table.");
      return rowCount;
    } catch (SQLException ex) {
      logSQLException(ex);
    }
    return -1;
  }

  /**
   * Calculate the row count at any time and at any current cursor position. Preserves the current
   * position of the cursor in the ResultSet.
   *
   * @param rs ResulSet Note, this method should only be called on ResultSet objects that are
   *        scrollable (type TYPE_SCROLL_INSENSITIVE).
   * @throws java.sql.SQLException
   */
  public static int getRowCount(final ResultSet rs) throws SQLException {
    int rowCount = -1;
    int currRow = 0;

    // make sure the ResultSet is not null
    if (rs != null) {
      // Save the current row position:
      // zero indicates that there is no current row positioin -
      // could be beforeFirst or afterLast
      currRow = rs.getRow();
      // afterLast, so set the currRow negative
      if (rs.isAfterLast()) {
        currRow = -1;
      }
      rowCount = moveToLastRowAndGetPosition(rs, currRow);
    }
    return rowCount;
  }

  // move to the last row and get the position
  // if rs.last() returns false, there are no results
  private static int moveToLastRowAndGetPosition(final ResultSet rs, int currRow)
      throws SQLException {
    int rowCount = -1;

    if (rs.last()) {
      // Get the row count
      rowCount = rs.getRow();
      // Return the cursor to the position it was in before the method was called.
      // if the currRow is negative, the cursor position was
      // after the last row, so return the cursor to the last row
      if (currRow == -1) {
        rs.afterLast();
        // else if the cursor is zero, move the cursor to before the first row
      } else if (currRow == 0) {
        rs.beforeFirst();
        // else return the cursor to its last position
      } else {
        rs.absolute(currRow);
      }
    }

    return rowCount;
  }

  /**
   * Can be used to modify the contents of a database table, including update existing rows, delete
   * existing rows, and add new rows. Note: an SQL statement that includes a JOIN or an SQL
   * statement with two tables cannot be updated.
   */
  public void updateResultSet() {
    int newCreditLimit = 20_000; // SUPPRESS CHECKSTYLE MagicNumber
    String query = "SELECT CREDIT_LIMIT FROM Customer WHERE CITY = 'New York'";
    try (Connection conn = pool.getDatabaseConnection();
        Statement stmt =
            conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery(query)) {
      while (rs.next()) {
        // Check each row: if creditLimit < 20,000 set it to 20,000
        if (rs.getInt("CREDIT_LIMIT") < newCreditLimit) {
          rs.updateInt("CREDIT_LIMIT", newCreditLimit);
          // update the row in the database
          rs.updateRow();

          if (rs.rowUpdated()) {
            System.out.println("Row: " + rs.getRow() + " updated.");
          }
        }
      }
    } catch (SQLException ex) {
      logSQLException(ex);
    }
  }

  /**
   * Doesn't work with the example DB, throws: java.sql.SQLIntegrityConstraintViolationException:
   * Column 'DISCOUNT_CODE' cannot accept a NULL value.
   */
  public void insertNewRow() {
    String query = "SELECT Customer_ID, NAME, CITY FROM Customer";
    try (Connection conn = pool.getDatabaseConnection();
        Statement stmt =
            conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery(query)) {
      rs.next();
      rs.moveToInsertRow(); // Move the special insert row
      // Create a customer ID
      rs.updateInt("Customer_ID", 2027); // SUPPRESS CHECKSTYLE MagicNumber
      rs.updateString("NAME", "Spectra"); // Set the name
      rs.updateString("CITY", "Moscow"); // Set the city
      rs.insertRow(); // Insert the row into the database
      rs.moveToCurrentRow(); // Move back to the current row in ResultSet

      if (rs.rowInserted()) {
        System.out.println("Row: " + rs.getRow() + " inserted.");
      }
    } catch (SQLException ex) {
      logSQLException(ex);
    }
  }

  public String getInformationAboutDatabase() {
    StringBuilder sb = new StringBuilder();
    try (Connection conn = pool.getDatabaseConnection()) {
      DatabaseMetaData dbmd = conn.getMetaData();
      sb.append("Columns info:").append(lineSeparator()).append(getColumns(dbmd)).append(lineSeparator());
      sb.append("Procedures:").append(lineSeparator()).append(getProcedures(dbmd).toString()).append(lineSeparator());
      sb.append("Driver info:").append(lineSeparator()).append(getDriverInfo(dbmd));
    } catch (SQLException ex) {
      logSQLException(ex);
    }
    return sb.toString();
  }

  private String getColumns(final DatabaseMetaData dbmd) throws SQLException {
    StringBuilder sb = new StringBuilder();
    // Get a ResultSet for any catalog (null) in the APP schema for all
    // tables (%) for all columns (%)
    ResultSet rs = dbmd.getColumns(null, "APP", "%", "%");
    while (rs.next()) {
      sb.append("Table Name: ").append(rs.getString("TABLE_NAME")).append(" ");
      sb.append("Column Name: ").append(rs.getString("COLUMN_NAME")).append(" ");
      sb.append("Type Name: ").append(rs.getString("TYPE_NAME")).append(" ");
      sb.append("Column Size: ").append(rs.getString("COLUMN_SIZE")).append(lineSeparator());
    }
    return sb.toString();
  }

  private List<String> getProcedures(final DatabaseMetaData dbmd) throws SQLException {
    List<String> procedures = new ArrayList<>();
    // Get a ResultSet of all the stored procedures in any catalog (null) in
    // any schema (null) with wildcard name (%)
    ResultSet rs = dbmd.getProcedures(null, null, "%");
    while (rs.next()) {
      procedures.add(rs.getString("PROCEDURE_NAME"));
    }
    return procedures;
  }

  private String getDriverInfo(final DatabaseMetaData dbmd) throws SQLException {
    StringBuilder sb = new StringBuilder();
    sb.append("Driver{");
    sb.append("name=").append(dbmd.getDriverName());
    sb.append(", version=").append(dbmd.getDriverVersion());
    sb.append(", meets minimum requirements for SQL-92 support=").append(dbmd.supportsANSI92EntryLevelSQL());
    sb.append(", supports savepoints=").append(dbmd.supportsSavepoints());
    sb.append("}");
    return sb.toString();
  }

  public void usePreparedStatement() {
    String pQuery = "SELECT * FROM Customer WHERE City LIKE ?";
    try (Connection conn = pool.getDatabaseConnection();
        PreparedStatement pstmt = conn.prepareStatement(pQuery)) {
      // Substitute this String for the first parameter (?)
      pstmt.setString(1, "%New York%");
      try (ResultSet rs = pstmt.executeQuery()) {
        processResultSet(rs);
      }
    } catch (SQLException ex) {
      logSQLException(ex);
    }
  }

  public void workWithRowSet() {
    try (JdbcRowSet jrs = RowSetProvider.newFactory().createJdbcRowSet()) {
      String query = "SELECT * FROM Customer WHERE Name LIKE 'Small Bill Company'";
      jrs.setCommand(query); // Set the query to build the RowSet
      jrs.setUrl(url);
      jrs.setUsername(username);
      jrs.setPassword(password);
      jrs.execute(); // Execute the query stored in setCommand
      processResultSet(jrs);
    } catch (SQLException ex) {
      logSQLException(ex);
    }
  }

  public void demonstrateTransaction() {
    try (Connection conn = pool.getDatabaseConnection()) {
      conn.setAutoCommit(false);      
      try (Statement stmt = conn.createStatement()) {
        stmt.executeUpdate("INSERT INTO Discount_Code VALUES ('O', 1.00)");
        stmt.executeUpdate("INSERT INTO Discount_Code VALUES ('P', 2.00)");
        stmt.executeUpdate("INSERT INTO Discount_Code VALUES ('Q', 3.00)");
        // No exception: commit the entire transaction
        conn.commit();
      } catch (SQLException ex) {
        // Rollback the entire transaction if an exception thrown
        conn.rollback();
        throw new SQLException("Failed to insert three new records in DISCOUNT_CODE.", ex);
      }
    } catch (SQLException ex) {
      logSQLException(ex);
    }
  }

  public void demonstrateTransactionUsingSavepoints() {
    try (Connection conn = getDatabaseConnectionWithAutoCommitDisabled();
            Statement stmt = conn.createStatement()) {
      String query1 = "INSERT INTO Discount_Code VALUES ('O', 1.00)";
      String query2 = "INSERT INTO Discount_Code VALUES ('P', 2.00)";
      Savepoint sp1 = null;
      try {
        stmt.executeUpdate(query1);
        stmt.executeUpdate(query2);
        // Create a Savepoint for the two inserts so far
        sp1 = conn.setSavepoint();
      } catch (SQLException ex) {
        // If we did not successfully insert two records in DISCOUNT_CODE,
        // rollback the transaction and throw an exception
        conn.rollback();
        throw new SQLException("Failed to insert two new records in DISCOUNT_CODE.", ex);
      }

      String query3 = "INSERT INTO Discount_Code VALUES ('Q', 3.00)";
      try {
        stmt.executeUpdate(query3);
        conn.commit(); // If the whole thing worked, commit
      } catch (SQLException ex) {
        // If the the third insert failed, that's ok, rollback to the
        // Savepoint (rollback the insert ('Q', 3.00)) and commit from there.
        conn.rollback(sp1);
        conn.commit();
      }
      logSQLWarning(conn.getWarnings()); // example how to log SQL Warnings
    } catch (SQLException ex) {
      logSQLException(ex);
    }
  }

  private Connection getDatabaseConnectionWithAutoCommitDisabled() throws SQLException {
    Connection conn = pool.getDatabaseConnection();
    conn.setAutoCommit(false);
    return conn;
  }

  public String getPoolingDriverInfo() {
    return pool.getPoolInfo();
  }

}
