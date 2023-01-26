package mataffar_mariokhalaf;

import static org.junit.jupiter.api.Assertions.*;
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
    
    // using the add and remove methods after finishing each test so every test is self contained
    // does not mess with more attempts of testing the same test.
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
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionForNonExistentProduct() {
        assertThrows(IllegalArgumentException.class, () -> inventory.increaseQuantity(
        new Product("not in inventory", 0, 0), 5));
    }

    @Test
    public void shouldNotIncreasingQuantityWithNegativeInput() {
        inventory.add(product);
        String result = inventory.increaseQuantity(product, -5);
        assertEquals("Invalid amount! No negative quantities.", result );
        inventory.remove(product);
    }

    @Test
    public void shouldNotDecreaseQuantityWithNegativeInput() {
        inventory.add(product);
        String result = inventory.decreaseQuantity(product, -5);
        assertEquals("Invalid amount! No negative quantities.", result);
        inventory.remove(product);
    }

    @Test
    public void shouldThrowIllegalArgumentWhenProductOutOfStock() {
        inventory.add(product);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
        inventory.decreaseQuantity(product, 15);
        });
        assertEquals("The product is out of stock", ex.getMessage());
        inventory.remove(product);
    }

    @Test
    public void shouldThrowIllegalArgumentWhenProductNotFoundInInventory() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
        inventory.decreaseQuantity(product, 5);
        });
        assertEquals("Product not found in inventory", ex.getMessage());
    }

    @Test
    public void shouldDecreaseQuantity() {
        inventory.add(product);
        String result = inventory.decreaseQuantity(product, 5);
        assertEquals("New cookies quantity: 5\n", result);
        inventory.remove(product);
    }   


    
}