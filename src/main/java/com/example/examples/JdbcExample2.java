package com.example.examples;

import java.sql.*;

public class JdbcExample2 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement pstmt = null;
        
        // Step 1: Load the Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Driver loaded successfully...");
        
        // Step 2: Create the Connection
        connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/jdbc", 
            "root", 
            "newpassword"  // Empty password
        );
        System.out.println("Connection Created successfully...");
        
        // Step 3: Execute queries
        statement = connection.createStatement();
        
        // Create table with primary key
        String createTable = "CREATE TABLE IF NOT EXISTS emp (" +
                            "id INT PRIMARY KEY, " +  // Added PRIMARY KEY
                            "name VARCHAR(50), " +
                            "salary DOUBLE)";
        statement.executeUpdate(createTable);
        System.out.println("Table created successfully...");
        
        // Clear existing data to avoid duplicates
        statement.executeUpdate("DELETE FROM emp");
        System.out.println("Cleared existing data...");
        
        // Insert static data
        String insertStatic = "INSERT INTO emp VALUES(111,'ratan',10000.45)";
        statement.executeUpdate(insertStatic);
        System.out.println("Static data inserted...");
        
        // Using PreparedStatement for dynamic queries
        String insertDynamic = "INSERT INTO emp VALUES(?, ?, ?)";
        pstmt = connection.prepareStatement(insertDynamic);
        
        // Insert multiple dynamic records
        int[] ids = {112, 113, 114, 115};
        String[] names = {"anu", "durga", "sravya", "ramya"};
        double[] salaries = {15000.50, 25000.75, 30000.00, 35000.25};
        
        for (int i = 0; i < ids.length; i++) {
            pstmt.setInt(1, ids[i]);
            pstmt.setString(2, names[i]);
            pstmt.setDouble(3, salaries[i]);
            pstmt.executeUpdate();
        }
        System.out.println("Dynamic data inserted using PreparedStatement...");
        
        // FIXED: Correct salary update logic
        String updateSalary = "UPDATE emp SET salary = " +
                            "CASE " +
                            "WHEN salary > 50000 THEN salary + 500 " +
                            "WHEN salary > 10000 AND salary <= 50000 THEN salary + 200 " +  // Fixed condition
                            "ELSE salary " +
                            "END";
        int updatedRows = statement.executeUpdate(updateSalary);
        System.out.println("Salaries updated successfully for " + updatedRows + " rows...");
        
        // Display final data
        System.out.println("\nFinal Employee Data:");
        ResultSet rs = statement.executeQuery("SELECT * FROM emp ORDER BY id");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id") + 
                             ", Name: " + rs.getString("name") + 
                             ", Salary: " + rs.getDouble("salary"));
        }
        rs.close();
        
        // Step 4: Release resources
        if (pstmt != null) pstmt.close();
        if (statement != null) statement.close();
        if (connection != null) connection.close();
        System.out.println("Resources released successfully...");
    }
}