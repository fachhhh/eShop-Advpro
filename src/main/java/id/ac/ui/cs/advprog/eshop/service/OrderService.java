package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;

import java.util.List;

public interface OrderService {
    Order createOrder(List<Product> products, String author);
    Order findById(String id);
    List<Order> findAllOrders();
    List<Order> findAllOrdersByAuthor(String author);
    Order updateOrderStatus(String id, String status);
}