
package it2a.paranjc.pets;


import java.util.InputMismatchException;
import java.util.Scanner;
 


public class Main {

    static config config = new config(); // Instance of Config class for database operations
    static Scanner scan = new Scanner(System.in); // Scanner for user input

    public static void main(String[] args) {
        mainMenu();
    }

    // Method to clear the screen
    public static void clearScreen() {
        for (int x = 0; x < 50; x++) {
            System.out.printf("\n");
        }
    }

    // Main menu
    public static void mainMenu() {
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Pets");
            System.out.println("2. Customers");
            System.out.println("3. Exit");
            System.out.print("Select option: ");

            int choice = getIntInput(); // Helper method to get integer input
            switch (choice) {
                case 1:
                    petOptions();
                    break;
                case 2:
                    customerOptions();
                    break;
                case 3:
                    System.out.println("Exiting system...");
                    return;
                default:
                    System.out.println("Error: Option does not exist. Try again.");
            }
        }
    }

    // Pet options menu
    public static void petOptions() {
        while (true) {
            System.out.println("Pet Options:");
            System.out.println("1. Add Pet");
            System.out.println("2. View Pets");
            System.out.println("3. Edit Pet");
            System.out.println("4. Delete Pet");
            System.out.println("5. Exit to Menu");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    addPet();
                    break;
                case 2:
                    viewPets();
                    break;
                case 3:
                    editPet();
                    break;
                case 4:
                    deletePet();
                    break;
                case 5:
                    clearScreen();
                    return;
                default:
                    System.out.println("Error: Option does not exist. Try again.");
            }
        }
    }

    // Add a pet
    public static void addPet() {
        try {
            System.out.print("Enter pet name: ");
            String petName = scan.nextLine();

            System.out.print("Enter pet type: ");
            String petType = scan.nextLine();

            String sql = "INSERT INTO pets (pet_name, pet_type) VALUES (?, ?)";
            config.addRecord(sql, petName, petType);

            clearScreen();
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Try again.");
            scan.nextLine(); // Clear scanner buffer
        }
    }

    // View pets
    public static void viewPets() {
        String sql = "SELECT * FROM pets";
        String[] headers = {"ID", "Pet Name", "Pet Type"};
        String[] fields = {"id", "pet_name", "pet_type"};
        config.viewRecords(sql, headers, fields);

        System.out.println("Press any key to return to menu...");
        scan.nextLine();
        clearScreen();
    }

    // Edit a pet
    public static void editPet() {
        viewPets(); // Show all pets before editing

        try {
            System.out.print("Enter the ID of the pet to update: ");
            int id = getIntInput();

            System.out.print("Enter new pet name: ");
            String petName = scan.nextLine();

            System.out.print("Enter new pet type: ");
            String petType = scan.nextLine();

            String sql = "UPDATE pets SET pet_name = ?, pet_type = ? WHERE id = ?";
            config.updateRecord(sql, petName, petType, id);

            clearScreen();
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Try again.");
            scan.nextLine(); // Clear scanner buffer
        }
    }

    // Delete a pet
    public static void deletePet() {
        viewPets(); // Show all pets before deleting

        try {
            System.out.print("Enter the ID of the pet to delete: ");
            int id = getIntInput();

            String sql = "DELETE FROM pets WHERE id = ?";
            config.deleteRecord(sql, id);

            System.out.println("Pet deleted successfully.");
            System.out.println("Press any key to return to menu...");
            scan.nextLine();
            clearScreen();
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Try again.");
            scan.nextLine(); // Clear scanner buffer
        }
    }

    // Customer options menu
    public static void customerOptions() {
        while (true) {
            System.out.println("Customer Options:");
            System.out.println("1. Add Customer");
            System.out.println("2. View Customers");
            System.out.println("3. Edit Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Exit to Menu");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    viewCustomers();
                    break;
                case 3:
                    editCustomer();
                    break;
                case 4:
                    deleteCustomer();
                    break;
                case 5:
                    clearScreen();
                    return;
                default:
                    System.out.println("Error: Option does not exist. Try again.");
            }
        }
    }

    // Add a customer
    public static void addCustomer() {
        try {
            viewPets(); // Show all pets
            System.out.print("Enter the pet ID: ");
            int petID = getIntInput();

            if (!config.checkPetAvailability(petID)) {
                System.out.println("Pet not available. Returning to menu...");
                return;
            }

            System.out.print("Enter customer's name: ");
            String customerName = scan.nextLine();

            System.out.print("Enter customer's address: ");
            String address = scan.nextLine();

            System.out.print("Enter customer's contact info: ");
            String contactInfo = scan.nextLine();

            String sql = "INSERT INTO customers (pet_id, name, address, contact_info) VALUES (?, ?, ?, ?)";
            config.addRecord(sql, petID, customerName, address, contactInfo);

            clearScreen();
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Try again.");
            scan.nextLine(); // Clear scanner buffer
        }
    }

    // View customers
    public static void viewCustomers() {
        String sql = "SELECT * FROM customers";
        String[] headers = {"ID", "Name", "Pet ID", "Address", "Contact Info"};
        String[] fields = {"id", "name", "pet_id", "address", "contact_info"};
        config.viewRecords(sql, headers, fields);

        System.out.println("Press any key to return to menu...");
        scan.nextLine();
        clearScreen();
    }

    // Edit a customer
    public static void editCustomer() {
        viewCustomers(); // Show all customers before editing

        try {
            System.out.print("Enter the ID of the customer to update: ");
            int id = getIntInput();

            viewPets(); // Show all pets
            System.out.print("Enter new pet ID: ");
            int petID = getIntInput();

            if (!config.checkPetAvailability(petID)) {
                System.out.println("Pet not available. Returning to menu...");
                return;
            }

            System.out.print("Enter new customer name: ");
            String customerName = scan.nextLine();

            System.out.print("Enter new customer address: ");
            String address = scan.nextLine();

            System.out.print("Enter new customer contact info: ");
            String contactInfo = scan.nextLine();

            String sql = "UPDATE customers SET pet_id = ?, name = ?, address = ?, contact_info = ? WHERE id = ?";
            config.updateRecord(sql, petID, customerName, address, contactInfo, id);

            clearScreen();
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Try again.");
            scan.nextLine(); // Clear scanner buffer
        }
    }

    // Delete a customer
    public static void deleteCustomer() {
        viewCustomers(); // Show all customers before deleting

        try {
            System.out.print("Enter the ID of the customer to delete: ");
            int id = getIntInput();

            String sql = "DELETE FROM customers WHERE id = ?";
            config.deleteRecord(sql, id);

            System.out.println("Customer deleted successfully.");
            System.out.println("Press any key to return to menu...");
            scan.nextLine();
            clearScreen();
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Try again.");
            scan.nextLine(); // Clear scanner buffer
        }
    }

    // Helper method to get integer input safely
    public static int getIntInput() {
        int input = -1;
        while (input == -1) {
            try {
                input = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
            }
        }
        return input;
    }

    // Helper method to get double input safely
    public static double getDoubleInput() {
        double input = -1;
        while (input == -1) {
            try {
                input = Double.parseDouble(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
            }
        }
        return input;
    }
}