package mataffar_mariokhalaf;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FoodStore foodStore = new FoodStore();
        Scanner input = new Scanner(System.in);
        Product product = new Product();

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
                    insertToJsonFile(foodStore, input);
                    break;
                case 2:
                    // remove product from json file
                    break;
                case 3:
                    // --- something ---
                    break;
                case 4:
                    System.out.println("Welcome to the Foodstore! What is your name?: ");
                    String name = input.next();
                    groceryShopping(foodStore, input, name);
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

    private static void insertToJsonFile(FoodStore foodStore, Scanner input) {
        System.out.println("Enter product name: ");
        String pName = input.next();
        System.out.println("Enter product price: ");
        float price = input.nextFloat();
        System.out.println("Enter product quantity: ");
        int quantity = input.nextInt();
        Product addProduct = new Product(pName, price, quantity);
        foodStore.addProductToInventory(addProduct);
    }

    private static void groceryShopping(FoodStore foodStore, Scanner input, String name) {
        System.out.println("\nEnter Q to view your basket \n");
        while (true) {
            displayGroceries(foodStore);
            try {
                System.out.println("\nChoose the products you would like by entering its name:");
                String option = input.next();
                if (option.equalsIgnoreCase("q")) {
                    viewBasket(foodStore, input);
                    continue;
                }
                Product chosenProduct = foodStore.getProductByName(option);
                if (chosenProduct == null) {
                    throw new IllegalArgumentException("\nProduct not found in inventory\n");
                }
                System.out.println("\nEnter the quantity of the product you want to add to your basket: ");
                int amount = input.nextInt();
                if (chosenProduct.getQuantity() < amount) {
                    throw new IllegalArgumentException("\nThe product is out of stock\n");
                }
                foodStore.addToBasket(chosenProduct, amount);
                System.out.println(amount + " " + option + " added to your basket.");
                foodStore.decreaseInventoryQuantity(chosenProduct, amount);
                foodStore.saveProductsToJson();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }

    private static void displayGroceries(FoodStore foodStore) {
        System.out.println("Product  | Amount | Price");
        System.out.println("-------------------------");
        List<Product> products = foodStore.browseProducts();
        for (Product p : products) {
            System.out.println(p.getName() + p);
        }
    }

    private static void viewBasket(FoodStore foodStore, Scanner input) {
        System.out.println("*** Your current basket ***\n");
        System.out.println("Product  | Amount | Price");
        System.out.println("-------------------------"); // using entry method to iterate through map
        Map<Product, Integer> customerBasket = foodStore.getBasket(); 
        for (Map.Entry<Product, Integer> entry : customerBasket.entrySet()) {
            System.out.println(entry.getKey().getName() + " |    " + entry.getValue()
             + "   | $"+ entry.getKey().getPrice() * entry.getValue());
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
