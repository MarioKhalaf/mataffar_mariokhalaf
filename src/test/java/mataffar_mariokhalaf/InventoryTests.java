package mataffar_mariokhalaf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryTests {
    private Inventory inventory;
    private Product product;

    @BeforeEach
    public void setUp() {
        // method will run before each test method
        inventory = new Inventory();
        product = new Product("cookies", 0.99, 10);
    }
    
    //using the add and remove methods after finishing each test to not mess with rest of tests
    @Test
    public void shouldAddProductToInventory() {
        String result = inventory.add(product);
        assertEquals("cookies has been added to the inventory.\n", result);
        inventory.remove(product);  
    }

    @Test
    public void ShouldNotAddExisitingProductToInventory() {
        inventory.add(product);
        String result = inventory.add(product);
        assertEquals("cookies is already in inventory.\n", result);
        inventory.remove(product);
    }

    @Test
    public void shouldRemoveProductFromInventory() {
        inventory.add(product);
        String result = inventory.remove(product);
        assertEquals("cookies has been removed from inventory", result);
    }

    @Test
    public void shouldNotRemoveNoneExistenProduct() {
        String result = inventory.remove(product);
        assertEquals("Product does not exist in inventory.\n", result);
    }

    @Test
    public void shouldNotAddProductWithNegativePrice() {
        Inventory inventory = new Inventory();
        Product testProduct = new Product("cookies", -8.99, 10);
        String result = inventory.add(testProduct);
        assertEquals("Invalid price! This is not a charity.\n", result);
    }
    
    @Test
    public void shouldNotAddProductWithNegativeQuantity() {
        Inventory inventory = new Inventory();
        Product testProduct = new Product("cookies", 8.99, -10);
        String result = inventory.add(testProduct);
        assertEquals("Invalid quantity! Cannot enter negative quantity.\n", result);
    }

    @Test
    public void shouldGetProductObjectOrNullWithNameParameter() {
        // Creating a mock Product object
        Product mockProduct = mock(Product.class);
        when(mockProduct.getName()).thenReturn("cookies");
        // Adding the mock Product to the mock Inventory
        Inventory mockInventory = mock(Inventory.class);
        when(mockInventory.getProductByName("cookies")).thenReturn(mockProduct);

        assertEquals(mockProduct, mockInventory.getProductByName("cookies"));
        assertNull(mockInventory.getProductByName("not in inventory"));
    }

    @Test
    public void shouldIncreaseQuantity() {
        inventory.add(product);
        String result = inventory.increaseQuantity(product, 5);
        assertEquals("New cookies quantity: 15\n", result);
        inventory.remove(product);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionForNonExistentProduct() {
        assertThrows(IllegalArgumentException.class, () -> inventory.increaseQuantity(
        new Product("not in inventory", 0, 0), 5));
    }


}