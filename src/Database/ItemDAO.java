
package Database;

import java.util.List;

public interface ItemDAO {

    public List<Item> getAllItems();
    
    public List<Item> getItemsRange(int start, int end);

    public List<Item> search(String name);
    
    public Item getItem(int itemNumber);

    public boolean updateItem(Item player);

    public boolean deleteItem(int itemNumber);
    
    public boolean deleteItem(String itemName);

    public boolean addItem(Item item);

    public boolean doesItemExist(int itemNumber);
}
