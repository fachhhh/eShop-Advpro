package id.ac.ui.cs.advprog.eshop.order;

import java.util.List;

public interface OrderService {
    public Order createOrder(Order order);
    public Order updateStatus(String orderId, String status);
    public Order findById(String order);
    public List<Order> findAllByAuthor(String author);
}