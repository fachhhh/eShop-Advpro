package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        sampleProduct.setProductName("Sample Product");
        sampleProduct.setProductQuantity(100);
    }

    @Test
    void testCreate_Success() {
        when(productRepository.create(any(Product.class))).thenReturn(sampleProduct);

        Product result = productService.create(sampleProduct);

        assertNotNull(result);
        assertEquals(sampleProduct.getProductId(), result.getProductId());
        assertEquals(sampleProduct.getProductName(), result.getProductName());
        assertEquals(sampleProduct.getProductQuantity(), result.getProductQuantity());
        verify(productRepository, times(1)).create(sampleProduct);
    }

    @Test
    void testCreate_Null() {
        when(productRepository.create(null)).thenReturn(null);

        Product result = productService.create(null);

        assertNull(result);
        verify(productRepository, times(1)).create(null);
    }

    @Test
    void testFindAll_WithProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(sampleProduct);

        Product anotherProduct = new Product();
        anotherProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd7");
        anotherProduct.setProductName("Another Product");
        anotherProduct.setProductQuantity(50);
        productList.add(anotherProduct);

        when(productRepository.findAll()).thenReturn(productList.iterator());

        List<Product> results = productService.findAll();

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(sampleProduct.getProductName(), results.get(0).getProductName());
        assertEquals(anotherProduct.getProductName(), results.get(1).getProductName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindAll_EmptyList() {
        when(productRepository.findAll()).thenReturn(new ArrayList<Product>().iterator());

        List<Product> results = productService.findAll();

        assertNotNull(results);
        assertTrue(results.isEmpty());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Found() {
        when(productRepository.findById(sampleProduct.getProductId())).thenReturn(sampleProduct);

        Product result = productService.findById(sampleProduct.getProductId());

        assertNotNull(result);
        assertEquals(sampleProduct.getProductId(), result.getProductId());
        assertEquals(sampleProduct.getProductName(), result.getProductName());
        assertEquals(sampleProduct.getProductQuantity(), result.getProductQuantity());
        verify(productRepository, times(1)).findById(sampleProduct.getProductId());
    }

    @Test
    void testFindById_NotFound() {
        String nonExistentId = "non-existent-id";
        when(productRepository.findById(nonExistentId)).thenReturn(null);

        Product result = productService.findById(nonExistentId);

        assertNull(result);
        verify(productRepository, times(1)).findById(nonExistentId);
    }

    @Test
    void testDelete_Success() {
        String id = sampleProduct.getProductId();
        doNothing().when(productRepository).delete(id);

        productService.delete(id);

        verify(productRepository, times(1)).delete(id);
    }

    @Test
    void testDelete_NullId() {
        productService.delete(null);

        verify(productRepository, times(1)).delete(null);
    }

    @Test
    void testUpdate_Success() {
        Product updatedProduct = new Product();
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductQuantity(200);

        when(productRepository.update(sampleProduct.getProductId(), updatedProduct))
                .thenReturn(updatedProduct);

        Product result = productService.update(sampleProduct.getProductId(), updatedProduct);

        assertNotNull(result);
        assertEquals(updatedProduct.getProductName(), result.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), result.getProductQuantity());
        verify(productRepository, times(1)).update(sampleProduct.getProductId(), updatedProduct);
    }

    @Test
    void testUpdate_NotFound() {
        String nonExistentId = "non-existent-id";
        Product updatedProduct = new Product();

        when(productRepository.update(nonExistentId, updatedProduct)).thenReturn(null);

        Product result = productService.update(nonExistentId, updatedProduct);

        assertNull(result);
        verify(productRepository, times(1)).update(nonExistentId, updatedProduct);
    }

    @Test
    void testUpdate_NullProduct() {
        when(productRepository.update(sampleProduct.getProductId(), null)).thenReturn(null);

        Product result = productService.update(sampleProduct.getProductId(), null);

        assertNull(result);
        verify(productRepository, times(1)).update(sampleProduct.getProductId(), null);
    }
}