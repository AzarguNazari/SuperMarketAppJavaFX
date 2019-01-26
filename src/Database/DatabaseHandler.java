package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DatabaseHandler {

    private static DatabaseHandler handler = null;
    private static User currentUsername;
    
    private static Connection conn = null;
    private static Statement stmt = null;

    static {
        createConnection();
    }

    private DatabaseHandler() {
    }

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    //Connects to this database
    private static void createConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarket", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cant load database", "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        System.out.println("Works!");
    }

    //Executes query
    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        } finally {
        }
        return result;
    }

    //Unknown
    public boolean execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        } finally {
        }
    }
    
    public Connection getConnection() {
        return conn;
    }

    public static User getCurrentUsername() {
        return currentUsername;
    }

    public static void setCurrentUsername(User currentUsername) {
        DatabaseHandler.currentUsername = currentUsername;
    }

    public static void main(String[] args) throws Exception {
        DatabaseHandler.getInstance();

    }
    
    
//
//    //Make Generic
//    public ObservableList<PieChart.Data> getBookGraphStatistics() {
//        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
//        try {
//            String qu1 = "SELECT COUNT(*) FROM BOOK";
//            String qu2 = "SELECT COUNT(*) FROM ISSUE";
//            ResultSet rs = execQuery(qu1);
//            if (rs.next()) {
//                int count = rs.getInt(1);
//                data.add(new PieChart.Data("Total Books (" + count + ")", count));
//            }
//            rs = execQuery(qu2);
//            if (rs.next()) {
//                int count = rs.getInt(1);
//                data.add(new PieChart.Data("Issued Books (" + count + ")", count));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return data;
//    }
//
//    //Make Generic
//    public ObservableList<PieChart.Data> getMemberGraphStatistics() {
//        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
//        try {
//            String qu1 = "SELECT COUNT(*) FROM MEMBER";
//            String qu2 = "SELECT COUNT(DISTINCT memberID) FROM ISSUE";
//            ResultSet rs = execQuery(qu1);
//            if (rs.next()) {
//                int count = rs.getInt(1);
//                data.add(new PieChart.Data("Total Members (" + count + ")", count));
//            }
//            rs = execQuery(qu2);
//            if (rs.next()) {
//                int count = rs.getInt(1);
//                data.add(new PieChart.Data("Active (" + count + ")", count));
//            }
//        } catch (SQLException e) {
//        }
//        return data;
//    }

    
}
