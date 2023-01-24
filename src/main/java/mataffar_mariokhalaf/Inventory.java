package mataffar_mariokhalaf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Inventory class is responsible for managing the products in the store.
 * It loads the products from a JSON file that then
 * allows adding and removing products from the inventory,
 * and provides methods for browsing and searching for products.
 * 
 * @author Mario Khalaf
 */
class Inventory {
    /**
     * two class variables that is used to store the JSON file containing the
     * products.
     * and the products List variable which stores all the product in the inventory
     */
    private File file;
    private List<Product> products;

    /**
     * The constructor creates a new Inventory object and loads the products from
     * the JSON file to ensure products list is always up to date with the data
     */
    public Inventory() {
        file = new File("target/foodstock.json");
        products = new ArrayList<>();
        loadProductsFromJson();
    }
    /**
     * This method loads the products from the JSON file into the products list,
     * using object mapper from the jackson library it can serialize and deserialize
     * into JSON and java objects.
     */
    public void loadProductsFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, List<Product>> map = objectMapper.readValue(file,
            new TypeReference<Map<String, List<Product>>>() {});
            products = map.get("Products");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method saves the products in the products list to the JSON file.
     */
    public void saveProductsToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, List<Product>> map = new HashMap<>();
            map.put("Products", products);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String add(Product product) {
        for (Product p : products) {
            if (product.getName().equals(p.getName())) {
                return product.getName() + " is already in inventory.\n";
            } else if (product.getPrice() <= 0) {
                return "Invalid price! This is not a charity.\n";
            }else if (product.getQuantity() < 0) {
                return "Invalid quantity! Cannot enter negative quantity.\n";
            }
        }
        products.add(product);
        saveProductsToJson();
        return product.getName() + " has been added to the inventory.\n";
    }

    public String remove(Product product) { 
        if (product != null) { 
            for (Product p : products) {
                if (product.getName().equals(p.getName())) {
                    products.remove(p);
                    saveProductsToJson();
                    return product.getName() + " has been removed from inventory";
                }
            }
        }
        return "Product does not exist in inventory.\n";
    }
    /**
     * get List of all products in the basket
     * @return list of products
     */
    public List<Product> browseProducts() {
        return products;
    }
    /**
     * method iterates a string product name through the products list to match with the product class object
     * @param name The String name of the product to search for
     * @return Returns the product class object that matches the given name. If no product is found, returns null.
     * Helps validiting user input before going further.
     */
    public Product getProductByName(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    public String increaseQuantity(Product product, int amount){
        if (amount < 0) {
            return "Invalid amount! No negative quantities.";
        } else {
            for (Product p : products) { // loops through all 
                if (p.getName().equals(product.getName())) { // compares each others string name
                    p.setQuantity(p.getQuantity() + amount); // Sets new quantity
                    saveProductsToJson(); // updates new quantity to json 
                    return "New " + product.getName() + " quantity: " + product.getQuantity() + "\n";
                }
            } // handles error if user inputs non existen product or
            throw new IllegalArgumentException("Product not found in inventory");
        }
    }
    
    public String decreaseQuantity(Product product, int amount) {
        if (amount < 0) {
            return "Invalid amount! No negative quantities.";
        } else {
            for (Product p : products) { 
                if (p.getName().equals(product.getName())) { // compares product name to the one in json file
                    if (p.getQuantity() >= amount) { // makes sures quantity in stock is more than asked amount
                        p.setQuantity(p.getQuantity() - amount); 
                        saveProductsToJson();
                        return "New " + product.getName() + " quantity: " + product.getQuantity() + "\n";
                    } else { // handles error user inputs amount more than available in stock
                        throw new IllegalArgumentException("The product is out of stock");
                    }
                }
            } // handles error if user inputs non existen product or
            throw new IllegalArgumentException("Product not found in inventory");
        }
    }
}