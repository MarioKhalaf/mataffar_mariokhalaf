package mataffar_mariokhalaf;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.junit.jupiter.api.Test;


public class FoodStoreTests {
 

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
    public void testGroceryShoppingOutOfStock() throws InterruptedException {
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

    @Test
    public void shouldCreateSuccessfullOrder() throws InterruptedException {
        Inventory mockInventory = mock(Inventory.class);
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.next()).thenReturn("apples");
        when(mockScanner.nextInt()).thenReturn(10);
        when(mockInventory.getProductByName("apples")).thenReturn(new Product("apples", 5.99, 15));

        FoodStore.inventoryOrder(mockInventory, mockScanner);
        verify(mockInventory, times(1)).getProductByName("apples");
        verify(mockInventory, times(1)).increaseQuantity(any(Product.class), eq(10));
    }

    @Test
    public void shouldNotCreateOrderIfProdcutDoesNotExistInInventory() throws InterruptedException {
        Inventory mockInventory = mock(Inventory.class);
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.next()).thenReturn("oranges", "1");
        when(mockInventory.getProductByName("oranges")).thenReturn(null);
        
        FoodStore.inventoryOrder(mockInventory, mockScanner);
        verify(mockInventory, times(1)).getProductByName("oranges");
        verify(mockInventory, never()).increaseQuantity(any(Product.class), anyInt());
    }

  
}