package gui;

import dao.InventoryItemDAO;
import model.InventoryItem;

public class TestInventoryDAO {

    public static void main(String[] args) {

        InventoryItemDAO dao = new InventoryItemDAO();

        // Add a test item first
        InventoryItem item = new InventoryItem(
                0, "Smart Fan", "Electronics",
                "Orient", 5, 2500.0, 2
        );

        dao.addItem(item);

        // Search item
        System.out.println("Search Results:");

        for (InventoryItem i : dao.searchItem("Smart")) {
            System.out.println(
                    i.getItemId() + " | " +
                            i.getItemName() + " | " +
                            i.getCategory()
            );
        }
    }
}