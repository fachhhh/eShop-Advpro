package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment(UUID.randomUUID().toString(), order.getId(), method, PaymentStatus.PENDING.getValue(), paymentData);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        if (!PaymentStatus.isValid(status)) {
            throw new IllegalArgumentException("Invalid payment status: " + status);
        }

        Optional<Payment> existingPayment = paymentRepository.findById(payment.getId());
        if (existingPayment.isEmpty()) {
            throw new IllegalStateException("Payment not found: " + payment.getId());
        }

        payment.setStatus(status);

        // Update order status based on payment status
        if (status.equals(PaymentStatus.SUCCESS.getValue())) {
            // Assume we have an OrderService to update order status
            // OrderService.updateStatus(payment.getOrderId(), OrderStatus.SUCCESS.getValue());
        } else if (status.equals(PaymentStatus.REJECTED.getValue())) {
            // OrderService.updateStatus(payment.getOrderId(), OrderStatus.FAILED.getValue());
        }

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId).orElse(null);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}