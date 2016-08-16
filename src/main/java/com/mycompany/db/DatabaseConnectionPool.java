/**
 * Copyright (c) 2016 Company.
 * All rights reserved.
 */
package com.mycompany.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnectionPool {

  Connection getDatabaseConnection() throws SQLException;

}
