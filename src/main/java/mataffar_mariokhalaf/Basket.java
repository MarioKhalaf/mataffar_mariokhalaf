package mataffar_mariokhalaf;

import java.util.Map;
import java.util.HashMap;

class Basket {
    private Map<Product, Integer> basket; // basket object containing 

    public Basket() {
        basket = new HashMap<>();
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

}

