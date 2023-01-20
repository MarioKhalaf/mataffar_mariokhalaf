package mataffar_mariokhalaf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

class FoodStore {
    private File productsJson;
    private List<Product> products;
    private Map<Product, Integer> basket;

    public FoodStore() {
        products = new ArrayList<>();
        basket = new HashMap<>();
        productsJson = new File("target/products.json");
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

    public void addProductToInventory(Product product) {
        products.add(product);
        saveProductsToJson();
    }

    public void removeProductFromInventory(String productName) {
        Product productToRemove = null;
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                productToRemove = product;
                break;
            }
        }
        if (productToRemove != null) {
            products.remove(productToRemove);
            saveProductsToJson();
        } else {
            System.out.println("Product not found in inventory.");
        }
    }

    public int checkInventoryCount() {
        return products.size();
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

    public void addToBasket(Product product, int amount) {
        if (basket.containsKey(product)) {
            int quantity = basket.get(product);
            basket.put(product, quantity + amount);
        }else {
            basket.put(product, amount);
        }
    }
    
    public Map<Product, Integer> getBasket() {
        return basket;
    }

    public double getTotalCost() { // entry method to iterate through map for keys & values
        double totalCost = 0;
        for (Map.Entry<Product, Integer> entry : basket.entrySet()) {
            totalCost += entry.getKey().getPrice() * entry.getValue(); 
        }
        return totalCost;
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
}

