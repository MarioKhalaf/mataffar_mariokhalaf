package mataffar_mariokhalaf;

class Product {
    private String name;
    private float price;
    private int quantity;

    public Product() {
        
    }

    public Product(String name, float price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotalCost(int quantity) {
        return price * quantity;
    }
}