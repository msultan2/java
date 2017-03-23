/**
 * Copyright (c) 2016 Company.
 * All rights reserved.
 */
package com.mycompany.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class Dbcp2ConnectionPool implements DatabaseConnectionPool {

  private static final String CONNECTION_POOL_NAME = "DBCP2Pool-1";
  private static final String CONNECTION_POOL_URL = "jdbc:apache:commons:dbcp:" + CONNECTION_POOL_NAME;

  private PoolingDriver driver;

  public Dbcp2ConnectionPool(final String jdbcUrl, final String username, final String password) {
    createPoolingDriver(jdbcUrl, username, password);
  }

  /**
   * Creates Database Connection Pool using Apache Commons DBCP.
   *
   * This minimizes the number of times you close and re-create Connection objects. Connections in
   * try-with-resources statements are taken from and returned to the connection pool. When a
   * connection is returned to the pool, setAutoCommit is set to true.
   * 
   * Uses defaults from GenericObjectPoolConfig:
   * (DEFAULT_MAX_IDLE=8, DEFAULT_MAX_TOTAL=8, DEFAULT_MIN_IDLE=0)
   *
   * Note: DBCP 2 compiles and runs under Java 7 only (JDBC 4.1).
   */
  private void createPoolingDriver(final String jdbcUrl, final String username, final String password) {
    ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(jdbcUrl, username, password);
    PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
    ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnectionFactory);
    poolableConnectionFactory.setPool(connectionPool);
    this.driver = new PoolingDriver();
    driver.registerPool(CONNECTION_POOL_NAME, connectionPool);
  }

  @Override
  public Connection getDatabaseConnection() throws SQLException {
    return DriverManager.getConnection(Dbcp2ConnectionPool.CONNECTION_POOL_URL);
  }

  @Override
  public String getPoolInfo() {
    return CONNECTION_POOL_NAME;
  }

}
