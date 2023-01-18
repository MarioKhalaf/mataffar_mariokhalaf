package mataffar_mariokhalaf;

import java.util.ArrayList;
import java.util.List;

class Customer {
    private String name;
    private List<Product> basket;

    public Customer(String name) {
        this.name = name;
        basket = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getBasket() {
        return basket;
    }

    public void addToBasket(Product product, int quantity) {
        for (int i = 0; i < quantity; i++) {
            basket.add(product);
        }
    }
    
}