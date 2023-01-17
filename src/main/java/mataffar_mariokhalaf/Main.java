package mataffar_mariokhalaf;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FoodStore FoodStore = new FoodStore();
        Scanner input = new Scanner(System.in);
        Customer customer = new Customer();
        Product product = new Product();

        while (true) {
            System.out.println("1. Add product to inventory");
            System.out.println("2. Remove product from inventory");
            System.out.println("3. Update inventory");
            System.out.println("4. List of all products in the food store.");
            System.out.println("5. Check out customer");
            System.out.println("6. Exit");

            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    // Create new products
                    System.out.println("Enter product name: ");
                    String name = input.next();
                    System.out.println("Enter product price: ");
                    float price = input.nextFloat();
                    System.out.println("Enter product quantity: ");
                    int quantity = input.nextInt();
                    Product addProduct = new Product(name, price, quantity);
                    FoodStore.addProduct(addProduct);
                    break;
                case 2:
                    // remove product from inventory
                    break;
                case 3:
                    // update products and fill stock);
                    break;
                case 4:
                    // check out customer
                    break;
                case 5:        
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;

                // more cases for more options later
            }
        }
    }
}
