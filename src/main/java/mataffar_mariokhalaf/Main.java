package mataffar_mariokhalaf;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FoodStore foodStore = new FoodStore();
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
                    insertProductToJsonFile(foodStore, input);
                    break;
                case 2:
                    // remove product from json file
                    deleteProductFromJsonFile(foodStore, input);
                    break;
                case 3:
                    // --- something ---
                    break;
                case 4:
                    System.out.println("Welcome to the Foodstore! What is your name?: ");
                    String name = input.next();
                    groceryShopping(foodStore, input, name);
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

    private static void insertProductToJsonFile(FoodStore foodStore, Scanner input) {
        System.out.println("Enter product name: ");
        String pName = input.next();
        System.out.println("Enter product price: ");
        float price = input.nextFloat();
        System.out.println("Enter product quantity: ");
        int quantity = input.nextInt();
        Product addProduct = new Product(pName, price, quantity);
        foodStore.addProductToInventory(addProduct);
    }

    private static void deleteProductFromJsonFile(FoodStore foodStore, Scanner input) {
        System.out.println("Enter the name of the product you want to remove: ");
        String productName = input.next();
        foodStore.removeProductFromInventory(productName);
        foodStore.saveProductsToJson();
        System.out.println("Product removed from inventory.");
    }

    private static void groceryShopping(FoodStore foodStore, Scanner input, String name) {
        System.out.println("\nEnter Q once you're ready to checkout and view your basket.\n");
        while (true) {
            displayGroceries(foodStore);
                System.out.println("\nChoose the products you would like by entering its name:");
                String option = input.next();
                if (option.equalsIgnoreCase("q")) {
                    viewBasket(foodStore, input);
                    continue;
                }
                Product chosenProduct = foodStore.getProductByName(option);
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
                    foodStore.addToBasket(chosenProduct, amount);
                    System.out.println("\n" + amount + " " + option + " added to your basket.");
                    foodStore.decreaseInventoryQuantity(chosenProduct, amount);
                    foodStore.saveProductsToJson();
                } catch (InputMismatchException e) {
                    System.out.println("\nInvalid input. Enter a valid amount");
                    input.next();
                }
        }
    }

    private static void displayGroceries(FoodStore foodStore) {
        System.out.println("\nProduct  | Amount | Price");
        System.out.println("-------------------------");
        List<Product> products = foodStore.browseProducts();
        for (Product p : products) {    //prints out lines aligned with eachother
            System.out.printf("%-8s | %-6d | %-8.2f\n", p.getName(), p.getQuantity(), p.getPrice());        }
        }

    private static void viewBasket(FoodStore foodStore, Scanner input) {
        System.out.println("\n*** Your current basket ***\n");
        System.out.println("Product  | Amount | Price");
        System.out.println("-------------------------"); // using entry method to iterate through map
        Map<Product, Integer> customerBasket = foodStore.getBasket();
        for (Map.Entry<Product, Integer> entry : customerBasket.entrySet()) { //prints out lines aligned with eachother
            System.out.printf("%-8s | %-6d | %-8.2f\n", entry.getKey().getName(), entry.getValue(), entry.getKey().getPrice() * entry.getValue());
        }
        double totalCost = foodStore.getTotalCost();
        System.out.println("\nYour total cost: $" + totalCost);
        System.out.println("\n1. Go to checkout");
        System.out.println("2. Continue shopping");
        String choice = input.next().toLowerCase();
        if (choice.equals("1")) {
            // foodStore.checkOut();
        } else if (choice.equals("2")) {
            System.out.println("Continuing shopping...");
        }
    }
}
