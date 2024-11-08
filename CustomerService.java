package CustomerPack;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    public void createCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO customers (name, email, phone, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getEmail());
            pstmt.setString(3, customer.getPhone());
            pstmt.setString(4, customer.getAddress());
            pstmt.executeUpdate();
            System.out.println("Customer record added successfully.");
        }
    }

    public List<Customer> readCustomers() throws SQLException {
        String query = "SELECT * FROM customers";
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address")
                );
                customer.setId(rs.getInt("id"));
                customers.add(customer);
            }
        }
        return customers;
    }

    public void updateCustomer(int id, Customer customer) throws SQLException {
        String query = "UPDATE customers SET name = ?, email = ?, phone = ?, address = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getEmail());
            pstmt.setString(3, customer.getPhone());
            pstmt.setString(4, customer.getAddress());
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
            System.out.println("Customer record updated successfully.");
        }
    }

    public void deleteCustomer(int id) throws SQLException {
        String query = "DELETE FROM customers WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Customer record deleted successfully.");
        }
    }
}
