package model;

public class InventoryItem {

    private int itemId;
    private String itemName;
    private String category;
    private String supplier;
    private int quantity;
    private double price;
    private int minimumStock;

    public InventoryItem(int itemId, String itemName, String category,
                         String supplier, int quantity,
                         double price, int minimumStock) {

        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
        this.supplier = supplier;
        this.quantity = quantity;
        this.price = price;
        this.minimumStock = minimumStock;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getCategory() {
        return category;
    }

    public String getSupplier() {
        return supplier;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public int getMinimumStock() {
        return minimumStock;
    }

    public boolean isLowStock() {
        return quantity <= minimumStock;
    }
}