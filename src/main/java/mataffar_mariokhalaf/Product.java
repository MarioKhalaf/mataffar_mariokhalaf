package mataffar_mariokhalaf;
/**
The Product class represents an item in the store inventory. 
It contains information about the product name, price and quantity.
@author Mario Khalaf
*/
class Product {
    private String name;
    private float price;
    private int quantity;
    /**
    This constructs a new Product object with the given name, price and quantity.
    @param name the name of the product
    @param price the price of the product
    @param quantity the quantity of the product in inventory
    */
    public Product(String name, float price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
     /**
    a default constructor the jackson library can use to create- 
    -instances of the product class during deserialization
     */
    public Product() {
    }
    /**
     * A setter to handle quantity changes.
     * such as new order or customers buying.
     * @param quantity to take in the new quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    /**
     * Get name of product
     * @return name of product
     */
    public String getName() {
        return name;
    }
    /**
     * Get price of certain product
     * @return price of product
     */
    public float getPrice() {
        return price;
    }
    /**
     * get quantity of cetrain product in inventory
     * @return quantity of product
     */
    public int getQuantity() {
        return quantity;
    }
}