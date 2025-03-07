package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PaymentRepository {
    private final Map<String, Payment> payments = new HashMap<>();

    public void save(Payment payment) {
        payments.put(payment.getId(), payment);
    }

    public Optional<Payment> findById(String id) {
        return Optional.ofNullable(payments.get(id));
    }
}