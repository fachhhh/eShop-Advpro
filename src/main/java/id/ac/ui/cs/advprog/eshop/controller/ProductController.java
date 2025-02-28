package id.ac.ui.cs.advprog.eshop.controller;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        System.out.println(product.getProductId());
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

@Controller
@RequestMapping("/car")
class CarController extends ProductController {
    @Autowired
    private CarServiceImpl carService;

    @GetMapping("/createCar")
    public String createCarPage(Model model) {
        Car car = new Car();
        model.addAttribute("car", car);
        return "createCar";
    }

    @PostMapping("/createCar")
    public String createCarPost(@ModelAttribute Car car, Model model) {
        carService.create(car);
        return "redirect:listCar";
    }

    @GetMapping("/listCar")
    public String carListPage(Model model) {
        List<Car> allCars = carService.findAll();
        model.addAttribute("cars", allCars);
        return "carList";
    }

    @GetMapping("/editCar/{carId}")
    public String editCarPage(@PathVariable String carId, Model model) {
        Car car = carService.findById(carId);
        model.addAttribute("car", car);
        return "editCar";
    }

    @PostMapping("/editCar")
    public String editCarPost(@ModelAttribute Car car, Model model) {
        System.out.println(car.getCarId());
        carService.update(car.getCarId(), car);

        return "redirect:listCar";
    }

    @PostMapping("/deleteCar")
    public String deleteCar(@RequestParam("carId") String carId) {
        carService.deleteCarById(carId);
        return "redirect:listCar";
    }
}