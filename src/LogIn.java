/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Database.DatabaseHandler;
import Database.User;
import Database.UserDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Hazar Gul
 */
public class LogIn implements Initializable {

    @FXML
    private AnchorPane body;

    //------ Sign In Part Variables ---    
    @FXML
    private AnchorPane signin;
    @FXML
    private TextField email_signin;
    @FXML
    private PasswordField password_signin;

    //------- Sign Up Part Variables -------
    @FXML
    private AnchorPane signup;
    @FXML
    private TextField name_signup;
    @FXML
    private TextField email_signup;
    @FXML
    private PasswordField password_signup;

    //------ User Type
    @FXML
    private ToggleGroup userType;
    @FXML
    private RadioButton user;
    @FXML
    private RadioButton admin;

    //------ Error Box ------------
    @FXML
    private AnchorPane errorMessageBox;
    @FXML
    private Label errorMessage;

    @FXML
    private void signin(ActionEvent event) throws SQLException, IOException {
        String email = email_signin.getText().trim();
        String pass = password_signin.getText().trim();

        UserDAOImpl users = new UserDAOImpl();

        if (user.isSelected()) {
            if (user.isSelected()) {
                DatabaseHandler.setCurrentUsername(users.getUser(email));
                changePage(event, "MainPage.fxml");
            } else {
                displayErrorMessage("Sorry, you have entered wrong username or password");
            }
        } else {
            if (email.equalsIgnoreCase("admin") && pass.equalsIgnoreCase("admin")) {
                changePage(event, "Admin.fxml");
            }
            else{
                displayErrorMessage("Sorry, the Admin username and password is wrong");
            }
        }
    }
    
    private void changePage(ActionEvent event, String page) throws IOException{
        Parent tableViewParent = FXMLLoader.load(getClass().getResource(page));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void signupSwitch(ActionEvent event) {
        signin.setVisible(false);
        signup.setVisible(true);
        Util.FadeInTransition(signup);
    }

    @FXML
    private void signup(ActionEvent event) throws SQLException {
        String firstName = name_signup.getText().trim();
        String email = email_signup.getText().trim();
        String pass = password_signup.getText().trim();

        UserDAOImpl users = new UserDAOImpl();
        if (users.isEmailUnique(email)) {
            User tempUser = new User(firstName, email, pass);
            users.addUser(tempUser);
            System.out.println(tempUser);
        } else {
            displayErrorMessage("Sorry, this email is already used");
        }
    }

    @FXML
    private void forgot(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Util.FadeInTransition(body);
        signup.setVisible(false);
        errorMessageBox.setVisible(false);
    }

    private void displayErrorMessage(String errorText) {
        errorMessage.setText(errorText);
        errorMessageBox.setVisible(true);
        Util.FadeInTransition(errorMessageBox);
        Thread errorThread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ex) {
                } finally {
                    Util.hideFadeOut(errorMessageBox);
                }
            }
        };
        errorThread.start();
    }

}
