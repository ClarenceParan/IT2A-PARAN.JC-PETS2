
package it2a.paranjc.pets;


import java.sql.*;

public class config {

    // Method to establish a connection to the SQLite database
    public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC"); // Load the SQLite JDBC driver
            con = DriverManager.getConnection("jdbc:sqlite:pet_customer.db"); // Change the database to match your project
            System.out.println("Connection Successful");
        } catch (Exception e) {
            System.out.println("Connection Failed: " + e);
        }
        return con;
    }

    // Method to display records from the database
    public void viewRecords(String query, String[] columnHeaders, String[] columnFields) {
        try (Connection conn = this.connectDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println(String.join(" | ", columnHeaders));
            while (rs.next()) {
                for (String field : columnFields) {
                    System.out.print(rs.getString(field) + " | ");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("Error viewing records: " + e.getMessage());
        }
    }

    // Method to add a record to the database
    public void addRecord(String sql, Object... values) {
        try (Connection conn = this.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }
            pstmt.executeUpdate();
            System.out.println("Record added successfully.");

        } catch (SQLException e) {
            System.out.println("Error adding record: " + e.getMessage());
        }
    }

    // Method to update a record in the database
    public void updateRecord(String sql, Object... values) {
        try (Connection conn = this.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }
            pstmt.executeUpdate();
            System.out.println("Record updated successfully.");

        } catch (SQLException e) {
            System.out.println("Error updating record: " + e.getMessage());
        }
    }

    // Method to delete a record from the database
    public void deleteRecord(String sql, int id) {
        try (Connection conn = this.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Record deleted successfully.");

        } catch (SQLException e) {
            System.out.println("Error deleting record: " + e.getMessage());
        }
    }

    // Check if a pet is available (exists in the pets table)
    public boolean checkPetAvailability(int petID) {
        String sql = "SELECT id FROM pets WHERE id = ?";
        try (Connection conn = this.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, petID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true; // Pet exists
            }
        } catch (SQLException e) {
            System.out.println("Error checking pet availability: " + e.getMessage());
        }
        return false;
    }

    // Additional helper methods could be added here based on future requirements
}
