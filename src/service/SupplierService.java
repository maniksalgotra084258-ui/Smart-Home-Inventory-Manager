package service;

import dao.SupplierDAO;
import model.Supplier;

import java.util.List;

public class SupplierService {

    private SupplierDAO supplierDAO = new SupplierDAO();

    // Add Supplier
    public boolean addSupplier(Supplier supplier) {
        return supplierDAO.addSupplier(supplier);
    }

    // Get All Suppliers
    public List<Supplier> getAllSuppliers() {
        return supplierDAO.getAllSuppliers();
    }

    // Update Supplier
    public boolean updateSupplier(Supplier supplier) {
        return supplierDAO.updateSupplier(supplier);
    }

    // Delete Supplier
    public boolean deleteSupplier(int supplierId) {
        return supplierDAO.deleteSupplier(supplierId);
    }
}