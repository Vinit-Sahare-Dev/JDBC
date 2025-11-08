package com.example.examples;

import java.sql.*;

public class JdbcExample1 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //step-1: Load the Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Driver loaded successfully...");
        
        //Step-2: Create the Connection
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/sleeping", 
            "root", 
            "root"
        );
        System.out.println("Connection Created successfully...");
        
        // Close connection
        connection.close();
    }
}