/**
 * Copyright (c) 2016 Company.
 * All rights reserved.
 */
package com.mycompany.db;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class HikariCpConnectionPool implements DatabaseConnectionPool {

  private final HikariDataSource ds;

  /**
   * Creates Database Connection Pool using HikariCP.
   *
   * Uses sane defaults that perform well in most deployments without additional tweaking
   * https://github.com/brettwooldridge/HikariCP
   *
   * autoCommit: true
   * connectionTimeout: 30 seconds
   * idleTimeout: 10 minutes
   * maxLifetime: 30 minutes
   * minimumIdle: same as maximumPoolSize
   * maximumPoolSize: 10
   * poolName: auto-generated
   * ... other infrequently used
   *
   * @param dbParameters
   */
  public HikariCpConnectionPool(final DatabaseParameters dbParameters) {
    ds = new HikariDataSource();
    ds.setJdbcUrl(dbParameters.getURL());
    ds.setUsername(dbParameters.getUsername());
    ds.setPassword(dbParameters.getPassword());
  }

  @Override
  public Connection getDatabaseConnection() throws SQLException {
    return ds.getConnection();
  }

  @Override
  public String getPoolInfo() {
    return ds.getPoolName();
  }

}
