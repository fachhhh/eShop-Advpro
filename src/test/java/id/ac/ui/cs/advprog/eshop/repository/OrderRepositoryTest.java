package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.order.Order;
import id.ac.ui.cs.advprog.eshop.product.Product;
import id.ac.ui.cs.advprog.eshop.order.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import id.ac.ui.cs.advprog.eshop.order.OrderStatus;

import java.util.ArrayList;
import java.util.List;

class OrderRepositoryTest {

    OrderRepository orderRepository;
    List<Order> orders;

    @BeforeEach
    void setUp() {
        orderRepository = new OrderRepository();

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        orders = new ArrayList<>();
        Order order1 = new Order(
                "13652556-012a-4c07-b546-54eb139d79b",
                products,
                1708560000L,
                "Safira Sudrajat"
        );
        orders.add(order1);

        Order order2 = new Order(
                "f79e15bb-4b15-42f4-aebc-c3af385fb078",
                products,
                1708570000L,
                "Safira Sudrajat"
        );
        orders.add(order2);

        Order order3 = new Order(
                "e354ef40-9eff-4da8-9487-8ee697ecbf1e",
                products,
                1708570000L,
                "Bambang Sudrajat"
        );
        orders.add(order3);
    }

    // Create a happy path test: Use save to add new Order.
    @Test
    void testSaveCreate() {
        Order order = orders.get(1);
        Order result = orderRepository.save(order);

        Order findResult = orderRepository.findById(orders.get(1).getId());

        assertEquals(order.getId(), result.getId());
        assertEquals(order.getId(), findResult.getId());
        assertEquals(order.getOrderTime(), findResult.getOrderTime());
        assertEquals(order.getAuthor(), findResult.getAuthor());
        assertEquals(order.getStatus(), findResult.getStatus());
    }

    // Happy path test: Use save to update an Order.
    @Test
    void testSaveUpdate() {
        Order order = orders.get(1);
        orderRepository.save(order);

        Order newOrder = new Order(order.getId(), order.getProducts(), order.getOrderTime(),
                order.getAuthor(), OrderStatus.SUCCESS.getValue());
        Order result = orderRepository.save(newOrder);

        Order findResult = orderRepository.findById(orders.get(1).getId());
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getId(), findResult.getId());
        assertEquals(order.getOrderTime(), findResult.getOrderTime());
        assertEquals(order.getAuthor(), findResult.getAuthor());
        assertEquals(OrderStatus.SUCCESS.getValue(), findResult.getStatus());
    }

    // Happy path test: Use findById to find Order using a valid ID.
    @Test
    void testFindByIdIfFound() {
        for (Order order : orders) {
            orderRepository.save(order);
        }

        Order findResult = orderRepository.findById(orders.get(1).getId());
        assertEquals(orders.get(1).getId(), findResult.getId());
        assertEquals(orders.get(1).getOrderTime(), findResult.getOrderTime());
        assertEquals(orders.get(1).getAuthor(), findResult.getAuthor());
        assertEquals(orders.get(1).getStatus(), findResult.getStatus());
    }

    // Unhappy path test: Use findById to find Order using an ID that is never used.
    @Test
    void testFindByIdIfNotFound() {
        for (Order order : orders) {
            orderRepository.save(order);
        }

        Order findResult = orderRepository.findById("zzcc");
        assertNull(findResult);
    }

    @Test
    void testFindAllByAuthorCorrect() {
        for (Order order : orders) {
            orderRepository.save(order);
        }

        List<Order> orderList = orderRepository.findAllByAuthor(
                orders.get(1).getAuthor()
        );
        assertEquals(2,orderList.size());
    }

    @Test
    void testFindAllByAuthorIfallLowercase() {
        orderRepository.save(orders.get(1));

        List<Order> orderList = orderRepository.findAllByAuthor(
                orders.get(1).getAuthor().toLowerCase()
        );
        assertTrue(orderList.isEmpty());
    }
}