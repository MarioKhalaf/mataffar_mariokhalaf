package mataffar_mariokhalaf;

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
            System.out.println("5. Exit");

            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    insertProductToJsonFile(inventory, input);
                    break;
                case 2:
                    deleteProductFromJsonFile(inventory, input);
                    break;
                case 3:
                    inventoryOrder(inventory, input);
                    break;
                case 4:
                    groceryShopping(inventory, basket, input);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
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
        String productName = input.next();
        Product product = inventory.getProductByName(productName);
        System.out.println(inventory.remove(product, product.getQuantity()));
    }

    private static void groceryShopping(Inventory inventory, Basket basket, Scanner input) {
        System.out.println("\nWelcome to the Food Store\nAdd products to your basket by entering its name.");
        while (true) {
            displayGroceries(inventory);
            System.out.println("\n1. View basket.\n2. Checkout.");
            String option = input.next();
            if (option.equals("1")) {
                System.out.println("\n*** Your current basket ***\n");
                adjustBasket(basket, inventory, input);
                continue;
            } else if (option.equals("2")) {
                System.out.println("Checking out...");
                checkOut();
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
                inventory.remove(chosenProduct, amount);
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
            System.out.println("1. Continue shopping\n2. Adjust quantity.\n3. Remove product.");
            int option = input.nextInt();
            if (option == 1)
                break;
            System.out.println("Enter name of product: ");
            String name = input.next();
            Product productName = inventory.getProductByName(name);
            if (productName == null){
                System.out.println("There is no '" + name + "' in the basket.");
            } else if (option == 2) {
                System.out.println("Enter amount you'd like to remove: ");
                int amount = input.nextInt();
                basket.remove(productName, amount);
                inventory.add(productName, amount);
            } else if (option == 3) {
                basket.remove(productName, 0);
                inventory.add(productName, 0);
            }
        }
    }

    private static void inventoryOrder(Inventory inventory, Scanner input) throws InterruptedException {
        displayGroceries(inventory);
        System.out.println("Name of product you'd like to order more of: ");
        String product = input.next();
        Product productName = inventory.getProductByName(product);
        System.out.println("Enter amount: ");
        int amount = input.nextInt();
        System.out.println("Order has been sent...");
        Thread.sleep(2000);
        System.out.println(inventory.add(productName, amount));
        System.out.println("Order has been delieverd.\n");
    }
    
    private static void checkOut() {

    }
}
