package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @GetMapping("/create")
    public String createOrderPage(Model model) {
        List<Product> allProducts = productService.findAll();
        model.addAttribute("products", allProducts);
        return "createOrder";
    }

    @PostMapping("/create")
    public String createOrder(@RequestParam("productIds") List<String> productIds, Principal principal) {
        if (productIds == null || productIds.isEmpty()) {
            return "redirect:/order/create?error=noProducts";
        }

        List<Product> selectedProducts = new ArrayList<>();
        for (String productId : productIds) {
            Product product = productService.findById(productId);
            if (product != null) {
                selectedProducts.add(product);
            }
        }

        String username = principal != null ? principal.getName() : "anonymous";
        Order order = orderService.createOrder(selectedProducts, username);

        return "redirect:/order/list";
    }

    @GetMapping("/list")
    public String orderListPage(Model model, Principal principal) {
        String username = principal != null ? principal.getName() : "anonymous";
        List<Order> orders = orderService.findAllOrdersByAuthor(username);
        model.addAttribute("orders", orders);
        return "orderList";
    }

    @GetMapping("/detail/{id}")
    public String orderDetail(@PathVariable String id, Model model, Principal principal) {
        Order order = orderService.findById(id);

        if (order == null) {
            return "redirect:/order/list?error=notFound";
        }

        String username = principal != null ? principal.getName() : "anonymous";
        if (!order.getAuthor().equals(username)) {
            return "redirect:/order/list?error=unauthorized";
        }

        model.addAttribute("order", order);
        return "orderDetail";
    }

    @PostMapping("/update/{id}")
    public String updateOrderStatus(@PathVariable String id, @RequestParam("status") String status, Principal principal) {
        Order order = orderService.findById(id);

        if (order == null) {
            return "redirect:/order/list?error=notFound";
        }

        String username = principal != null ? principal.getName() : "anonymous";
        if (!order.getAuthor().equals(username)) {
            return "redirect:/order/list?error=unauthorized";
        }

        orderService.updateOrderStatus(id, status);
        return "redirect:/order/detail/" + id;
    }
}