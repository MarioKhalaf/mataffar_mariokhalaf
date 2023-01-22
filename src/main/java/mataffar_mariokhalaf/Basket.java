package mataffar_mariokhalaf;

import java.util.Map;
import java.util.HashMap;
/**
* The Basket class represents a shopping basket that contains- 
* -items that a customer wants to purchase.
* It has methods for adding and removing items from the basket, 
* as well as getting the total cost of all items in the basket.
*/
class Basket implements QuantityChange{
    /*
    Class variable representing the shopping basket as a map of products (Keys) and their quantities (Values).
    */
    private Map<Product, Integer> basket;

    public Basket() {
        basket = new HashMap<>();
    }
    @Override
    public String add(Product product, int amount) {
        if (basket.containsKey(product)) {
            int quantity = basket.get(product);
            basket.put(product, quantity + amount);
            return "New" + product.getName() + " quantity: " + product.getName();
        }else {
            basket.put(product, amount);
            return product.getQuantity() + " " + product.getName() + " Has been added to your baset.";
        }
    }
    @Override
    public String remove(Product product, int amount) {
        if (amount > 0) {
            int quantity = basket.get(product);
            basket.put(product, quantity - amount);
            return "New" + product.getName() + " quantity: " + product.getQuantity();
        } else {
            basket.remove(product, basket.get(product));
            return product.getQuantity() + " " + product.getName() + " Has been removed to your baset.";
        }
    }

     /**
     * Get the map representing the basket.
     * @return the map of products and their quantities thats inside the basket
     */
    public Map<Product, Integer> getBasket() {
        return basket;
    }
    /**
     * To get the total cost of all items inside the basket
     * @return the total cost
     */
    public double getTotalCost() { //Using entry method for map to get the keys & values
        double totalCost = 0; 
        for (Map.Entry<Product, Integer> entry : basket.entrySet()) {
            totalCost += entry.getKey().getPrice() * entry.getValue(); 
        }
        return totalCost;
    }

}

