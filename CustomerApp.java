package CustomerPack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


public class CustomerApp {
    public static void main(String[] args) {
        DatabaseConnection.ensureTableExists(); // Ensures table is created if it doesn’t exist
        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\nChoose an operation:");
            System.out.println("1. Create a new customer record");
            System.out.println("2. View customer records");
            System.out.println("3. Update a customer record");
            System.out.println("4. Delete a customer record");
            System.out.println("5. Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1": createCustomer(scanner); break;
                case "2": viewCustomers(); break;
                case "3": updateCustomer(scanner); break;
                case "4": deleteCustomer(scanner); break;
                case "5": System.out.println("Exiting..."); keepRunning = false; break;
                default: System.out.println("Invalid choice. Try again."); break;
            }
        }
        scanner.close();
    }

    private static void createCustomer(Scanner scanner) {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone (10+ digits): ");
        String phone = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        if (ValidationUtil.isValidName(name) && ValidationUtil.isValidEmail(email) &&
            ValidationUtil.isValidPhone(phone) && ValidationUtil.isValidAddress(address)) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String query = "INSERT INTO customers (name, email, phone, address) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, phone);
                stmt.setString(4, address);
                stmt.executeUpdate();
                System.out.println("Customer record created successfully.");
            } catch (Exception e) {
                System.err.println("Error inserting record: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid input data.");
        }
    }

    private static void viewCustomers() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM customers")) {

            System.out.println("ID | Name | Email | Phone | Address | Created At");
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %s | %s | %s\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getTimestamp("created_at"));
            }
        } catch (Exception e) {
            System.err.println("Error reading records: " + e.getMessage());
        }
    }

    private static void updateCustomer(Scanner scanner) {
        System.out.print("Enter the ID of the customer to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter new Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter new Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter new Address: ");
        String address = scanner.nextLine();

        if (ValidationUtil.isValidName(name) && ValidationUtil.isValidEmail(email) &&
            ValidationUtil.isValidPhone(phone) && ValidationUtil.isValidAddress(address)) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String query = "UPDATE customers SET name = ?, email = ?, phone = ?, address = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, phone);
                stmt.setString(4, address);
                stmt.setInt(5, id);
                int rowsAffected = stmt.executeUpdate();
                System.out.println(rowsAffected > 0 ? "Record updated successfully." : "No record found with that ID.");
            } catch (Exception e) {
                System.err.println("Error updating record: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid input data.");
        }
    }

    private static void deleteCustomer(Scanner scanner) {
        System.out.print("Enter the ID of the customer to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM customers WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected > 0 ? "Record deleted successfully." : "No record found with that ID.");
        } catch (Exception e) {
            System.err.println("Error deleting record: " + e.getMessage());
        }
    }
}
