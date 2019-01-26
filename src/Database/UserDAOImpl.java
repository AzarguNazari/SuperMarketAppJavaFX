package Database;

import static Database.DataHelper.handleSQLExceptions;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public List<User> getAllUsers() {
        try {

            //1: Create a preparated statement
            List<User> users = new ArrayList();
            String checkstmt = "SELECT * FROM USER";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            //2: Return a list of players
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(new User(rs.getString("c_name"), rs.getString("c_email"), rs.getString("c_password")));
            }

            return users;
        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return null;
    }

    @Override
    public User getUser(String email) {
        try {

            //1: Create a preparated statement
            User user = null;
            String checkstmt = "SELECT * FROM USER WHERE c_email=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            //2: Assign values
            stmt.setString(1, email);

            //3: Return the player
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(rs.getString("c_name"), rs.getString("c_email"), rs.getString("c_password"));
            }

            return user;
        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        try {

            //1: Create a preparated statement
            String update = "UPDATE USER SET c_name=?, c_password=? WHERE c_email=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(update);

            //2: Assign values to the ? in SQL statement
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());

            //3: Check if the update worked
            int res = stmt.executeUpdate();
            return (res > 0);
        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return false;
    }

    @Override
    public boolean deleteUser(String email) {
        try {

            //1: Create a preparated statement
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "DELETE FROM USER WHERE C_EMAIL = ?");

            //2: Assign values to the ? in SQL statement
            stmt.setString(1, email);

            //3: Check if the deletion worked
            int res = stmt.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return false;
    }

    @Override
    public boolean addUser(User user) {
        try {

            //1: Create a preparated statement
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO USER(c_name,c_email,c_password) VALUES(?,?,?)");

            //2: Assign values
            statement.setString(1, user.getFullName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());

            //3: Check if at least one record has been affected
            return statement.executeUpdate() > 0;

        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }

        return false;
    }

    @Override
    public boolean doesUserExist(String email, String password) {
        try {

            //1: Create a preparated statement
            String checkstmt = "SELECT COUNT(*) FROM USER WHERE c_email=? and c_password=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            //2: Assign values
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            //3: Check if the player exists
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return (count > 0);
            }

        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return false;
    }

    @Override
    public boolean loginUser(String email, String password) {
        try {

            //1: Create a preparated statement
            String checkstmt = "SELECT COUNT(*) FROM USER WHERE c_email=? AND c_PASSWORD=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            //2: Assign values
            stmt.setString(1, email);
            stmt.setString(2, password);

            //3: Return the player
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return (count > 0);
            }

        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return false;
    }

    @Override
    public boolean isEmailUnique(String email) {
        try {

            //1: Create a preparated statement
            String checkstmt = "SELECT COUNT(*) FROM USER WHERE C_EMAIL=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            //2: Assign values
            stmt.setString(1, email);

            //3: Return the player
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return (count== 0);
            }

        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return false;
    }

}
