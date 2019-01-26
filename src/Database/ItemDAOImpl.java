package Database;

import static Database.DataHelper.handleSQLExceptions;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public List<Item> getAllItems() {
        try {
            //1: Create a preparated statement
            List<Item> items = new ArrayList();
            String checkstmt = "SELECT * FROM ITEM";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            //2: Return a list of players
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                items.add(new Item(rs.getString("name"), rs.getInt("quantity"), rs.getInt("weight"), rs.getInt("price"), rs.getString("description")));
            }

            return items;
        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return null;
    }

    @Override
    public List<Item> search(String name) {
        List<Item> items = new ArrayList<>();
        try {

            //1: Create a preparated statement
            String checkstmt = "SELECT * FROM item WHERE name like ?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            //2: Assign values
            stmt.setString(1, "%" + name + "%");

            //3: Return the player
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Item temp = new Item(rs.getString("name"), rs.getInt("quantity"), rs.getInt("weight"), rs.getInt("price"), rs.getString("description"));
                temp.setId(rs.getInt("id"));
                items.add(temp);
            }
        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return items;
    }

    @Override
    public Item getItem(int itemNumber) {
         try {

            //1: Create a preparated statement
            Item item = null;
            String checkstmt = "SELECT * FROM item WHERE id=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            //2: Assign values
            stmt.setInt(1, itemNumber);

            //3: Return the player
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                item = new Item(rs.getString("name"), rs.getInt("quantity"), rs.getInt("weight"), rs.getInt("price"), rs.getString("description"));
                item.setId(rs.getInt("id"));
            }
            return item;
        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return null;
    }

    @Override
    public boolean updateItem(Item item) {
        try {

            //1: Create a preparated statement
            String update = "UPDATE ITEM SET name=?, price=?, weight=?, quantity=?, description=? WHERE id=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(update);

            //2: Assign values to the ? in SQL statement
            stmt.setString(1, item.getName());
            stmt.setInt(2, item.getCost());
            stmt.setInt(3, item.getWeight());
            stmt.setInt(4, item.getQuantity());
            stmt.setString(5, item.getDescription());
            stmt.setInt(6, item.getId());

            //3: Check if the update worked
            int res = stmt.executeUpdate();
            return (res > 0);
        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return false;
    }

    @Override
    public boolean deleteItem(int itemNumber) {
        try {

            //1: Create a preparated statement
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "DELETE FROM ITEM WHERE id = ?");

            //2: Assign values to the ? in SQL statement
            stmt.setInt(1, itemNumber);

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
    public boolean deleteItem(String item) {
        try {

            //1: Create a preparated statement
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "DELETE FROM ITEM WHERE name = ?");

            //2: Assign values to the ? in SQL statement
            stmt.setString(1, item);

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
    public boolean addItem(Item item) {
        try {

            //1: Create a preparated statement
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO ITEM(name,price,weight,quantity,description) VALUES(?,?,?,?,?)");

            //2: Assign values
            statement.setString(1, item.getName());
            statement.setInt(2, item.getCost());
            statement.setInt(3, item.getWeight());
            statement.setInt(4, item.getQuantity());
            statement.setString(5, item.getDescription());

            //3: Check if at least one record has been affected
            return statement.executeUpdate() > 0;

        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }

        return false;

    }

    @Override
    public boolean doesItemExist(int itemNumber) {
        try {

            //1: Create a preparated statement
            String checkstmt = "SELECT COUNT(*) FROM ITEM WHERE id=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            //2: Assign values
            stmt.setInt(1, itemNumber);

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
    public List<Item> getItemsRange(int start, int end) {
        try {
            //1: Create a preparated statement
            List<Item> items = new ArrayList();
            String checkstmt = "SELECT * FROM ITEM where id between " + start + " and " + end;
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);

            //2: Return a list of players
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                items.add(new Item(rs.getString("name"), rs.getInt("quantity"), rs.getInt("weight"), rs.getInt("price"), rs.getString("description")));
            }

            return items;
        } catch (SQLException ex) {
            handleSQLExceptions(ex);
        }
        return null;
    }

}
