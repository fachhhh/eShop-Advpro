package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.product.Product;
import id.ac.ui.cs.advprog.eshop.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        // Ensure repository is empty before each test
    }

    @Test
    void testCreate() {
        Product product = new Product();
        product.setProductName("Sample Product");
        product.setProductQuantity(10);

        Product result = productRepository.create(product);

        assertNotNull(result);
        assertNotNull(result.getProductId());
        assertEquals("Sample Product", result.getProductName());
        assertEquals(10, result.getProductQuantity());
    }

    @Test
    void testCreateNullProduct() {
        Product result = productRepository.create(null);
        assertNull(result);
    }

    @Test
    void testFindAll() {
        // Add two products
        Product product1 = new Product();
        product1.setProductName("Product 1");
        product1.setProductQuantity(10);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductName("Product 2");
        product2.setProductQuantity(20);
        productRepository.create(product2);

        // Test find all
        Iterator<Product> result = productRepository.findAll();

        assertTrue(result.hasNext());
        Product foundProduct1 = result.next();
        assertEquals(product1.getProductName(), foundProduct1.getProductName());

        assertTrue(result.hasNext());
        Product foundProduct2 = result.next();
        assertEquals(product2.getProductName(), foundProduct2.getProductName());

        assertFalse(result.hasNext());
    }

    @Test
    void testFindAllEmpty() {
        Iterator<Product> result = productRepository.findAll();
        assertFalse(result.hasNext());
    }

    @Test
    void testFindById() {
        // Create a product
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductQuantity(15);
        Product savedProduct = productRepository.create(product);

        // Find by ID
        Product foundProduct = productRepository.findById(savedProduct.getProductId());

        assertNotNull(foundProduct);
        assertEquals(savedProduct.getProductId(), foundProduct.getProductId());
        assertEquals("Test Product", foundProduct.getProductName());
        assertEquals(15, foundProduct.getProductQuantity());
    }

    @Test
    void testFindByIdNullId() {
        Product result = productRepository.findById(null);
        assertNull(result);
    }

    @Test
    void testFindByIdNonExistent() {
        Product result = productRepository.findById("non-existent-id");
        assertNull(result);
    }

    @Test
    void testUpdate() {
        // Create a product
        Product product = new Product();
        product.setProductName("Original Name");
        product.setProductQuantity(10);
        Product savedProduct = productRepository.create(product);

        // Update the product
        Product updatedDetails = new Product();
        updatedDetails.setProductName("Updated Name");
        updatedDetails.setProductQuantity(20);

        Product result = productRepository.update(savedProduct.getProductId(), updatedDetails);

        assertNotNull(result);
        assertEquals(savedProduct.getProductId(), result.getProductId());
        assertEquals("Updated Name", result.getProductName());
        assertEquals(20, result.getProductQuantity());
    }

    @Test
    void testUpdateNullId() {
        Product updatedDetails = new Product();
        Product result = productRepository.update(null, updatedDetails);
        assertNull(result);
    }

    @Test
    void testUpdateNullProduct() {
        // Create a product
        Product product = new Product();
        product.setProductName("Original Name");
        product.setProductQuantity(10);
        Product savedProduct = productRepository.create(product);

        Product result = productRepository.update(savedProduct.getProductId(), null);
        assertNull(result);
    }

    @Test
    void testUpdateNonExistentProduct() {
        Product updatedDetails = new Product();
        updatedDetails.setProductName("Updated Name");
        updatedDetails.setProductQuantity(20);

        Product result = productRepository.update("non-existent-id", updatedDetails);
        assertNull(result);
    }

    @Test
    void testDelete() {
        // Create a product
        Product product = new Product();
        product.setProductName("Product to Delete");
        product.setProductQuantity(5);
        Product savedProduct = productRepository.create(product);

        // Delete the product
        productRepository.delete(savedProduct.getProductId());

        // Verify it's deleted
        Product foundProduct = productRepository.findById(savedProduct.getProductId());
        assertNull(foundProduct);
    }

    @Test
    void testDeleteNullId() {
        // Should not throw exception
        assertDoesNotThrow(() -> productRepository.delete(null));
    }

    @Test
    void testDeleteNonExistentProduct() {
        // Should not throw exception
        assertDoesNotThrow(() -> productRepository.delete("non-existent-id"));
    }

    @Test
    void testFindByIdReturnsNullWhenIdIsNull() {
        // This test specifically targets the "if (id == null) { return null; }" condition
        Product result = productRepository.findById(null);
        assertNull(result, "findById should return null when id parameter is null");
    }

    @Test
    void testUpdateReturnsNullWhenIdIsNull() {
        // This test specifically targets the first part of "if (id == null || updatedProduct == null) { return null; }"
        Product updatedDetails = new Product();
        updatedDetails.setProductName("Updated Name");
        updatedDetails.setProductQuantity(20);

        Product result = productRepository.update(null, updatedDetails);
        assertNull(result, "update should return null when id parameter is null");
    }

    @Test
    void testUpdateReturnsNullWhenProductIsNull() {
        // This test specifically targets the second part of "if (id == null || updatedProduct == null) { return null; }"
        // First create a product to get a valid ID
        Product product = new Product();
        product.setProductName("Original Name");
        product.setProductQuantity(10);
        Product savedProduct = productRepository.create(product);

        // Then try to update with null product
        Product result = productRepository.update(savedProduct.getProductId(), null);
        assertNull(result, "update should return null when updatedProduct parameter is null");
    }

    @Test
    void testCreateReturnsNullWhenProductIsNull() {
        // This test specifically targets the "if (product == null) { return null; }" condition
        Product result = productRepository.create(null);
        assertNull(result, "create should return null when product parameter is null");
    }
}