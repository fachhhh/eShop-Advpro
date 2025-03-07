package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.product.Product;
import id.ac.ui.cs.advprog.eshop.product.ProductController;
import id.ac.ui.cs.advprog.eshop.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProductPage() {
        Model model = new BindingAwareModelMap();
        String viewName = productController.createProduct(model);

        assertEquals("CreateProduct", viewName);
        assertTrue(model.containsAttribute("product"));
    }

    @Test
    void testCreateProductPost() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        when(productService.create(product)).thenReturn(product);

        Model model = new BindingAwareModelMap();
        String viewName = productController.createProduct(product, model);

        assertEquals("redirect:/product/list", viewName);
        verify(productService, times(1)).create(product);
    }

    @Test
    void testListProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());

        when(productService.findAll()).thenReturn(products);

        Model model = new BindingAwareModelMap();
        String viewName = productController.listProduct(model);

        assertEquals("ProductList", viewName);
        assertTrue(model.containsAttribute("products"));
        assertEquals(products, model.getAttribute("products"));
    }

    @Test
    void testDeleteProductGet() {
        String id = "test-id";

        String viewName = productController.deleteProductGet(id);

        assertEquals("redirect:/product/list", viewName);
        verify(productService, times(1)).delete(id);
    }

    @Test
    void testDeleteProductPost() {
        String id = "test-id";

        String viewName = productController.deleteProductPost(id);

        assertEquals("redirect:/product/list", viewName);
        verify(productService, times(1)).delete(id);
    }

    @Test
    void testEditProductForm_ProductExists() {
        String id = "test-id";
        Product product = new Product();
        product.setProductId(id);

        when(productService.findById(id)).thenReturn(product);

        Model model = new BindingAwareModelMap();
        String viewName = productController.editProductForm(id, model);

        assertEquals("EditProduct", viewName);
        assertTrue(model.containsAttribute("product"));
        assertEquals(product, model.getAttribute("product"));
    }

    @Test
    void testEditProductForm_ProductNotFound() {
        String id = "non-existent-id";

        when(productService.findById(id)).thenReturn(null);

        Model model = new BindingAwareModelMap();
        String viewName = productController.editProductForm(id, model);

        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testEditProduct_Success() {
        String id = "test-id";
        Product product = new Product();
        product.setProductName("Updated Name");
        product.setProductQuantity(20);

        when(productService.update(id, product)).thenReturn(product);

        Model model = new BindingAwareModelMap();
        String viewName = productController.editProduct(id, product, model);

        assertEquals("redirect:/product/list", viewName);
        verify(productService, times(1)).update(id, product);
    }

    @Test
    void testEditProduct_Failure() {
        String id = "test-id";
        Product product = new Product();

        when(productService.update(id, product)).thenReturn(null);

        Model model = new BindingAwareModelMap();
        String viewName = productController.editProduct(id, product, model);

        assertEquals("redirect:/product/list", viewName);
    }
}