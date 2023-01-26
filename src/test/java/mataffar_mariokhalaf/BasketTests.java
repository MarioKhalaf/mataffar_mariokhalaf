package mataffar_mariokhalaf;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

class BasketTests {

    @Test
    public void shouldAddProductToBasket() {
    // Creating a mock Product object
    Product mockProduct = mock(Product.class);
    when(mockProduct.getName()).thenReturn("apples");
    // Adding the mock Product to the basket
    Basket mockBasket = new Basket();
    String result = mockBasket.addToBasket(mockProduct, 5);
    assertEquals("apples Has been added to your basket.\n", result);
    // Verifying that the product has been added to the basket with the correct quantity
    assertEquals(5, (int) mockBasket.getBasket().get(mockProduct));
    }

    @Test
    public void shouldAddQuantityToExistingProductInBasket() {
        Product product = new Product("apples", 5.99, 10);
        Basket basket = new Basket();
        basket.addToBasket(product, 5); // inserts a product to test it
        String result = basket.addToBasket(product, 7);
        assertEquals("New apples quantity: 12\n", result);
        assertEquals(12, basket.getQuantity(product));
    }

    @Test
    public void shouldRemoveProductFromBasket() {
        // here i create a mock product object
        Product mockProduct = mock(Product.class);
        Basket mockBasket = mock(Basket.class);
        when(mockBasket.removeFromBasket(mockProduct, 5)).thenReturn("5 apples removed from basket.\n");
        String result = mockBasket.removeFromBasket(mockProduct, 5);
        assertEquals("5 apples removed from basket.\n", result);
        // and i verify that the mock Product has been removed from the mock Basket
        verify(mockBasket).removeFromBasket(mockProduct, 5);
    }

}
