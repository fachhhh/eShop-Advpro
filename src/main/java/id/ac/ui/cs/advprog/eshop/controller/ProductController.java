package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    // Define constant for the repeated redirect URL
    private static final String REDIRECT_PRODUCT_LIST = "redirect:/product/list";

    @GetMapping("/create")
    public String createProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "CreateProduct";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product, Model model) {
        productService.create(product);
        return REDIRECT_PRODUCT_LIST;
    }

    @GetMapping("/list")
    public String listProduct(Model model) {
        model.addAttribute("products", productService.findAll());
        return "ProductList";
    }

    // Support both GET and POST for delete to make it more flexible
    @GetMapping("/delete/{id}")
    public String deleteProductGet(@PathVariable String id) {
        productService.delete(id);
        return REDIRECT_PRODUCT_LIST;
    }

    @PostMapping("/delete/{id}")
    public String deleteProductPost(@PathVariable String id) {
        productService.delete(id);
        return REDIRECT_PRODUCT_LIST;
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable String id, Model model) {
        Product product = productService.findById(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "EditProduct";
        }
        return REDIRECT_PRODUCT_LIST;
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable String id, @ModelAttribute Product product, Model model) {
        Product updatedProduct = productService.update(id, product);
        if (updatedProduct != null) {
            return REDIRECT_PRODUCT_LIST;
        }
        return REDIRECT_PRODUCT_LIST;
    }
}