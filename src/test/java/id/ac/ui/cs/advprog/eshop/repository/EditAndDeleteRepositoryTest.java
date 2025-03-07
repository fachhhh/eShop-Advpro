package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.product.Product;
import id.ac.ui.cs.advprog.eshop.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EditAndDeleteRepositoryTest {
    @InjectMocks
    ProductRepository productRepository;

    Product product1;
    Product product2;

    @BeforeEach
    void setUp() {
        // Create and store initial test products
        product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Product 1");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        product2 = new Product();
        product2.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd7");
        product2.setProductName("Product 2");
        product2.setProductQuantity(200);
        productRepository.create(product2);
    }

    // Positive test cases for Edit feature
    @Test
    void testUpdateProduct_Success() {
        Product updatedProduct = new Product();
        updatedProduct.setProductName("Updated Product 1");
        updatedProduct.setProductQuantity(150);

        Product result = productRepository.update(product1.getProductId(), updatedProduct);

        assertNotNull(result);
        assertEquals("Updated Product 1", result.getProductName());
        assertEquals(150, result.getProductQuantity());
        assertEquals(product1.getProductId(), result.getProductId());
    }

    @Test
    void testUpdateProduct_PreservesOtherProducts() {
        Product updatedProduct = new Product();
        updatedProduct.setProductName("Updated Product 1");
        updatedProduct.setProductQuantity(150);

        productRepository.update(product1.getProductId(), updatedProduct);

        Product product2FromRepo = productRepository.findById(product2.getProductId());
        assertNotNull(product2FromRepo);
        assertEquals(product2.getProductName(), product2FromRepo.getProductName());
        assertEquals(product2.getProductQuantity(), product2FromRepo.getProductQuantity());
    }

    // Negative test cases for Edit feature
    @Test
    void testUpdateProduct_NonexistentId() {
        Product updatedProduct = new Product();
        updatedProduct.setProductName("Updated Product");
        updatedProduct.setProductQuantity(150);

        Product result = productRepository.update("nonexistent-id", updatedProduct);

        assertNull(result);
    }

    @Test
    void testUpdateProduct_NullProduct() {
        Product result = productRepository.update(product1.getProductId(), null);
        assertNull(result);
    }

    // Positive test cases for Delete feature
    @Test
    void testDeleteProduct_Success() {
        productRepository.delete(product1.getProductId());

        Product deletedProduct = productRepository.findById(product1.getProductId());
        assertNull(deletedProduct);
    }

    @Test
    void testDeleteProduct_PreservesOtherProducts() {
        productRepository.delete(product1.getProductId());

        Product product2FromRepo = productRepository.findById(product2.getProductId());
        assertNotNull(product2FromRepo);
        assertEquals(product2.getProductName(), product2FromRepo.getProductName());
        assertEquals(product2.getProductQuantity(), product2FromRepo.getProductQuantity());
    }

    // Negative test cases for Delete feature
    @Test
    void testDeleteProduct_NonexistentId() {
        assertDoesNotThrow(() -> productRepository.delete("nonexistent-id"));
    }

    @Test
    void testDeleteProduct_NullId() {
        assertDoesNotThrow(() -> productRepository.delete(null));
    }
}