package mataffar_mariokhalaf;

import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * This class is the main one where everything runs through it using all the other classes.
 */
public class FoodStore {
    /**
     * The main method where the program begins.
     * Contains a switch case menu for 8 different options to interact with user
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Inventory inventory = new Inventory();
        Basket basket = new Basket();
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("1. Add product to inventory");
            System.out.println("2. Remove product from inventory");
            System.out.println("3. Re-stock inventory.");
            System.out.println("4. Start shopping.");
            System.out.println("5. View basket");
            System.out.println("6. Order History.");
            System.out.println("7. Start Food Store simulator.");
            System.out.println("8. Exit");
            String choice = input.next();

            switch (choice) {
                case "1":
                    insertProductToJsonFile(inventory, input);
                    break;
                case "2":
                    deleteProductFromJsonFile(inventory, input);
                    break;
                case "3":
                    inventoryOrder(inventory, input);
                    break;
                case "4":
                    groceryShopping(inventory, basket, input);
                    break;
                case "5":
                    if (basket.getBasket().size() == 0){
                        System.out.println("Basket is empty!");
                        break;}
                    else
                        adjustBasket(basket, inventory, input);
                        break;
                case "6":
                    displayOrderHistory();
                    break;
                case "7":
                    simulateFoodStore(inventory);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
                    break;
            }
        }
    }
    /**
     * method to input product name, price & quantity to create a new instance of the Product class & save it to a varable addProduct
     * sends addProduct var as argument to the inventory.add methood
     * @param inventory instance of inventory class
     * @param input Scanner to handle user inputs
     */
    private static void insertProductToJsonFile(Inventory inventory, Scanner input) {
        System.out.println("Enter product name: ");
        String pName = input.next();
        System.out.println("Enter product price: ");
        float price = input.nextFloat();
        System.out.println("Enter product quantity: ");
        int quantity = input.nextInt();
        Product addProduct = new Product(pName, price, quantity);
        System.out.println(inventory.add(addProduct));
    }
    /**
     * method to remove products in inventory by user input of name
     * @param inventory instance of inventory class
     * @param input Scanner to handle user inputs
     */
    private static void deleteProductFromJsonFile(Inventory inventory, Scanner input) {
        System.out.println("Enter the name of the product you want to remove: ");
        String name = input.next();
        Product product = inventory.getProductByName(name);
        System.out.println(inventory.remove(product));
    }
    /**
     * initiates the grocery shopping simulator where it displays groceries.
     * You can add and remove products to your basket using the basket instance of basket class.
     * @param inventory instance of inventory class
     * @param basket instance of basket class
     * @param input Scanner to handle user inputs
     * @throws InterruptedException using Thread.sleep to pause the program for a few seconds.
     */
    private static void groceryShopping(Inventory inventory, Basket basket, Scanner input) throws InterruptedException {
        System.out.println("\nWelcome to the Food Store\nAdd products to your basket by entering its name.");
        while (true) {
            try {
                System.out.println("\n1.Return to menu");
                displayGroceries(inventory);
                String option = input.next();
                if (option.equals("1")) {
                    break;
                } 
                Product chosenProduct = inventory.getProductByName(option);
                System.out.println("\nEnter amount: ");
                int amount = input.nextInt();
                if (chosenProduct == null) {
                    System.out.println("\nProduct not found in inventory\n");
                } else if (chosenProduct.getQuantity() < amount) {
                    System.out.println("\n" + option + " are out of stock\n");
                } else {
                    System.out.println(basket.addToBasket(chosenProduct, amount));
                    inventory.decreaseQuantity(chosenProduct, amount);
                    inventory.writeToJsonFile();
        
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Enter a valid amount");
                input.next();
            }
        }
    }
    /**
     * method that displays all products inside json file by looping through and printing out.
     * @param inventory instance of class
     */
    private static void displayGroceries(Inventory inventory) {
        System.out.println("\nProduct  | Amount | Price");
        System.out.println("-------------------------");
        List<Product> products = inventory.getProducts();
        for (Product p : products) { // prints out lines aligned with eachother
            System.out.printf("%-8s | %-6d | %-8.2f\n", p.getName(), p.getQuantity(), p.getPrice());
        }
    }
    /**
     * method to display all products inside the customer basket
     * prints out products inside map using entryset to get keys(product name) and values(quantity)
     * @param basket instance of basket class.
     */
    private static void displayBasket(Basket basket) {
        System.out.println("\nProduct  | Amount | Price");
        System.out.println("-------------------------"); // using entry method to iterate through map
        Map<Product, Integer> customerBasket = basket.getBasket();
        for (Map.Entry<Product, Integer> entry : customerBasket.entrySet()) { // prints out lines aligned with eachother
            System.out.printf("%-8s | %-6d | %-8.2f\n", entry.getKey().getName(),
                    entry.getValue(), entry.getKey().getPrice() * entry.getValue());
        }
        double totalCost = basket.getTotalCost();
        System.out.println("\nTotal cost: $" + totalCost + "\n");
    }
    /**
     * method to adjust the contents of the basket.
     * you can add and remove quantity from each product in the basket to ur specific needs.
     * @param inventory instance of inventory class
     * @param basket instance of basket class
     * @param input Scanner to handle user inputs
     * @throws InterruptedException pauses program for better user interaction
     */
    private static void adjustBasket(Basket basket, Inventory inventory, Scanner input) throws InterruptedException {
        while (true) {
            displayBasket(basket);
            System.out.println("1. Checkout.\n2. Return to menu.\n3. Add.\n4. Remove.");
            String option = input.next();
            if (option.equals("1" )) {
                checkOut(basket);
                break;
            }else if (option.equals("2")) {
                break;
            }
            System.out.println("Enter name of product: ");
            String name = input.next();
            Product product = inventory.getProductByName(name);
            if (product == null) {
                System.out.println("Product not found in inventory\n");
                continue;
            }
            if (option.equals("3")) {
                System.out.println("Enter amount you'd like to add: ");
                int amount = input.nextInt();
                if (amount > product.getQuantity()) { // prevents user from entering number higher than whats in inventory
                    System.out.println("There are only " + product.getQuantity() + " " + name + " left in stock.\n");
                } else {
                    inventory.decreaseQuantity(product, amount);
                    String response = basket.addToBasket(product, amount);
                    System.out.println(response);
                }
            } else if (option.equals("4")) {
                System.out.println("Enter amount you'd like to remove: ");
                int amount = input.nextInt();
                int quantity = basket.getQuantity(product);
                if (quantity - amount < 0) {
                    amount = quantity; // if amount is more than quantity, set amount equal to quantity to not become negative number
                } 
                String response = basket.removeFromBasket(product, amount);
                inventory.increaseQuantity(product, amount);
                System.out.println(response);
            }
        }
    }
    /**
     * method to refill the foodstore stock by ordering more of each product.
     * @param inventory instance of inventory class
     * @param input Scanner to handle user inputs
     * @throws InterruptedException sleep mode for a fun interaction with user
     */
    private static void inventoryOrder(Inventory inventory, Scanner input) throws InterruptedException {
        displayGroceries(inventory);
        System.out.println("\nWholesale gets you a 30% discount on all products!");
        System.out.println("Name of product you'd like to order more of: ");
        String product = input.next();
        Product productName = inventory.getProductByName(product);
        if (productName == null) {
            System.out.println("This product is not in inventory, use the add option to add it to inventory.\n");
        } else {
            System.out.println("Enter amount: ");
            int quantity = input.nextInt();
            double payment = productName.getPrice() * quantity * 0.7;
            System.out.println("Order receipt:\nProduct: " + product + "\nQuantity: " + 
            quantity + "\nTotal price after 30% discount: " + payment);
            System.out.println("\nOrder has been sent...\n");
            Thread.sleep(2000);
            System.out.println("Order has been delieverd.\n");
            System.out.println(inventory.increaseQuantity(productName, quantity));
        }
    }
    /**
     * method to checkout all curent produts inside the basket and saving it to orderhistory.json.
     * sends all basket contents and total cost of it to Order class to save it in json.
     * @param basket instance of basket class.
     * @param customer
     * @param input Scanner to handle user inputs
     * @throws InterruptedException
     */
    private static void checkOut(Basket basket) throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("\nAll products have been scanned\n total is $" + basket.getTotalCost());
        Order order = new Order(basket.getBasket(), basket.getTotalCost());
        order.writeOrderToJson();
        System.out.println("\n        Receipt    ");
        System.out.println("----------------------------");
        displayBasket(basket);
        System.out.println("----------------------------");
    }
    /**
     * method to display all Orders from orderhistory.json in by sorting it in order of total cost.
     * uses comparator and sort() to compare each content inside the list of maps in order of highest total cost.
     */
    private static void displayOrderHistory() {
        Order order = new Order();
        List<Map<String, Object>> orderHistory = order.readOrderHistoryFromJson();
        Collections.sort(orderHistory, new Comparator<Map<String, Object>>() {
        @Override
        public int compare(Map<String, Object> order1, Map<String, Object> order2) {
            double totalPrice1 = (double) order1.get("Total price");
            double totalPrice2 = (double) order2.get("Total price");
            return Double.compare(totalPrice2, totalPrice1);
        }});
        for(Map<String, Object> o : orderHistory) { 
            System.out.println("Products:");
            for (Map.Entry<String, Object> entry : o.entrySet()) { 
                if (entry.getKey().equals("Products")) {
                    Map<String, Integer> products = (Map<String, Integer>) entry.getValue();
                    for (Map.Entry<String, Integer> product : products.entrySet()) {
                        System.out.println("- " + product.getKey() + ": " + product.getValue());
                    }
                }
            }
            System.out.println("Date & time: " + o.get("Date & time"));
            System.out.println("Total price: " + o.get("Total price"));
            System.out.println();
        }
    }
    /**
     * A simulator that creates a set amount of customers shopping for groceries
     * By using threads to run the customers concurrently instead of 1 by 1.
     * @param inventory instance of inventory class
     * @throws InterruptedException to help slow down to read whats happening in the simulator.
     */
    private static void simulateFoodStore(Inventory inventory) throws InterruptedException {
        System.out.println("Simulation mode activated. Press any key to start.");
        Thread[] threads = new Thread[5]; // Create an array of threads
        for (int i = 0; i < 5; i++) {
            // Create a new thread for each customer
            int id = i + 1;
            threads[i] = new Thread(() -> {
                try {
                    // runs the method concurrently to simulate multiple customers at the same time shopping
                    simulateCustomer(id, inventory);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            threads[i].start(); // Start the thread
            Thread.sleep(1000);
        }
        for (Thread t : threads) {
            t.join();
        }
        System.out.println("Simulation mode deactivated.");
    }
    /**
     * a method to simulate a customer shopping for the simulation mode
     * @param id takes in id for each customer created
     * @param inventory 
     * @throws InterruptedException 
     */
    private static void simulateCustomer(int id, Inventory inventory) throws InterruptedException {
        Random rand = new Random();
        Basket customerBasket = new Basket(); // Creates a basket for each customer
        System.out.println("\nCustomer " + id + " has entered the store.");
        for (int j = 1; j <= 3; j++) {
            Thread.sleep(1000);
            Product product = getRandomProduct(inventory, rand);
            if (product.getQuantity() == 0) {
                System.out.println("\nCustomer " + id + ": God damn! These shit " + product.getName() + " are out of stock.");
                continue;
            } else {
                int quantity = rand.nextInt(10);
                customerBasket.addToBasket(product, quantity);
                inventory.decreaseQuantity(product, quantity);
                System.out.println("\nCustomer " + id + " has added " + quantity + " " + product.getName() + " to their basket.");
            }
        }
        Order checkOut = new Order(customerBasket.getBasket(), customerBasket.getTotalCost());
        checkOut.writeOrderToJson();
        System.out.println("\nCustomer " + id + " is finished and pays $" + customerBasket.getTotalCost());
        customerBasket.clearBasket();
    }

    /**
     * a method to return a random product from the inventory to the simulator.
     * @param inventory
     * @param rand
     * @return the random product.
     */
    private static Product getRandomProduct(Inventory inventory, Random rand) {
        int randomIndex = rand.nextInt(inventory.getProducts().size());
        Product randomProduct = inventory.getProducts().get(randomIndex);
        return randomProduct;
    }
}
