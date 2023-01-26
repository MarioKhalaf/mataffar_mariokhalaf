package mataffar_mariokhalaf;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import static org.mockito.Mockito.*;


import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

public class FoodStoreTests {

    // @Test
    // public void shouldInsertProductToJsonFile() {
    //     Inventory mockInventory = mock(Inventory.class);
    //     Scanner mockScanner = mock(Scanner.class);

    //     when(mockScanner.next()).thenReturn("apples");
    //     when(mockScanner.nextDouble()).thenReturn(5.99);
    //     when(mockScanner.nextInt()).thenReturn(10);
    //     FoodStore.insertProductToJsonFile(mockInventory, mockScanner);
    //     Product expectedProduct = new Product("apples", 5.99, 10);
    //     verify(mockInventory).add(expectedProduct);
    // }

    @Test
    public void shouldDeleteProductFromJsonFile() {
        Inventory mockInventory = mock(Inventory.class);
        Scanner mockScanner = mock(Scanner.class);

        Product product = new Product("apples", 5.99, 10);
        when(mockScanner.next()).thenReturn("apples");
        when(mockInventory.getProductByName("apples")).thenReturn(product);
        FoodStore.deleteProductFromJsonFile(mockInventory, mockScanner);
        verify(mockInventory).remove(product);
    }

    @Test
    public void shouldGetRandomProduct() {
        Inventory inventory = new Inventory();
        Random rand = new Random();
        List<Product> products = inventory.getProducts();
        Product randomProduct = FoodStore.getRandomProduct(inventory, rand);
        assertTrue(products.contains(randomProduct));

    }

    @Test
    public void shouldGroceryShoppingProductNotFound() throws InterruptedException {
        Inventory mockInventory = mock(Inventory.class);
        Basket mockBasket = mock(Basket.class);
        Scanner mockScanner = mock(Scanner.class);

        when(mockScanner.next()).thenReturn("bananas", "1");
        when(mockInventory.getProductByName("bananas")).thenReturn(null);
        FoodStore.groceryShopping(mockInventory, mockBasket, mockScanner);
        verify(mockInventory, never()).decreaseQuantity(any(), anyInt());
        verify(mockBasket, never()).addToBasket(any(), anyInt());
        verify(mockInventory, never()).writeToJsonFile();
    }

    @Test
    public void testGroceryShopping_OutOfStock() throws InterruptedException {
        Inventory mockInventory = mock(Inventory.class);
        Basket mockBasket = mock(Basket.class);
        Scanner mockScanner = mock(Scanner.class);

        Product product = new Product("apples", 5.99, 10);
        when(mockScanner.next()).thenReturn("apples", "20", "1");
        when(mockInventory.getProductByName("apples")).thenReturn(product);
        FoodStore.groceryShopping(mockInventory, mockBasket, mockScanner);
        verify(mockInventory, never()).decreaseQuantity(product, 20);
        verify(mockBasket, never()).addToBasket(product, 20);
    }


}