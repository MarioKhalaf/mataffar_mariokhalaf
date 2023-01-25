package mataffar_mariokhalaf;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

class Order {
    private Map<String, Integer> items;
    private double totalPrice;
    private LocalDateTime date;
    private File file;

    public Order(Map<Product, Integer> receipt, double totalCost) {
        this.items = new HashMap<>();
        for(Map.Entry<Product, Integer> entry : receipt.entrySet()) {
            items.put(entry.getKey().getName(), entry.getValue());
        }
        this.totalPrice = totalCost;
        this.date = LocalDateTime.now();
        file = new File("orderhistory.json");
    }

    public Order() {
        file = new File("orderhistory.json");
    }

    public void writeOrderToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Map<String, Object>> existingOrders = readOrderHistoryFromJson(); 
            Map<String, Object> newOrder = new HashMap<>();
            newOrder.put("Products", items);
            newOrder.put("Total price", totalPrice);
            newOrder.put("Date & time", getDate());
            existingOrders.add(newOrder);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, existingOrders);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public String getDate(){
        return date.toString().replace("T", " - ").substring(0, 21);

    }
}




