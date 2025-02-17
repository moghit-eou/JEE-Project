package com.denbondd.restaurant.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection_jdbc {
    private Connection connection;
    private String url = "jdbc:mysql://localhost/restaurant";  // Ensure correct URL
    private String username = "root";
    private String password = "root";

    public static int counter = 0 ;
    public Connection_jdbc(String opeartion) throws SQLException , ClassNotFoundException  {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish connection
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established " + opeartion + " Num = " + ++counter);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
        }
    }
    public Connection_jdbc() throws SQLException , ClassNotFoundException  {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish connection
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
