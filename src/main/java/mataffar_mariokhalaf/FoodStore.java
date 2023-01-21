package mataffar_mariokhalaf;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FoodStore {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        Basket basket = new Basket();
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add product to inventory");
            System.out.println("2. Remove product from inventory");
            System.out.println("3. Check inventory count");
            System.out.println("4. Start shopping.");
            System.out.println("5. Exit");

            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    // Add products to json file
                    insertProductToJsonFile(inventory, input);
                    break;
                case 2:
                    // remove product from json file
                    deleteProductFromJsonFile(inventory, input);
                    break;
                case 3:
                    System.out.println(inventory.checkInventoryCount());
                    break;
                case 4:
                    groceryShopping(inventory, basket, input);
                    break;
                case 5:
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
        inventory.addProductToInventory(addProduct);
        System.out.println(quantity + pName + " Has been added to the inventory.\n");
    }

    private static void deleteProductFromJsonFile(Inventory inventory, Scanner input) {
        System.out.println("Enter the name of the product you want to remove: ");
        String productName = input.next();
        inventory.removeProductFromInventory(productName);
        System.out.println(productName + " Has been removed from the inventory.\n");
    }

    private static void groceryShopping(Inventory inventory, Basket basket, Scanner input) {
        System.out.println("\nEnter Q once you're ready to checkout and view your basket.\n");
        while (true) {
            displayGroceries(inventory);
            System.out.println("\nChoose the products you would like by entering its name:");
            String option = input.next();
            if (option.equalsIgnoreCase("q")) {
                if (viewBasket(basket, input)) {
                    System.out.println("Checking out...");
                    break;
                } else continue;
            }
            Product chosenProduct = inventory.getProductByName(option);
            if (chosenProduct == null) {
                System.out.println("\nProduct not found in inventory\n");
                continue;
            }
            System.out.println("\nEnter the quantity of the product you want to add to your basket: ");
            try {
                int amount = input.nextInt();
                if (chosenProduct.getQuantity() < amount) {
                    System.out.println("\nThe product is out of stock\n");
                    continue;
                }
                basket.addToBasket(chosenProduct, amount);
                System.out.println("\n" + amount + " " + option + " added to your basket.");
                inventory.decreaseInventoryQuantity(chosenProduct, amount);
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

    private static boolean viewBasket(Basket basket, Scanner input) {
        System.out.println("\n*** Your current basket ***\n");
        System.out.println("Product  | Amount | Price");
        System.out.println("-------------------------"); // using entry method to iterate through map
        Map<Product, Integer> customerBasket = basket.getBasket();
        for (Map.Entry<Product, Integer> entry : customerBasket.entrySet()) { // prints out lines aligned with eachother
            System.out.printf("%-8s | %-6d | %-8.2f\n", entry.getKey().getName(), entry.getValue(),
                    entry.getKey().getPrice() * entry.getValue());
        }
        double totalCost = basket.getTotalCost();
        System.out.println("\nYour total cost: $" + totalCost);
        System.out.println("\n1. Go to checkout");
        System.out.println("2. Continue shopping");
        String choice = input.next().toLowerCase();
        if (choice.equals("1")) {
            return true;
        } 
        return false;
    }
}
