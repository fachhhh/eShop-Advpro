package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;
import id.ac.ui.cs.advprog.eshop.model.Order;

public interface OrderService {
    public Order createOrder(Order order);
    public Order updateStatus(String orderId, String status);
    public Order findById(String order);
    public List<Order> findAllByAuthor(String author);
}