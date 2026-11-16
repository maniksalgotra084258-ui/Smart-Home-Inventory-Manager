package service;

import dao.InventoryItemDAO;
import model.InventoryItem;

import java.util.List;

public class InventoryItemService {

    private final InventoryItemDAO dao = new InventoryItemDAO();

    // ADD ITEM
    public boolean addItem(InventoryItem item) {

        if (item == null) {
            return false;
        }

        if (item.getItemName() == null ||
                item.getItemName().trim().isEmpty()) {
            System.out.println("Item name is required!");
            return false;
        }

        if (item.getQuantity() < 0 ||
                item.getPrice() < 0 ||
                item.getMinimumStock() < 0) {
            System.out.println("Invalid quantity, price or minimum stock!");
            return false;
        }

        return dao.addItem(item);
    }

    // VIEW ALL ITEMS
    public List<InventoryItem> getAllItems() {
        return dao.getAllItems();
    }

    // UPDATE ITEM
    public boolean updateItem(InventoryItem item) {

        if (item == null || item.getItemId() <= 0) {
            return false;
        }

        if (item.getItemName() == null ||
                item.getItemName().trim().isEmpty()) {
            return false;
        }

        if (item.getQuantity() < 0 ||
                item.getPrice() < 0 ||
                item.getMinimumStock() < 0) {
            return false;
        }

        return dao.updateItem(item);
    }

    // DELETE ITEM
    public boolean deleteItem(int itemId) {

        if (itemId <= 0) {
            return false;
        }

        return dao.deleteItem(itemId);
    }

    // SEARCH ITEM
    public List<InventoryItem> searchItem(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllItems();
        }

        return dao.searchItem(keyword.trim());
    }

    // CHECK LOW STOCK
    public List<InventoryItem> getLowStockItems() {

        List<InventoryItem> lowStock = dao.getAllItems()
                .stream()
                .filter(InventoryItem::isLowStock)
                .toList();

        return lowStock;
    }
}