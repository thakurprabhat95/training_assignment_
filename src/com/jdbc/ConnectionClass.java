package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {

	
	static Connection c;
    static
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException cnfe)
        {
            System.out.println("Class Driver is Missing");
        }

    }
    public static Connection getConnection(String url,String username,String password)
    {
        try
        {
            c= DriverManager.getConnection(url,username,password);
        }
        catch(SQLException sqle)
        {
            System.out.println("Error in SQL");
        }
        return c;
    }
}


