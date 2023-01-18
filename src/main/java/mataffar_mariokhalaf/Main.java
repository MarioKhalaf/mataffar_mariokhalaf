package mataffar_mariokhalaf;

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
            System.out.println("4. Check out customer");
            System.out.println("5. List of all products in the food store.");
            System.out.println("6. Exit");

            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    // Add products to json file
                    System.out.println("Enter product name: ");
                    String pName = input.next();
                    System.out.println("Enter product price: ");
                    float price = input.nextFloat();
                    System.out.println("Enter product quantity: ");
                    int quantity = input.nextInt();
                    Product addProduct = new Product(pName, price, quantity);
                    foodStore.addProductToInventory(addProduct);
                    break;
                case 2:
                    // remove product from json file
                    break;
                case 3:
                    // --- something ---
                    break;
                case 4:
                    // check out customer
                    break;
                case 5:
                    System.out.println("what is your name?");
                    String name = input.next();
                    while (true) {
                        foodStore.browseProducts();
                        System.out.println("\nChoose the products you would like by entering its name: \n");
                        String productName = input.next();
                        try {
                            Product chosenProduct = foodStore.getProductByName(productName);
                            if(chosenProduct == null) {
                                throw new IllegalArgumentException("Product not found in inventory");
                            }
                            System.out.println("Enter the quantity of the product you want to add to your basket: ");
                            int amount = input.nextInt();
                            foodStore.decreaseQuantity(chosenProduct, amount);
                            Customer customer = new Customer(name);
                            customer.addToBasket(chosenProduct, amount);
                            System.out.println(chosenProduct + " added to basket your basket.");
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                            break;
                        }
                    }
                case 6:
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}

