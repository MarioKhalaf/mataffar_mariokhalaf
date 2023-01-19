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

    @Override
    public String toString() { // toString method so browseBasket() prints actual names and not object name
        return name + " - Quantity: " + quantity + " - Price: " + price;
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