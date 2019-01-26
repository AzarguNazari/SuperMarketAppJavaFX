/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Database.Item;
import Database.ItemDAOImpl;
import Database.User;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Hazar Gul
 */
public class MainPage implements Initializable {

    @FXML
    private AnchorPane mainItemList_pane;
    @FXML
    private AnchorPane profile_pane;
    @FXML
    private AnchorPane aboutNahdi_pane;
    @FXML
    private AnchorPane search_pane;
    @FXML
    private AnchorPane order_pane;
    @FXML
    private AnchorPane addToListPane;
    @FXML
    private ListView itemList;
    @FXML
    private ListView searchList;
    @FXML
    private ListView orderList;
    @FXML
    private TextField search;
    @FXML
    private Button profile;
    @FXML
    private Text totalPurchase;
    

    private int startRange = 1;
    private int endRange = 10;

    @FXML
    private void signOut(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void shopping(ActionEvent event) {
        startRange = 1;
        endRange = 10;
        printItemList();
        visibility(true, false, false, false, false);
    }

    @FXML
    private void profileView(ActionEvent event) {
        visibility(false, true, false, false, false);
    }

    @FXML
    private void aboutNahdi(ActionEvent event) {
        visibility(false, false, true, false, false);
    }

    @FXML
    private void search(ActionEvent event) {
        String searchText = search.getText().toLowerCase().trim();
        ItemDAOImpl itemDAO = new ItemDAOImpl();
        searchList.getItems().clear();
        List<Item> foundItems = itemDAO.search(searchText);
        if (!foundItems.isEmpty()) {
            itemDAO.search(searchText).forEach(item -> searchList.getItems().add(item));
        }
        visibility(false, false, false, true, false);
    }

    @FXML
    private void nextItems(ActionEvent event) {

        startRange += 10;
        endRange += 10;

        printItemList();
    }

    @FXML
    private void previousItems(ActionEvent event) {
        if (startRange > 10) {
            startRange -= 10;
            endRange -= 10;
            printItemList();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        profile.setText(Database.DatabaseHandler.getCurrentUsername().getFullName());
        printItemList();
        selectitemOption(itemList);
        selectitemOption(searchList);
        // All the panes should be closed at first
        visibility(false, false, false, false, false);
    }

    private void selectitemOption(ListView list) {
        list.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            // Your action here
            addToListPane.setVisible(false);
            addToList();
            System.out.println("Selected item: " + newValue);
        });
    }

    private void printItemList() {
        itemList.getItems().clear();
        ItemDAOImpl items = new ItemDAOImpl();
        items.getItemsRange(startRange, endRange).forEach(item -> {
            itemList.getItems().add(item);
        });
    }

    /**
     *
     * @param addNewItem Is the addNewItem Panel should be visible or not
     * @param changeStock Is the changeStock panel should be visible or not
     * @param analysis Is the analysis panel should be visible or not
     * @param setting Is the setting panel should be visible or not
     */
    private void visibility(boolean itemList, boolean profile, boolean aboutNahdi, boolean search, boolean order) {
        mainItemList_pane.setVisible(itemList);
        profile_pane.setVisible(profile);
        aboutNahdi_pane.setVisible(aboutNahdi);
        search_pane.setVisible(search);
        order_pane.setVisible(order);
        if (itemList) {
            Util.FadeInTransition(mainItemList_pane);
        } else if (profile) {
            Util.FadeInTransition(profile_pane);
        } else if (aboutNahdi) {
            Util.FadeInTransition(aboutNahdi_pane);
        } else if (search) {
            Util.FadeInTransition(search_pane);
        } else if (order) {
            Util.FadeInTransition(order_pane);
        }

    }
    
    private void addToList(){
        addToListPane.setVisible(true);
        Util.FadeInTransition(addToListPane);

        Thread addToPurchaseList = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ex) {
                } finally {
                    Util.hideFadeOut(addToListPane);
                }
            }
        };
        addToPurchaseList.start();
    }
    
    @FXML
    private void addItem(ActionEvent event) {
        orderList.getItems().add(itemList.getSelectionModel().getSelectedItem());
        orderList.getItems();
        order_pane.setVisible(true);
        
        int total = 0;
        // Calculating total cost
        for(Object item: orderList.getItems()){
            total += ((Item) item).getCost();
        }
        totalPurchase.setText(String.valueOf(total));
    }
    
    @FXML
    private void cancelItem(ActionEvent event) {
        Util.hideFadeOut(addToListPane);
    }
    

}
