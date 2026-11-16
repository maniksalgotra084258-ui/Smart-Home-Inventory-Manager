package gui;

import service.InventoryItemService;
import model.InventoryItem;

public class TestInventoryService {

    public static void main(String[] args) {

        InventoryItemService service = new InventoryItemService();

        System.out.println("All Inventory Items:");

        for (InventoryItem item : service.getAllItems()) {

            System.out.println(
                    item.getItemId() + " | " +
                            item.getItemName() + " | " +
                            item.getQuantity()
            );
        }

        System.out.println("\nLow Stock Items:");

        for (InventoryItem item : service.getLowStockItems()) {

            System.out.println(
                    item.getItemName() + " | " +
                            item.getQuantity()
            );
        }
    }
}