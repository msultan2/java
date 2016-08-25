/**
 * Copyright (c) 2016 Company.
 * All rights reserved.
 */
package com.mycompany.db;

/**
 * Helper class to execute other classes.
 */
public final class JdbcLauncher {

  private JdbcLauncher() {}

  public static void main(final String[] args) {
    Database.DERBY.submitQueryAndGetResult("SELECT * FROM Customer WHERE NAME LIKE 'Bob Hosting Corp.'");
    /*
    Database.DERBY.constructAndUseStatement();
    Database.DERBY.getInformationAboutResultSet();
    Database.DERBY.printReport();
    Database.DERBY.moveAroundResultSets();
    Database.DERBY.demonstrateGetRowCount();
    Database.DERBY.updateResultSet();
    Database.DERBY.getInformationAboutDatabase();
    Database.DERBY.usePreparedStatement();
    Database.DERBY.workWithRowSet();
    Database.DERBY.demonstrateTransaction();
    Database.DERBY.demonstrateTransactionUsingSavepoints();
    Database.DERBY.getPoolingDriverInfo();
     */
  }

}
