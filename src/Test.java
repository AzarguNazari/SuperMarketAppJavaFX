/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;

/**
 *
 * @author Hazar Gul
 */
public class Test {

    private static final String DB_DRIVER = "jdbc:mysql://localhost:3306/supermarket";
    private static final String DB_CONNECTION = "localhost";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;

        try {
           dbConnection = DriverManager.getConnection(
                             "jdbc:mysql://localhost:3306/supermarket", DB_USER,DB_PASSWORD);
            statement = dbConnection.createStatement();

            ResultSet set = statement.executeQuery("select * from user");
            
            if(set.next()){
                System.out.println(set.getString("name"));
            }
            else{
                System.out.println("Doesn't exist");
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }
    }

}
