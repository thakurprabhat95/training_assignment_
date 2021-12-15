package com.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtil {
	
	public static void select(Connection connection, 
            String sql, 
            ResultSetProcessor processor, 
            Object... params) {
try (PreparedStatement ps = connection.prepareStatement(sql)) {
int cnt = 0;
for (Object param : params) {
  ps.setObject(++cnt, param);
}

try (ResultSet rs = ps.executeQuery()) {
  long rowCnt = 0;
  while (rs.next()) {
      processor.process(rs, rowCnt++);
  }
} 
} catch (SQLException e) {
 e.printStackTrace();
}

	}
	
	
public static void select2(Connection connection,String sql,ResultSetProcessor processor) 
	{
int count=0;	
try 
{
PreparedStatement ps = connection.prepareStatement(sql);
ps.setString(1,"CSE");
ResultSet rs = ps.executeQuery(); 
while (rs.next()) 
  {
	 long rowCnt = 0;
	  processor.process(rs, rowCnt++); 
	  count=count+1;  
  }  
System.out.println("The Total Number of CSE Students are "+count);
System.out.println("--------------------------------------------");
} 
catch (SQLException e) 
{
  System.out.println(e);
}  
}


public static void select3(Connection connection,String sql,ResultSetProcessor processor) 
{
int count=0;	
int sum=0;
try 
{
PreparedStatement ps = connection.prepareStatement(sql);  
ResultSet rs = ps.executeQuery(); 
while (rs.next()) 
{
	 long rowCnt = 0;
	  processor.process(rs, rowCnt++);
	  count=count+1;
      sum=sum+rs.getInt(5);
     
}  
System.out.println("The Total Number of CSE Students are "+count);
System.out.println("Total Amount paid by All Students "+sum);
} 
catch (SQLException e) 
{
System.out.println(e);
}  
}	
}
