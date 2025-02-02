package com.example.my_app;

/**
 * Hello world!
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "id2241002090";
        
        
        //there is a industry standard level code
        //prepaired statement--> here we give value to database in run time
        //there we can set delete all thing we can done in run time
        
        
        //there is also code for update the database
        //also code for delete data from database
        //working with date operation on mysql date format
        
        

        // SQL query
        
        String query = "SELECT * FROM student";

        try  {
        	Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Load the JDBC driver (not required for modern JDBC drivers)
            //Class.forName("com.mysql.cj.jdbc.Driver");

            // Process the result set
            while (resultSet.next()) {
                int id = resultSet.getInt("sid");
                String name = resultSet.getString("sname");
                System.out.println("ID: " + id + ", Name: " + name);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}