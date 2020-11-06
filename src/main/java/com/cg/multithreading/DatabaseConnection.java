package com.cg.multithreading;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
public class DatabaseConnection {
    private static int counterConnection=0;
    public static synchronized Connection getConnecton() {
        counterConnection++;
        String jdbcURL="jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
        String userName="root";
        String password="Nuzumaki1@";
        Connection connection = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("cannot find classpath");
        }
//        listDrivers();
       try  {
            System.out.println("Connection established with id "+ counterConnection);
            connection= DriverManager.getConnection(jdbcURL,userName,password);
            System.out.println("Connection successful with id "+counterConnection+" for thread "+Thread.currentThread().getName());
        } catch (SQLException e) {
            System.out.println("connection failed");
        }
        return connection;
    }
}
