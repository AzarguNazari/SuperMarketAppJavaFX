/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

/**
 *
 * @author J
 */
public class DaoPatternDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAOImpl();
        
        for (User user: userDAO.getAllUsers()){
            System.out.println(user.toString());
        }

    }

}
