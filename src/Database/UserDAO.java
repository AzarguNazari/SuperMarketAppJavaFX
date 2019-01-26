
package Database;

import java.util.List;

public interface UserDAO {

    public List<User> getAllUsers();

    public User getUser(String username);

    public boolean updateUser(User player);

    public boolean deleteUser(String username);

    //Registers a new player
    public boolean addUser(User player);

    public boolean doesUserExist(String email, String password);
    
    public boolean isEmailUnique(String email);

    //Logs in an existing player 
    public boolean loginUser(String email, String password);

}
