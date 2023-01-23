package mataffar_mariokhalaf;

import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FoodStore {
    public static void main(String[] args) throws InterruptedException {
        Inventory inventory = new Inventory();
        Basket basket = new Basket();
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add product to inventory");
            System.out.println("2. Remove product from inventory");
            System.out.println("3. Re-stock inventory.");
            System.out.println("4. Start shopping.");
            System.out.println("5. Order History.");
            System.out.println("6. Exit");

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
                    displayOrderHistory();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
                    break;
            }
        }
    }

    private static void insertProductToJsonFile(Inventory inventory, Scanner input) {
        System.out.println("Enter product name: ");
        String pName = input.next();
        System.out.println("Enter product price: ");
        float price = input.nextFloat();
        System.out.println("Enter product quantity: ");
        int quantity = input.nextInt();
        Product addProduct = new Product(pName, price, quantity);
        System.out.println(inventory.add(addProduct, quantity));
    }

    private static void deleteProductFromJsonFile(Inventory inventory, Scanner input) {
        System.out.println("Enter the name of the product you want to remove: ");
        String name = input.next();
        Product product = inventory.getProductByName(name);
        System.out.println(inventory.remove(product, 0));
    }

    private static void groceryShopping(Inventory inventory, Basket basket, Scanner input) throws InterruptedException {
        Customer customer = new Customer();
        System.out.println("\nWelcome to the Food Store " + customer.getName() + "\nAdd products to your basket by entering its name.");
        while (true) {
            System.out.println("\n1. View basket.\n2. Checkout.");
            displayGroceries(inventory);
            String option = input.next();
            if (option.equals("1")) {
                System.out.println("\n*** Your current basket ***\n");
                adjustBasket(basket, inventory, input);
                continue;
            } else if (option.equals("2")) {
                checkOut(basket, customer, input);
                break;
            }
            Product chosenProduct = inventory.getProductByName(option);
            System.out.println("\nEnter amount: ");
            try {
                int amount = input.nextInt();
                if (chosenProduct == null) {
                    System.out.println("\nProduct not found in inventory\n");
                    continue;
                } else if (chosenProduct.getQuantity() < amount) {
                    System.out.println("\n" + option + " are out of stock\n");
                    continue;
                }
                System.out.println(basket.add(chosenProduct, amount));
                inventory.decreaseQuantity(chosenProduct, amount);
                inventory.saveProductsToJson();
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Enter a valid amount");
                input.next();
            }
        }
    }

    private static void displayGroceries(Inventory inventory) {
        System.out.println("\nProduct  | Amount | Price");
        System.out.println("-------------------------");
        List<Product> products = inventory.browseProducts();
        for (Product p : products) { // prints out lines aligned with eachother
            System.out.printf("%-8s | %-6d | %-8.2f\n", p.getName(), p.getQuantity(), p.getPrice());
        }
    }

    private static void displayBasket(Basket basket) {
        System.out.println("Product  | Amount | Price");
        System.out.println("-------------------------"); // using entry method to iterate through map
        Map<Product, Integer> customerBasket = basket.getBasket();
        for (Map.Entry<Product, Integer> entry : customerBasket.entrySet()) { // prints out lines aligned with eachother
            System.out.printf("%-8s | %-6d | %-8.2f\n", entry.getKey().getName(),
                    entry.getValue(), entry.getKey().getPrice() * entry.getValue());
        }
        double totalCost = basket.getTotalCost();
        System.out.println("\nTotal cost: $" + totalCost + "\n");
    }

    private static void adjustBasket(Basket basket, Inventory inventory, Scanner input) {
        while (true) {
            displayBasket(basket);
            System.out.println("1. Continue shopping\n2. Add.\n3. Remove.");
            int option = input.nextInt();
            if (option == 1)
                break;
            System.out.println("Enter name of product: ");
            String name = input.next();
            Product productName = inventory.getProductByName(name);
            if (productName == null) { 
                System.out.println("There is no '" + name + "' in the basket.\n");
            } else if (option == 2) {
                System.out.println("Enter amount you'd like to add: ");
                int amount = input.nextInt();
                if (amount > productName.getQuantity()) { // prevents user from entering number higher than whats in inventory
                    System.out.println("There are only " + productName.getQuantity() + " " + name + " left in stock.\n");
                } else {
                    inventory.decreaseQuantity(productName, amount);
                    String message = basket.add(productName, amount);
                    System.out.println(message);
                }
            } else if (option == 3) {
                System.out.println("Enter amount you'd like to remove: ");
                int amount = input.nextInt();
                int quantity = basket.getQuantity(productName);
                if (quantity - amount < 0) {
                    amount = quantity; // if amount is more than quantity, set amount equal to quantity to not become negative number
                } 
                String message = basket.remove(productName, amount);
                inventory.increaseQuantity(productName, amount);
                System.out.println(message);
            }
        }
    }

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

    private static void checkOut(Basket basket, Customer customer, Scanner input) throws InterruptedException {
        System.out.println("Welcome to the check out, please put all your products on the conveyor belt.");        
        Thread.sleep(3000);
        System.out.println("All products have been scanned\nYour total is $" + basket.getTotalCost());
        Order order = new Order(basket.getBasket(), basket.getTotalCost());
        order.saveOrderToJson();
        System.out.println("\n---Here is your receipt---");
        System.out.println("----------------------------");
        displayBasket(basket);
        System.out.println("----------------------------");
    }

    private static void displayOrderHistory() {
        Order order = new Order();
        List<Map<String, Object>> orderHistory = order.getOrderHistory();
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
}