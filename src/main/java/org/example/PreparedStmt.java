package org.example;

import java.sql.*;

public class PreparedStmt {
    public static void main(String[] args) throws SQLException {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            // Get a connection to database
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo","student","student");

            //prepare statement
            myStmt = myConn.prepareStatement("select * from employees where salary > ? and department = ?");

            //set the parameters
            myStmt.setDouble(1,80000);
            myStmt.setString(2,"Legal");

            //execute query
            myRs = myStmt.executeQuery();

            //display the result set
            display(myRs);

            //
            //reuse the prepaerd statement where salary is <20000 and department HR
            //
            System.out.println("Reusing the prepared statement salary<20000 department HR\n");
            myStmt.setDouble(1,20000);
            myStmt.setString(2,"HR");

            //execute reuse the prepared statement
            myRs = myStmt.executeQuery();

            //display the result
            display(myRs);
        }
        catch (Exception exe){
            exe.printStackTrace();
        }
        finally {
            if (myRs != null){
                myRs.close();
            }
            if (myStmt != null){
                myStmt.close();
            }
            if (myConn != null){
                myConn.close();
            }
        }

    }

    private static void display(ResultSet myRs) throws SQLException {
        while (myRs.next()) {
            String lastName = myRs.getString("last_name");
            String firstName = myRs.getString("first_name");
            double salary = myRs.getDouble("salary");
            String department = myRs.getString("department");

            System.out.printf("%s, %s, %.2f, %s\n", lastName, firstName, salary, department);
        }
    }
}
