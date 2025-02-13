package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    Product product;

    @BeforeEach
    void setup() {
        this.product = new Product();
        this.product.setProductId("123e4567-e89b-12d3-a456-556642440000");
        this.product.setProductName("Product 1");
        this.product.setProductQuantity(100);
    }

    @Test
    public void testGetProductId() {
        assertEquals("123e4567-e89b-12d3-a456-556642440000", this.product.getProductId());
    }

    @Test
    public void testGetProductName() {
        assertEquals("Product 1", this.product.getProductName());
    }

    @Test
    public void testGetProductQuantity() {
        assertEquals(100, this.product.getProductQuantity());
    }

    // Negative Test Cases
    @Test
    public void testNullProductId() {
        product.setProductId(null);
        assertNull(product.getProductId());
    }

    @Test
    public void testEmptyProductName() {
        product.setProductName("");
        assertEquals("", product.getProductName());
    }

    @Test
    public void testNegativeProductQuantity() {
        product.setProductQuantity(-10);
        assertEquals(-10, product.getProductQuantity());
    }
}