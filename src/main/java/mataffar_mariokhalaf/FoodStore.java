package mataffar_mariokhalaf;

import java.util.List;

public class FoodStore {
    private Inventory inventory;

    public FoodStore() {
        inventory = new Inventory();
    }

    public void addProduct(Product product) {
        inventory.addProduct(product);
    }

    public void removeProduct(Product product) {
        inventory.removeProduct(product);
    }

    public int getInventoryCount() {
        return inventory.getProductCount();
    }

    public float checkOut(Customer customer) {
        return customer.getTotalBill();
    }    

    public List<Product> getAllProducts() {
        return inventory.getAllProducts();
    }
    
    public void decreaseProductQuantity(Product product){
        inventory.decreaseProductQuantity(product);
    }
}
