package org.example;

import java.sql.*;

public class JdbcUpdateDemo {
    public static void main(String[] args) throws SQLException {
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        String dbUrl = "jdbc:mysql://localhost:3306/demo";
        String user = "student";
        String pass = "student";

        try {
            // Get a connection to database
            myConn = DriverManager.getConnection(dbUrl, user, pass);

            //Create a statement
            myStmt = myConn.createStatement();

            //Call helper method to display employee information
            System.out.println("Before the update...");
            displayEmployee(myConn,"utsav","bhattarai");

            //Update the employee
            System.out.println("\n Executing the update for utsav bhattarai");

            int rowsAffected = myStmt.executeUpdate(
                    "update employees "+ "set email='utsav@gmail.com'"+
                            "where last_name='bhattarai' and first_name='utsav'");

            //Call helper method to display the information
            System.out.println("After the update....");
            displayEmployee(myConn,"utsav","bhattarai");

        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        finally {
            close(myConn,myStmt,myRs);
        }
    }

    private static void displayEmployee(Connection myConn, String firstName, String lastName) throws SQLException {
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            // Prepare statement
            myStmt = myConn
                    .prepareStatement("select last_name, first_name, email from employees where last_name=? and first_name=?");

            myStmt.setString(1, lastName);
            myStmt.setString(2, firstName);

            // Execute SQL query
            myRs = myStmt.executeQuery();

            // Process result set
            while (myRs.next()) {
                String theLastName = myRs.getString("last_name");
                String theFirstName = myRs.getString("first_name");
                String email = myRs.getString("email");

                System.out.printf("%s %s, %s\n", theFirstName, theLastName, email);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            close(myStmt, myRs);
        }

    }

    private static void close(Connection myConn, Statement myStmt,
                              ResultSet myRs) throws SQLException {
        if (myRs != null) {
            myRs.close();
        }

        if (myStmt != null) {
            myStmt.close();
        }

        if (myConn != null) {
            myConn.close();
        }
    }

    private static void close(Statement myStmt, ResultSet myRs)
            throws SQLException {

        close(null, myStmt, myRs);
    }
}
