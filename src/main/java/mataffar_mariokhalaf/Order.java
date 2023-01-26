package mataffar_mariokhalaf;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/** 
 * A class representing all the orders that are checked out and saves them to a json file
 * four class variables of a map that the product name and their quantities,
 * the total price of all the products in the map
 * the date & time
 * the file where they will be saved.
*/
class Order {
    private Map<String, Integer> products;
    private double totalPrice;
    private LocalDateTime date;
    private File file;
    /**
    * This constructor creates a new Order object with a given basket and total cost.
    * using for loop to extract the Products and their respective String names from the basket
    * inserts the baskets key(name) and value(quantity) to the new hasmap "products" to be inserted into the json
    @param basket a map containing products and their respective quantities
    @param totalCost the total cost of the order
    */
    public Order(Map<Product, Integer> basket, double totalCost) {
        this.products = new HashMap<>();
        for(Map.Entry<Product, Integer> entry : basket.entrySet()) {
            products.put(entry.getKey().getName(), entry.getValue());
        }
        this.totalPrice = totalCost;
        this.date = LocalDateTime.now();
        file = new File("orderhistory.json");
    }
    /**
     * a constructor with empty parameters to be used to display order history
     */
    public Order() {
        file = new File("orderhistory.json");
    }
    /**
     * method to write each order into the json file
     * reads the file first and puts class variables into a hashmap 
     * Appends the new hashmap into the existing orders and writes back updated file.
     * 
     */
    public void writeOrderToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Map<String, Object>> existingOrders = readOrderHistoryFromJson(); 
            Map<String, Object> newOrder = new HashMap<>();
            newOrder.put("Products", products);
            newOrder.put("Total price", totalPrice);
            newOrder.put("Date & time", getDate());
            existingOrders.add(newOrder);
            // Pretty printer to insert into nicer json format.
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, existingOrders);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * reads the json file into a variable
     * @return the variable or null if empty.
     */
    public List<Map<String, Object>> readOrderHistoryFromJson(){  
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Map<String, Object>> existingOrders;
            existingOrders = objectMapper.readValue(file, new TypeReference<List<Map<String, Object>>>(){});
            return existingOrders;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * convert date object to string format and replace a few unneccessary infos in the date
     * @return date & tiome as a string.
     */
    public String getDate(){
        return date.toString().replace("T", " - ").substring(0, 21);

    }
}




