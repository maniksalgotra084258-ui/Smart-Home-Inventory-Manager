package dao;

import database.DatabaseConnection;
import model.InventoryItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryItemDAO {

    // ADD ITEM
    public boolean addItem(InventoryItem item) {

        String sql = "INSERT INTO inventory_items " +
                "(item_name, category, supplier, quantity, price, minimum_stock) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, item.getItemName());
            ps.setString(2, item.getCategory());
            ps.setString(3, item.getSupplier());
            ps.setInt(4, item.getQuantity());
            ps.setDouble(5, item.getPrice());
            ps.setInt(6, item.getMinimumStock());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // VIEW ALL ITEMS
    public List<InventoryItem> getAllItems() {

        List<InventoryItem> list = new ArrayList<>();

        String sql = "SELECT * FROM inventory_items";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                InventoryItem item = new InventoryItem(
                        rs.getInt("item_id"),
                        rs.getString("item_name"),
                        rs.getString("category"),
                        rs.getString("supplier"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getInt("minimum_stock")
                );

                list.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // UPDATE ITEM
    public boolean updateItem(InventoryItem item) {

        String sql = "UPDATE inventory_items SET item_name=?, category=?, " +
                "supplier=?, quantity=?, price=?, minimum_stock=? WHERE item_id=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, item.getItemName());
            ps.setString(2, item.getCategory());
            ps.setString(3, item.getSupplier());
            ps.setInt(4, item.getQuantity());
            ps.setDouble(5, item.getPrice());
            ps.setInt(6, item.getMinimumStock());
            ps.setInt(7, item.getItemId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE ITEM
    public boolean deleteItem(int itemId) {

        String sql = "DELETE FROM inventory_items WHERE item_id=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, itemId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // SEARCH ITEM
    public List<InventoryItem> searchItem(String keyword) {

        List<InventoryItem> list = new ArrayList<>();

        String sql = "SELECT * FROM inventory_items " +
                "WHERE item_name LIKE ? OR category LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                InventoryItem item = new InventoryItem(
                        rs.getInt("item_id"),
                        rs.getString("item_name"),
                        rs.getString("category"),
                        rs.getString("supplier"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getInt("minimum_stock")
                );

                list.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}