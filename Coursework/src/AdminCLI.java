import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class AdminCLI {
    public final static String NOT_IMPLEMENTED = "Not implemented";
    public final static String INVALID = "Invalid input";

    public static void run(Scanner consoleInput, Stock stock) {
        System.out.println("ADMIN VIEW");

        while (true) {
            printAdminMenu();
            
            int selection;
            try {
                selection = Integer.parseInt(consoleInput.nextLine().trim());
            } catch (NumberFormatException e) {
                selection = -1; // Forces default case
            }
            
            switch (selection) {
                case 1: 
                    adminDisplayItems(stock.getProductList(), consoleInput); 
                    break;
                
                case 2: 
                    System.out.println("Add New Product to Stock:");
                    takeProductDetails(consoleInput, stock); 
                    break;
                    
                case 3: 
                    updateStockAmount(consoleInput, stock);
                    break;
                    
                case 0:
                    return;
                    
                default:
                    System.out.println(INVALID);
                    System.out.println();
            }
        }
    }
    
    public static void updateStockAmount(Scanner scanner, Stock stock) {
        try {
            System.out.println("Enter the Product ID:");
            int productId = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.println("Enter the amount to add:");
            int amountAdded = Integer.parseInt(scanner.nextLine().trim());
            
            while (true) {
                System.out.println("You are about to add " + amountAdded + " items to the stock.");
                System.out.println("Enter 1 to confirm or 0 to cancel");
                int accept = Integer.parseInt(scanner.nextLine().trim());
                
                if (accept == 1) {
                    boolean success = stock.updateStockValue(productId, amountAdded); 
                    
                    if (success) {
                        System.out.println("Stock updated successfully.");
                    } else {
                        System.out.println("Failed to update stock. Please ensure the Product ID is correct.");
                    }
                    break;
                } else if (accept == 0) {
                    System.out.println("Operation cancelled.");
                    break;
                } else {
                    System.out.println(INVALID);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Must be a number.");
        }
    }
    
    private static void printAdminMenu() {
        System.out.println("\nPLEASE SELECT ACTION BY INPUTTING THE CORRESPONDING NUMBER (or 0 for logout)");
        System.out.println("1) View all products");
        System.out.println("2) Add new product");
        System.out.println("3) Update Stock Amounts");
        System.out.println("0) Log out");
    }
    
    public static void takeProductDetails(Scanner scanner, Stock stock) {
        System.out.println("What type of product is this?");
        System.out.println("Enter '0' to cancel this operation");
        
        ProductCategory[] categories = ProductCategory.values();
        for (int i = 0; i < categories.length; i++) {
            System.out.println("Enter '" + (i + 1) + "' for " + categories[i].getDisplayName());
        }
        
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(INVALID);
            return;
        }

        if (choice == 0) {
            System.out.println("Operation cancelled.");
            return;
        }
        
        if (choice < 1 || choice > categories.length) {
            System.out.println(INVALID);
            return;
        }

        ProductCategory selectedCat = categories[choice - 1];
        String[] productDetails = new String[8];

        productDetails[1] = selectedCat.getDisplayName(); 

        System.out.println("Enter the Product ID: ");
        productDetails[0] = scanner.nextLine().trim();
        
        System.out.println("Enter type:");
        productDetails[2] = scanner.nextLine().trim();
        
        System.out.println("Enter name:");
        productDetails[3] = scanner.nextLine().trim();      
        
        System.out.println("Enter selling price:");
        productDetails[4] = scanner.nextLine().trim();

        System.out.println("Enter initial stock amount:");
        productDetails[5] = scanner.nextLine().trim();
        
        System.out.println("Enter wholesale unit price:");
        productDetails[6] = scanner.nextLine().trim();
        
        System.out.println(selectedCat.getExtraPrompt());
        productDetails[7] = scanner.nextLine().trim();
        
        System.out.println("\nThe product you are adding has the following details:");
        System.out.printf("ID: %s\nCategory: %s\nType: %s\nName: %s\nPrice: £%s\nStock: %s\nWholesale Price: £%s\n%s: %s\n",
            productDetails[0], productDetails[1], productDetails[2], productDetails[3], productDetails[4],
            productDetails[5], productDetails[6], selectedCat.getExtraLabel(), productDetails[7]);
        
        while (true) {
            System.out.println("Enter 1 to add product and 0 to cancel");
            try {
                int accept = Integer.parseInt(scanner.nextLine().trim());
                if (accept == 1) {
                    boolean success = stock.addNewProduct(productDetails);
                    if (success) {
                        System.out.println("Item successfully added to stock.");
                    } else {
                        System.out.println("Failed to add item. Ensure numbers were entered correctly for ID, prices, and stock.");
                    }
                    break;
                } else if (accept == 0) {
                    System.out.println("Operation cancelled.");
                    break;
                } else {
                    System.out.println(INVALID);
                }
            } catch (NumberFormatException e) {
                System.out.println(INVALID);
            }
        }
    }
    
    public static void adminDisplayItems(List<Product> productList, Scanner scanner) {
        List<Product> sortedList = new ArrayList<>(productList);
        sortedList.sort(Comparator.comparingDouble(Product::getPrice).reversed());

        System.out.println(String.format("%-6s | %-30s | %-8s | %-15s | %-7s | %s",
                "ID", "Name", "Price", "Purchase Cost", "Stock", "Extra Info"));
        System.out.println("--------------------------------------------------------------------------------------------------------");

        for (Product p : sortedList) {
            String commonInfo = String.format("%-6d | %-30s | £%-7.2f | £%-14.2f | %-7d", 
                                p.getProductID(), p.getProductName(), p.getPrice(), p.getPurchaseCost(), p.getStock());

            String extraInfo = p.getExtraDetails();

            if (!extraInfo.isEmpty()) {
                System.out.println(commonInfo + " | " + extraInfo);
            } else {
                System.out.println(commonInfo);
            }
        }
        System.out.println("\nPress Enter to return to the main menu...");
        scanner.nextLine();
    }
}