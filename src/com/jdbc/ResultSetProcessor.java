package com.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetProcessor {

	 public void process(ResultSet resultSet, 
             long currentRow) 
             throws SQLException;

}
