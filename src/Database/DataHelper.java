
package Database;

import java.sql.SQLException;


public class DataHelper {

    public static void handleSQLExceptions(SQLException e) {
        while (e != null) {

            //Vendor-dependent state codes, error codes and messages.
            System.out.println("SQLState:   " + e.getSQLState());
            System.out.println("Error Code:" + e.getErrorCode());
            System.out.println("Message:    " + e.getMessage());

            Throwable t = e.getCause();

            while (t != null) {
                System.out.println("Cause:" + t);

                //Iterate to the next cause.
                t = t.getCause();
            }

            //Iterate to the next SQL exception
            e = e.getNextException();
        }
    }
}
