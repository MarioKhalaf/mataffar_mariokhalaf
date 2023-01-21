package mataffar_mariokhalaf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

class Inventory {
    private File productsJson;
    private List<Product> products;

    public Inventory() {
        productsJson = new File("target/foodstock.json");
        products = new ArrayList<>();
        loadProductsFromJson();
    
    }
    public void loadProductsFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, List<Product>> map = objectMapper.readValue(productsJson, 
            new TypeReference<Map<String, List<Product>>>(){});
            products = map.get("Product");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveProductsToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, List<Product>> map = new HashMap<>();
            map.put("Product", products);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(productsJson, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String addProductToInventory(Product product) {
        for (Product p : products) {
            if (product.equals(p)) {
                return product.getName() + " is already in inventory.\n";
            }
        }
        products.add(product);
        saveProductsToJson();
        return product.getName() + " Has been added to inventory\n";
    }
    public String removeProductFromInventory(String productName) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                products.remove(product);
                saveProductsToJson();
                return productName + " Has been removed from inventory.\n";
            }
        }
        return "There is no " + productName + " in inventory.\n";
    }

    public  List<Product> browseProducts() {
        return products;
    }

    public Product getProductByName(String name) {
        for(Product product : products) {
            if(product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    public void decreaseInventoryQuantity(Product product, int quantity) {
        for (Product p : products) { 
            if (p.getName().equals(product.getName())) { // compares product name to the one in json file
                if (p.getQuantity() >= quantity) { // makes sures quantity in stock is more than asked amount
                    p.setQuantity(p.getQuantity() - quantity); 
                    return;
                } else { // handles error user inputs amount more than available in stock
                    throw new IllegalArgumentException("The product is out of stock");
                }
            }
        } // handles error if user inputs non existen product or
        throw new IllegalArgumentException("Product not found in inventory");
    }

    public String reStockInventory(String productName, int amount) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                int quantity = product.getQuantity();
                product.setQuantity(quantity + amount);
                return amount + " " + productName + " Has been refilled to the inventory. \nNew " + productName + " quantity: " + product.getQuantity() + "\n";
            }
        } 
        return productName + " is not in inventory\n";

    }
}