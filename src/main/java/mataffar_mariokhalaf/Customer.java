package mataffar_mariokhalaf;

import java.util.ArrayList;
import java.util.List;

class Customer {
    private String name;
    private List<Product> basket;

    public Customer () {

    }
    public Customer(String name) {
        this.name = name;
        basket = new ArrayList<Product>();
    }

    public void addToBasket(Product product) {
        basket.add(product);
    }

    public void removeProduct(Product product) {
        basket.remove(product);
    }

    public float getTotalBill() {
        float total = 0;
        for (Product product : basket) {
            total += product.getTotalCost(1);
        }
        return total;
    }
    
    public void decreaseProductQuantity(Product product){
        product.setQuantity(product.getQuantity()-1);
    }
}
