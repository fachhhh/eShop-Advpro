package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import java.util.HashMap;
import java.util.Map;

public class PaymentRepository {
    private final Map<String, Payment> payments = new HashMap<>();

    public void save(Payment payment) {
        payments.put(payment.getId(), payment);
    }

    public Payment findById(String id) {
        Payment payment = payments.get(id);
        if (payment == null) {
            throw new IllegalArgumentException("Payment with ID " + id + " not found");
        }
        return payment;
    }
}