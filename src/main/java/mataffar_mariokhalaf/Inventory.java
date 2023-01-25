package mataffar_mariokhalaf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
        file = new File("foodstock.json");
        products = new ArrayList<>();
        readFromJsonFile();
    }
    /**
     * This method loads the products from the JSON file into the products list,
     * using object mapper from the jackson library it can serialize and deserialize
     * into JSON and java objects.
     */
    public void readFromJsonFile() {
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
    public void writeToJsonFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, List<Product>> map = new HashMap<>();
            map.put("Products", products);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method adds verifies the product being sent to it is valid before adding it to the product class.
     * @param product takes in the product to validate it
     * @return string statement for user to see the outcome
     */
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
        writeToJsonFile();
        return product.getName() + " has been added to the inventory.\n";
    }
    /**
     * This method verifies the product is valid before going further and removing it.
     * checks if argument being sent is of null value or if product does not exist.
     * @param product is the product it validates
     * @return string statement for user to see outcome
     */
    public String remove(Product product) { 
        if (product != null) { 
            for (Product p : products) {
                if (product.getName().equals(p.getName())) {
                    products.remove(p);
                    writeToJsonFile();
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
    public List<Product> getProducts() {
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
    /**
     * this method validates that the product and amount is valid before increasing quantity in json file.
     * @param product the product that will be updated
     * @param amount the amount to update the product with
     * @return string statement explaining what happened or throws new exception if product does not exist
     */
    public String increaseQuantity(Product product, int amount){
        if (amount < 0) {
            return "Invalid amount! No negative quantities.";
        } else {
            for (Product p : products) { // loops through all 
                if (p.getName().equals(product.getName())) { // compares each others string name
                    p.setQuantity(p.getQuantity() + amount); // Sets new quantity
                    writeToJsonFile(); // updates new quantity to json 
                    return "New " + product.getName() + " quantity: " + product.getQuantity() + "\n";
                }
            } // handles error if user inputs non existen product or
            throw new IllegalArgumentException("Product not found in inventory");
        }
    }
    /**
     * this method validates that the product and amount is valid before decreasing quantity in json file.
     * @param product the product that will be updated
     * @param amount  the amount to update the product with
     * @return string statement or throws new exception depending on outcome
     */
    public String decreaseQuantity(Product product, int amount) {
        if (amount < 0) {
            return "Invalid amount! No negative quantities.";
        } else {
            for (Product p : products) { 
                if (p.getName().equals(product.getName())) { // compares product name to the one in json file
                    if (p.getQuantity() >= amount) { // makes sures quantity in stock is more than asked amount
                        p.setQuantity(p.getQuantity() - amount); 
                        writeToJsonFile();
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