package mataffar_mariokhalaf;

import java.util.ArrayList;
import java.util.List;

class Inventory {
    private List<Product> products;

    public Inventory() {
        products = new ArrayList<Product>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public int getProductCount() {
        return products.size();
    }

    public List<Product> getAllProducts() {
        return products;
    }
    
    public void decreaseProductQuantity(Product product){
        product.setQuantity(product.getQuantity()-1);
    }
}