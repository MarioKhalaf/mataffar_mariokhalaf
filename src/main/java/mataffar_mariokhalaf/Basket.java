package mataffar_mariokhalaf;

import java.util.Map;
import java.util.HashMap;

class Basket {
    private Map<Product, Integer> basket; // basket object with map containing product name as key & quantity as value 

    public Basket() {
        basket = new HashMap<>();
    }
    // method to add items to the basket 
    public void addToBasket(Product product, int amount) {
        if (basket.containsKey(product)) {
            int quantity = basket.get(product);
            basket.put(product, quantity + amount);
        }else {
            basket.put(product, amount);
        }
    }
    // method removing items from the basket
    public void removeFromBasket(Product product, int amount) {
        if (amount > 0) {
            int quantity = basket.get(product);
            basket.put(product, quantity - amount);
        } else {
            basket.remove(product, basket.get(product));
        }
    }
    // a getter for the basket object
    public Map<Product, Integer> getBasket() {
        return basket;
    }
    // sums up all products in the basket to get total cost
    public double getTotalCost() { // entry method to iterate through map for keys & values
        double totalCost = 0;
        for (Map.Entry<Product, Integer> entry : basket.entrySet()) {
            totalCost += entry.getKey().getPrice() * entry.getValue(); 
        }
        return totalCost;
    }

}

