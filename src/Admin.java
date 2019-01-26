
import Database.Item;
import Database.ItemDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Hazar Gul
 */
public class Admin implements Initializable {

    @FXML
    private AnchorPane analysisPane;
    @FXML
    private AnchorPane settingPane;

    //------------ Success Message --------
    @FXML
    private AnchorPane successMessageBox;
    @FXML
    private Text successMessage;

    //--------------- Error Message --------
    @FXML
    private AnchorPane errorMessageBox;
    @FXML
    private Text errorMessage;

    //----------- Add New Item ----------------
    @FXML
    private AnchorPane addNewItemPane;
    @FXML
    private TextField itemName_add;
    @FXML
    private Spinner itemQuantity_add;
    @FXML
    private Spinner itemWeight_add;
    @FXML
    private Spinner itemCost_add;
    @FXML
    private TextArea itemDescription;

    //----------- Change Item Stock ----------------
    @FXML
    private AnchorPane changeStockPane;
    @FXML
    private AnchorPane foundItem;
    @FXML
    private TextField itemName_found;
    @FXML
    private TextField itemNumber_search;
    @FXML
    private Spinner itemQuanity_founds;
    @FXML
    private Spinner cost_founds;
    @FXML
    private Spinner weight_founds;
    @FXML
    private TextArea description_found;
    private Item currentFoundItem = null;

    //------ List All Items
    @FXML
    private AnchorPane listAllItem;
    @FXML
    private ListView itemList;

