package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(List<Product> products, String author) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one product");
        }

        if (author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Author cannot be empty");
        }

        Order order = new Order();
        order.setProducts(products);
        order.setAuthor(author);
        order.setOrderTime(System.currentTimeMillis());

        return orderRepository.save(order);
    }

    @Override
    public Order findById(String id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findAllOrders() {
        Iterator<Order> orderIterator = orderRepository.findAll();
        List<Order> allOrders = new ArrayList<>();
        orderIterator.forEachRemaining(allOrders::add);
        return allOrders;
    }

    @Override
    public List<Order> findAllOrdersByAuthor(String author) {
        return orderRepository.findAllByAuthor(author);
    }

    @Override
    public Order updateOrderStatus(String id, String status) {
        Order order = orderRepository.findById(id);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
        }
        return order;
    }
}