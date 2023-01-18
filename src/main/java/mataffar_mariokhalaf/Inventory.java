package mataffar_mariokhalaf;

import java.util.List;

class Inventory {
    private List<Product> products;

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public int checkInventoryCount() {
        return products.size();
    }
}