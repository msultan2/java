/**
 * Copyright (c) 2016 Company.
 * All rights reserved.
 */
package com.mycompany.db;

import com.ninja_squad.dbsetup.DbSetup;
import static com.ninja_squad.dbsetup.Operations.*;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import java.sql.ResultSet;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class DatabaseTest {

  @BeforeClass
  public static void setUpClass() {
    // Always start derby database first in order to connect
    // or if running for the first time
    // Netbeans -> Services -> Databases -> Java DB -> Create Sample Database...: sample
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
    Operation operation = sequenceOf(CommonOperations.DELETE_ALL, CommonOperations.INSERT_REFERENCE_DATA);
    DbSetup dbSetup = new DbSetup(new DriverManagerDestination("jdbc:derby:sample", "app", "app"), operation);
    dbSetup.launch();
  }

  @After
  public void tearDown() {
    Database.DERBY.constructAndUseStatement("DELETE FROM Discount_Code WHERE DISCOUNT_CODE LIKE 'R'");
  }

  @Test
  public void testSubmitQueriesAndReadResults() {
    String result = Database.DERBY.submitQueryAndGetResult("SELECT * FROM Customer WHERE NAME LIKE 'Bob%'");
    assertEquals("36 Bob Hosting Corp. www.bobhostcorp.example.com 650-555-0160", result);
  }

  @Test
  public void testConstructAndUseStatement() {
    String result = Database.DERBY.constructAndUseStatement("SELECT * FROM Customer WHERE NAME LIKE 'Bob%'");
    assertEquals("36 Bob Hosting Corp. www.bobhostcorp.example.com 650-555-0160", result);
  }

  @Test
  public void testConstructAndUseStatementUpdateOneRow() {
    String result = Database.DERBY.constructAndUseStatement("INSERT INTO Discount_Code VALUES ('R', 4.00)");
    assertEquals("1 rows affected.", result);
  }

  @Ignore("Not implemented yet")
  @Test
  public void testGetInformationAboutResultSet() {
    System.out.println("getInformationAboutResultSet");
    Database instance = null;
    instance.getInformationAboutResultSet();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  @Ignore("Not implemented yet")
  @Test
  public void testPrintReport() {
    System.out.println("printReport");
    Database instance = null;
    instance.printReport();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  @Ignore("Not implemented yet")
  @Test
  public void testMoveAroundResultSets() {
    System.out.println("moveAroundResultSets");
    Database instance = null;
    instance.moveAroundResultSets();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  @Test
  public void testDemonstrateGetRowCount() {
    assertEquals(13, Database.DERBY.demonstrateGetRowCount());
  }

  @Ignore("Not implemented yet")
  @Test
  public void testGetRowCount() throws Exception {
    System.out.println("getRowCount");
    ResultSet rs = null;
    int expResult = 0;
    int result = Database.getRowCount(rs);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  @Ignore("Not implemented yet")
  @Test
  public void testUpdateResultSet() {
    System.out.println("updateResultSet");
    Database instance = null;
    instance.updateResultSet();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  @Ignore("Not implemented yet")
  @Test
  public void testInsertNewRow() {
    System.out.println("insertNewRow");
    Database instance = null;
    instance.insertNewRow();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  @Ignore("Not implemented yet")
  @Test
  public void testGetInformationAboutDatabase() {
    System.out.println("getInformationAboutDatabase");
    Database instance = null;
    instance.getInformationAboutDatabase();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  @Ignore("Not implemented yet")
  @Test
  public void testUsePreparedStatement() {
    System.out.println("usePreparedStatement");
    Database instance = null;
    instance.usePreparedStatement();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  @Ignore("Not implemented yet")
  @Test
  public void testWorkWithRowSet() {
    System.out.println("workWithRowSet");
    Database instance = null;
    instance.workWithRowSet();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  @Ignore("Not implemented yet")
  @Test
  public void testDemonstrateTransaction() {
    System.out.println("demonstrateTransaction");
    Database instance = null;
    instance.demonstrateTransaction();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  @Ignore("Not implemented yet")
  @Test
  public void testDemonstrateTransactionUsingSavepoints() {
    System.out.println("demonstrateTransactionUsingSavepoints");
    Database instance = null;
    instance.demonstrateTransactionUsingSavepoints();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  @Test
  public void testGetPoolingDriverInfo() {
    assertNotNull(Database.DERBY.getPoolingDriverInfo());
  }

}
