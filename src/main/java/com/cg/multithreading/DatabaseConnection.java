package com.cg.multithreading;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
public class DatabaseConnection {
    public  Connection getConnecton() {
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
        try {
            connection= DriverManager.getConnection(jdbcURL,userName,password);
        } catch (SQLException e) {
            System.out.println("connection failed");
        }
        return connection;
    }
}
