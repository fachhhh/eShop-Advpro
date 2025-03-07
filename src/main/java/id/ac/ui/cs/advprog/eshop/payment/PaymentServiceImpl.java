package id.ac.ui.cs.advprog.eshop.payment;

import id.ac.ui.cs.advprog.eshop.order.OrderStatus;

import id.ac.ui.cs.advprog.eshop.order.Order;
import id.ac.ui.cs.advprog.eshop.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
    }

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment(
                UUID.randomUUID().toString(),
                order.getId(),
                method,
                paymentData
        );
        payment.setStatus(PaymentStatus.PENDING.getValue());
        return paymentRepository.save(payment);
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        if (!PaymentStatus.isValid(status)) {
            throw new IllegalArgumentException("Invalid payment status: " + status);
        }

        // Change this part to handle non-Optional return type
        Payment existingPayment = paymentRepository.findById(payment.getId());
        if (existingPayment == null) {
            throw new IllegalStateException("Payment not found: " + payment.getId());
        }

        existingPayment.setStatus(status);

        if (status.equals(PaymentStatus.SUCCESS.getValue())) {
            orderService.updateStatus(existingPayment.getOrderId(), OrderStatus.SUCCESS.getValue());
        } else if (status.equals(PaymentStatus.REJECTED.getValue())) {
            orderService.updateStatus(existingPayment.getOrderId(), OrderStatus.FAILED.getValue());
        }

        return paymentRepository.save(existingPayment);
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}