import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Stock {
    private ArrayList<Product> productList;
    private final String filename = "Stock.txt";
    
    public Stock() {
        this.productList = new ArrayList<>();
        loadStock();
    }
    
    public ArrayList<Product> getProductList(){
        return productList;
    }
    
    public boolean addNewProduct(String[] details) {
        if (details.length != 8) {
            return false;
        }
        try {
            int id = Integer.parseInt(details[0].trim());
            
            // BRIEF REQUIREMENT: Prevent adding products with the same ID
            if (findProductById(id) != null) {
                System.out.println("Error: A product with ID " + id + " already exists.");
                return false; 
            }
            
            String category = details[1].trim();
            String type = details[2].trim();
            String name = details[3].trim();
            double price = Double.parseDouble(details[4].trim());
            int stock = Integer.parseInt(details[5].trim());
            double cost = Double.parseDouble(details[6].trim());
            String additional = details[7].trim();
            
            if (!category.equalsIgnoreCase("board game") && !category.equalsIgnoreCase("accessory")) {
                return false; 
            }
            
            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
            writer.printf("\n%d; %s; %s; %s; %.2f; %d; %.2f; %s", 
                id, category, type, name, price, stock, cost, additional);
            writer.close();
            
            if (category.equalsIgnoreCase("board game")) {
                int maxPlayers = Integer.parseInt(additional);
                productList.add(new BoardGame(id, ProductCategory.BOARDGAME, type, name, cost, stock, price, maxPlayers));
            } else if (category.equalsIgnoreCase("accessory")) {
                productList.add(new Accessory(id, ProductCategory.ACCESSORY, type, name, cost, stock, price, additional));
            }
            
            return true;
        } catch(Exception e) {
            return false;
        }
    }
    
    public boolean updateStockValue(int id, int stockToAdd) {
        boolean itemFound = false;

        for (Product p : productList) {
            if (p.getProductId() == id) {
                int updatedStock = p.getQuantityInStock() + stockToAdd;
                p.setQuantityInStock(updatedStock);
                itemFound = true;
                break;
            }
        }

        if (!itemFound) return false; 

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename, false));
            
            for (Product p : productList) {
                String category = "";
                String type = "";
                String additional = "";

                if (p instanceof BoardGame) {
                    category = "board game";
                    type = ((BoardGame) p).getType();
                    additional = String.valueOf(((BoardGame) p).getMaxPlayers());
                } else if (p instanceof Accessory) {
                    category = "accessory";
                    type = ((Accessory) p).getType();
                    additional = ((Accessory) p).getCompatibility();
                }

                writer.printf("%d; %s; %s; %s; %.2f; %d; %.2f; %s\n",
                    p.getProductId(), category, type, p.getProductName(), 
                    p.getPrice(), p.getQuantityInStock(), p.getPurchaseCost(), additional);
            }

            writer.close();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Product> loadStock() {
        try {
            productList.clear();
            File file = new File(filename);
            if (!file.exists()) return productList; 
            
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if(line.isEmpty()) continue;
                
                String[] parts = line.split(";");
                
                int id = Integer.parseInt(parts[0].trim());
                String category = parts[1].trim();
                String type = parts[2].trim(); 
                String name = parts[3].trim();
                double price = Double.parseDouble(parts[4].trim());
                int stock = Integer.parseInt(parts[5].trim());
                double cost = Double.parseDouble(parts[6].trim());
                String additional = parts[7].trim();
                
                if(category.equalsIgnoreCase("board game")) {
                    int maxPlayers = Integer.parseInt(additional);
                    productList.add(new BoardGame(id, ProductCategory.BOARDGAME, type, name, cost, stock, price, maxPlayers));
                }
                else if (category.equalsIgnoreCase("accessory")) {
                    productList.add(new Accessory(id, ProductCategory.ACCESSORY, type, name, cost, stock, price, additional));
                }
            }
            scanner.close();
        } catch(Exception e) {
            System.out.println("Error loading stock: "+ e.getMessage());
        }
        return productList;
    }
    
    public Product findProductById(int id) {
        for (Product p : productList) {
            if (p.getProductId() == id) {
                return p; 
            }
        }
        return null; 
    }
    
    public ArrayList<Product> searchByIdMatch(String idQuery) {
        ArrayList<Product> matches = new ArrayList<>();
        for (Product p : productList) {
            if (String.valueOf(p.getProductId()).contains(idQuery)) {
                matches.add(p);
            }
        }
        return matches;
    }

    public ArrayList<Product> searchByCompatibility(String compQuery) {
        ArrayList<Product> matches = new ArrayList<>();
        String searchLower = compQuery.toLowerCase();
        
        for (Product p : productList) {
            if (p instanceof Accessory) {
                String itemComp = ((Accessory) p).getCompatibility().toLowerCase();
                if (searchLower.length() < 3 && itemComp.equals(searchLower)) {
                    matches.add(p);
                } else if (searchLower.length() >= 3 && itemComp.contains(searchLower)) {
                    matches.add(p);
                }
            }
        }
        return matches;
    }
}