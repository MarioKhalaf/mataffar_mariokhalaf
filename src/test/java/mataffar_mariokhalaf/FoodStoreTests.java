package mataffar_mariokhalaf;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Scanner;

import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;


public class FoodStoreTests {

    @Test
    public void testInsertProductToJsonFile() {
        Inventory mockInventory = mock(Inventory.class);
        Scanner mockScanner = mock(Scanner.class);
        // set up mock scanner to return specific values when next() method is called
        when(mockScanner.next()).thenReturn("apples", "5.99", "10");
        // accessing method in a static way
        FoodStore.insertProductToJsonFile(mockInventory, mockScanner);

        // use mockito to verify that the add() method was called on the mock inventory object with the correct product
        Product expectedProduct = new Product("apples", 5.99, 10);
        verify(mockInventory).add(expectedProduct);
    }
    
}
