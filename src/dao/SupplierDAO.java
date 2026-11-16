package dao;

import database.DatabaseConnection;
import model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    // 1. Add Supplier
    public boolean addSupplier(Supplier supplier) {

        String sql = "INSERT INTO suppliers "
                + "(supplier_name, contact, email, address) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, supplier.getSupplierName());
            ps.setString(2, supplier.getContact());
            ps.setString(3, supplier.getEmail());
            ps.setString(4, supplier.getAddress());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // 2. Get All Suppliers
    public List<Supplier> getAllSuppliers() {

        List<Supplier> list = new ArrayList<>();

        String sql = "SELECT * FROM suppliers";

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Supplier supplier = new Supplier(
                        rs.getInt("supplier_id"),
                        rs.getString("supplier_name"),
                        rs.getString("contact"),
                        rs.getString("email"),
                        rs.getString("address")
                );

                list.add(supplier);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 3. Update Supplier
    public boolean updateSupplier(Supplier supplier) {

        String sql = "UPDATE suppliers SET supplier_name=?, "
                + "contact=?, email=?, address=? WHERE supplier_id=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, supplier.getSupplierName());
            ps.setString(2, supplier.getContact());
            ps.setString(3, supplier.getEmail());
            ps.setString(4, supplier.getAddress());
            ps.setInt(5, supplier.getSupplierId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // 4. Delete Supplier
    public boolean deleteSupplier(int supplierId) {

        String sql = "DELETE FROM suppliers WHERE supplier_id=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, supplierId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}