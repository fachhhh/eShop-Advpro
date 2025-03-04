package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class OrderRepository {
    private List<Order> orderData = new ArrayList<>();

    public Order save(Order order) {
        if (order.getId() == null) {
            UUID uuid = UUID.randomUUID();
            order.setId(uuid.toString());
        }

        // Check if order already exists (for updates)
        for (int i = 0; i < orderData.size(); i++) {
            if (orderData.get(i).getId().equals(order.getId())) {
                orderData.set(i, order);
                return order;
            }
        }

        // If not exists, add new order
        orderData.add(order);
        return order;
    }

    public Order findById(String id) {
        for (Order order : orderData) {
            if (order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    }

    public Iterator<Order> findAll() {
        return orderData.iterator();
    }

    public List<Order> findAllByAuthor(String author) {
        List<Order> authorOrders = new ArrayList<>();
        for (Order order : orderData) {
            if (order.getAuthor().equals(author)) {
                authorOrders.add(order);
            }
        }
        return authorOrders;
    }

    public void delete(String id) {
        orderData.removeIf(order -> order.getId().equals(id));
    }
}