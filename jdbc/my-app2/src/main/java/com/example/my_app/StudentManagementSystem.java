package com.example.my_app;




import java.sql.*;
import java.util.Scanner;

public class StudentManagementSystem {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/School";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "id2241002090";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {
            
            System.out.println("Connected to database successfully!");

            while (true) {
                System.out.println("\nStudent Management System");
                System.out.println("1. Create Student Table");
                System.out.println("2. Add New Student");
                System.out.println("3. View All Students");
                System.out.println("4. Update Student");
                System.out.println("5. Delete Student");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        createStudentTable(connection);
                        break;
                    case 2:
                        insertStudent(connection, scanner);
                        break;
                    case 3:
                        readStudents(connection);
                        break;
                    case 4:
                        updateStudent(connection, scanner);
                        break;
                    case 5:
                        deleteStudent(connection, scanner);
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

    private static void createStudentTable(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                     "id INT AUTO_INCREMENT PRIMARY KEY," +
                     "name VARCHAR(100) NOT NULL," +
                     "age INT NOT NULL," +
                     "grade VARCHAR(10) NOT NULL)";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Student table created successfully!");
        }
    }

    private static void insertStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter student age: ");
        int age = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        
        System.out.print("Enter student grade: ");
        String grade = scanner.nextLine();

        String sql = "INSERT INTO students (name, age, grade) VALUES (?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setString(3, grade);
            int rowsInserted = statement.executeUpdate();
            
            if (rowsInserted > 0) {
                System.out.println("Student added successfully!");
            }
        }
    }

    private static void readStudents(Connection connection) throws SQLException {
        String sql = "SELECT * FROM students";
        
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            
            System.out.println("\nStudent List:");
            System.out.println("ID\tName\tAge\tGrade");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String grade = resultSet.getString("grade");
                
                System.out.printf("%d\t%s\t%d\t%s%n", id, name, age, grade);
            }
        }
    }

    private static void updateStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter student ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter new age: ");
        int age = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        
        System.out.print("Enter new grade: ");
        String grade = scanner.nextLine();

        String sql = "UPDATE students SET name = ?, age = ?, grade = ? WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setString(3, grade);
            statement.setInt(4, id);
            
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("No student found with ID: " + id);
            }
        }
    }

    private static void deleteStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter student ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        String sql = "DELETE FROM students WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("No student found with ID: " + id);
            }
        }
    }
}