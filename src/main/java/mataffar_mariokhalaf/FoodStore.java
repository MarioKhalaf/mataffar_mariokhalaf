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
    private List<Product> basket;

    public FoodStore() {
        products = new ArrayList<>();
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

    public void removeProductFromInventory(Product product) {
        products.remove(product);
        saveProductsToJson();
    }

    public int checkInventoryCount() {
        return products.size();
    }

    public void browseProducts() {
        for (Product product : products) {
            System.out.println(product);
        }
    }
    
    public Product getProductByName(String name) {
        for(Product product : products) {
            if(product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    public void addToBasket(Product product, int quantity) {
        for (int i = 0; i < quantity; i++) {
            basket.add(product);
        }
    }
    
    public List<Product> getBasket() {
        return basket;
    }

    public void decreaseQuantity(Product product, int quantity) {
        for (Product p : products) {
            if (p.getName().equals(product.getName())) {
                if (p.getQuantity() >= quantity) {
                    p.setQuantity(p.getQuantity() - quantity);
                    return;
                } else {
                    throw new IllegalArgumentException("The product is out of stock");
                }
            }
        }
        throw new IllegalArgumentException("Product not found in inventory");
    }
}