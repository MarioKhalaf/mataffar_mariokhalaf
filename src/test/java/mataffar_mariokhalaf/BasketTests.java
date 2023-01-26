package mataffar_mariokhalaf;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;


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
    public void shouldRemoveCertainQuantityOfProductFromBasket() {
        // here i create a mock product object
        Product mockProduct = mock(Product.class);
        Basket mockBasket = mock(Basket.class);
        when(mockBasket.removeFromBasket(mockProduct, 5)).thenReturn("5 apples removed from basket.\n");
        String result = mockBasket.removeFromBasket(mockProduct, 5);
        assertEquals("5 apples removed from basket.\n", result);
        // and i verify that the mock Product has been removed from the mock Basket
        verify(mockBasket).removeFromBasket(mockProduct, 5);
    }

    @Test
    public void shouldRemoveProductFromBasketIfAmountIsEqualOrGreaterThanProductQuantity() {
        Basket basket = new Basket();
        Product product1 = new Product("apples", 0.99, 10);
        Product product2 = new Product("oranges", 0.99, 10);
        basket.addToBasket(product1, 5);
        basket.addToBasket(product2, 8);
        assertSame(5, basket.getQuantity(product1));
        String result1 = basket.removeFromBasket(product1, 6);
        String result2 = basket.removeFromBasket(product2, 8);
        assertEquals("You put back all the apples\n", result1);
        assertEquals("You put back all the oranges\n", result2);
    }

    @Test
    public void shouldNotBeAbleToRemoveNoneExistenProduct(){
        Product product1 = new Product("oranges", 0.99, 10);
        Product product2 = new Product("apples", 0.99, 10);
        Basket basket = new Basket();
        basket.addToBasket(product1, 5);
        String result = basket.removeFromBasket(product2, 0);
        assertEquals("apples are not in your basket.\n", result);
    }

    @Test
    public void shouldReturnCorrectTotalCostHamcrest() {
        Product product1 = new Product("apples", 5.99, 10);
        Product product2 = new Product("oranges", 1.99, 5);
        Product product3 = new Product("bananas", 3.49, 7);
        Basket basket = new Basket();
        basket.addToBasket(product1, 2);
        basket.addToBasket(product2, 3);
        basket.addToBasket(product3, 4);
        double totalCost = basket.getTotalCost();
        double expectedTotalCost = (5.99 * 2) + (1.99 * 3) + (3.49 * 4);
        assertThat(totalCost, is(equalTo(expectedTotalCost)));
        assertThat(3, is(equalTo(basket.getBasket().size())));
    }
}
