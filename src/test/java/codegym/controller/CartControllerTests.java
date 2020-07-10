package codegym.controller;

import codegym.model.Cart;
import codegym.model.Product;
import codegym.repository.ProductRepository;
import codegym.services.CartService;
import codegym.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class CartControllerTests {

    private MockMvc mockMvc;

    @InjectMocks
    private CartController cartController;

    @InjectMocks
    private ProductService productService = Mockito.mock(ProductService.class);

    @BeforeEach
    void setup() {

        Product product = new Product();
        product.setId(1L);
        Mockito.when(productService.findOne(1L)).thenReturn(product);

        Product product2 = new Product();
        product2.setId(2L);
        Mockito.when(productService.findOne(2L)).thenReturn(product2);

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
    }

    @Test
    void test_get_cart_page() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(status().is(200))
                .andExpect(view().name("myCart"))
        ;
    }

    @Test
    void test_delete_cart_by_id() throws Exception {
        mockMvc.perform(get("/deletecart/1"))
                .andExpect(status().is(200))
                .andExpect(view().name("myCart"));
    }

    @Test
    void test_totalPriceReturn0_when_cartEmpty() {
        HashMap<Long, Cart> carts = new HashMap<>();
        int actualResult = this.cartController.totalPrice(carts);

        Assertions.assertEquals(0, actualResult);
    }


    @Test
    void test_totalPriceReturnTotal_when_cartHasOneProduct() {

        int expectedTotalPrice = 50000;
        HashMap<Long, Cart> carts = new HashMap<>();
        Product product = new Product();
        product.setPrice(expectedTotalPrice);

        Cart cart = new Cart();
        cart.setProducts(product);
        cart.setAmount(1);

        carts.put(1L, cart);
        int actualResult = this.cartController.totalPrice(carts);

        Assertions.assertEquals(expectedTotalPrice, actualResult);
    }


    @Test
    void test_totalPriceReturnTotal_when_cartHasTwoProduct() {

        HashMap<Long, Cart> carts = new HashMap<>();
        Product product = new Product();
        product.setPrice(5000);

        Cart cart = new Cart();
        cart.setProducts(product);
        cart.setAmount(2);

        carts.put(1L, cart);

        int actualResult = this.cartController.totalPrice(carts);

        Assertions.assertEquals(10000, actualResult);
    }

    @Test
    void test_insertDupplicatedProduct_then_increaseAmountInCart() {
        HashMap<Long, Cart> carts = new HashMap<>();
        cartController.insertProductToCart(carts, 1L);
        cartController.insertProductToCart(carts, 1L);

        Cart cart = carts.get(1L);
        Assertions.assertEquals(1, carts.size());
        Assertions.assertEquals(new Integer(2), cart.getAmount());
    }

    @Test
    void test_insertDifferentProduct_then_increaseCartInHashmap() {
        HashMap<Long, Cart> carts = new HashMap<>();
        cartController.insertProductToCart(carts, 1L);
        cartController.insertProductToCart(carts, 2L);

        Assertions.assertEquals(2, carts.size());

        Cart cart1 = carts.get(1L);
        Assertions.assertEquals(new Integer(1), cart1.getAmount());

        Cart cart2 = carts.get(2L);
        Assertions.assertEquals(new Integer(1), cart2.getAmount());
    }
}
