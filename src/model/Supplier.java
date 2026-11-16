package model;

public class Supplier {

    private int supplierId;
    private String supplierName;
    private String contact;
    private String email;
    private String address;

    public Supplier(int supplierId, String supplierName,
                    String contact, String email, String address) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.contact = contact;
        this.email = email;
        this.address = address;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}