    @FXML
    private void showAddNewItemPanel(ActionEvent event) {

        itemQuantity_add.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1));
        itemWeight_add.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1));
        itemCost_add.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1));

        // Only the pane for adding new Item should be visible
        visibility(true, false, false, false, false);
    }

    @FXML
    private void addItem(ActionEvent event) {
        String itemName = itemName_add.getText().trim();
        int itemQuantity = (int) itemQuantity_add.getValue();
        int itemWeight = (int) itemWeight_add.getValue();
        int itemCost = (int) itemCost_add.getValue();
        String description = itemDescription.getText().trim();

        if (itemName.length() == 0) {
            displayError("Please Enter Item Name");
            return;
        }

        ItemDAOImpl itemDAO = new ItemDAOImpl();
        Item tempTemp = new Item(itemName, itemQuantity, itemWeight, itemCost, description);
        if (itemDAO.addItem(tempTemp)) {
            displaySuccess("Item Name : " + tempTemp.getName() + "\n"
                    + "Price : " + tempTemp.getCost() + "\n"
                    + "Weight : " + tempTemp.getWeight() + "\n"
                    + "Quantity : " + tempTemp.getQuantity() + "\n"
                    + "Description : " + tempTemp.getDescription(), "add");
        } else {
            displayError("Sorry the item is not stored");
        }
    }

    @FXML
    private void showChangeStockPanel(ActionEvent event) {

        foundItem.setVisible(false);

        // Only the changeStock pane should be visible
        visibility(false, true, false, false, false);
    }

    @FXML
    private void searchItemNumber(ActionEvent event) {
        ItemDAOImpl itemDAO = new ItemDAOImpl();

        try {
            int itemNumber = Integer.valueOf(itemNumber_search.getText().trim());
            currentFoundItem = itemDAO.getItem(itemNumber);
            if (currentFoundItem != null) {
                itemName_found.setText(currentFoundItem.getName());
                itemQuanity_founds.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, currentFoundItem.getQuantity()));
                cost_founds.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, currentFoundItem.getCost()));
                weight_founds.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, currentFoundItem.getWeight()));
                description_found.setText(currentFoundItem.getDescription());
                foundItem.setVisible(true);
                Util.FadeInTransition(foundItem);
            }
            itemNumber_search.clear();
        } catch (Exception ex) {
            displayError("Item Number should be only digit");
        }
    }

    @FXML
    private void updateItemStock(ActionEvent event) {
        String itemName = itemName_found.getText().trim();
        int cost = (int) cost_founds.getValue();
        int weight = (int) weight_founds.getValue();
        int quantity = (int) itemQuanity_founds.getValue();
        String description = description_found.getText().trim();

        ItemDAOImpl itemDAO = new ItemDAOImpl();
        if (currentFoundItem != null) {
            currentFoundItem.setName(itemName);
            currentFoundItem.setCost(cost);
            currentFoundItem.setWeight(weight);
            currentFoundItem.setQuantity(quantity);
            currentFoundItem.setDescription(description);
            if (itemDAO.updateItem(currentFoundItem)) {
                displaySuccess("", "update");
            } else {
                displayError("Sorry the item can not be updated");
            }
        } else {
            displayError("Sorry, item could not be found");
        }
    }

    @FXML
    private void removeStockItem(ActionEvent event) {
        ItemDAOImpl itemDAO = new ItemDAOImpl();
        if (itemDAO.deleteItem(itemName_found.getText())) {
            displaySuccess(itemName_found.getText() + " is removed from database", "remove");
        } else {
            displayError("Sorry the item cannot be removed");
        }
    }

    @FXML
    private void showAnalysisPanel(ActionEvent event) {
        visibility(false, false, true, false, false);
    }

    @FXML
    private void showAllItems(ActionEvent event) {
        itemList.getItems().clear();
        ItemDAOImpl items = new ItemDAOImpl();
        items.getAllItems().forEach(item -> {
            String print = "Item Name : " + item.getName() + "\n"
                    + "Price : " + item.getCost() + "\n"
                    + "Weight : " + item.getWeight() + "\n"
                    + "Quantity : " + item.getQuantity() + "\n"
                    + "Description : " + item.getDescription();
            itemList.getItems().add(print);

        });
        itemList.setCellFactory(cell -> {
            return new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item);
                        getStyleClass().add("allList");
                        // decide to add a new styleClass
                        // getStyleClass().add("costume style");
                        // decide the new font size
                    }
                }
            };
        });
        visibility(false, false, false, true, false);
    }

    @FXML
    private void showSettingPanel(ActionEvent event) {

        // Only the setting pane should be visible
        visibility(false, false, false, false, true);
    }

    @FXML
    private void signOut(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // All the panes should be closed at first
        visibility(false, false, false, false, false);
        errorMessageBox.setVisible(false);
        successMessageBox.setVisible(false);
    }

    /**
     *
     * @param addNewItem Is the addNewItem Panel should be visible or not
     * @param changeStock Is the changeStock panel should be visible or not
     * @param analysis Is the analysis panel should be visible or not
     * @param setting Is the setting panel should be visible or not
     */
    private void visibility(boolean addNewItem, boolean changeStock, boolean analysis, boolean allItem, boolean setting) {
        addNewItemPane.setVisible(addNewItem);
        changeStockPane.setVisible(changeStock);
        analysisPane.setVisible(analysis);
        listAllItem.setVisible(allItem);
        settingPane.setVisible(setting);
        if (addNewItem) {
            Util.FadeInTransition(addNewItemPane);
        } else if (changeStock) {
            Util.FadeInTransition(changeStockPane);
        } else if (analysis) {
            Util.FadeInTransition(analysisPane);
        } else if (allItem) {
            Util.FadeInTransition(listAllItem);
        } else if (setting) {
            Util.FadeInTransition(settingPane);
        }

    }

    /**
     *
     * @param message The message to display as error message
     */
    private void displayError(String message) {
        errorMessage.setText(message);
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

    private void displaySuccess(String displayMessage, String operationType) {
        String message = "";
        if (operationType.equalsIgnoreCase("add")) {
            message += "Added Successfully :\n";
        } else if (operationType.equalsIgnoreCase("remove")) {
            message += "Removed Successfully :\n";
        } else if (operationType.equalsIgnoreCase("update")) {
            message += "Updated Successfully :\n";
        }
        message += displayMessage;
        successMessage.setText(message);
        successMessageBox.setVisible(true);
        Util.FadeInTransition(successMessageBox);

        Thread errorThread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ex) {
                } finally {
                    Util.hideFadeOut(successMessageBox);
                }
            }
        };
        errorThread.start();
    }
}
