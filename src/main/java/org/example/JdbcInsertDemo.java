package org.example;

import java.sql.*;

public class JdbcInsertDemo {
    public static void main(String[] args) throws SQLException {
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        String dbUrl = "jdbc:mysql://localhost:3306/demo";
        String user = "student";
        String pass = "student";

        try {
            //Get a Connection to a database
            myConn = DriverManager.getConnection(dbUrl,user,pass);
            //Create a statement
            myStmt = myConn.createStatement();
            // Insert a new employee
            System.out.println("Inserting a new employee to database\n");

            int rowsAffected = myStmt.executeUpdate(
                    "insert into employees" + "(last_name,first_name,email,department,salary)"+
                    "values"+ "('Bhattarai','Ram','ram@gmail.com','HR',30000)");

            // Verify this by getting list of employees
            myRs = myStmt.executeQuery("select * from employees order by last_name");

            // Process the result set
            while (myRs.next()){
                System.out.println(myRs.getString("last_name")+ ","+myRs.getString("first_name"));
            }
        }
        catch (Exception exc ){
            exc.printStackTrace();
        }
        finally {
            if (myRs != null){
                myRs.close();
            }
        }
    }
}
