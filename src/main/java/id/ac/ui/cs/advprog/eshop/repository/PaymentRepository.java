package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository {
    private final List<Payment> paymentData = new ArrayList<>();

    public void save(Payment payment) {
        int i = 0;
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getId().equals(payment.getId())) {
                paymentData.set(i, payment);
                return;
            }
            i++;
        }

        paymentData.add(payment);
    }

    public Payment findById(String id) {
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getId().equals(id)) {
                return savedPayment;
            }
        }
        return null;
    }

    public List<Payment> findAllByMethod(String method) {
        List<Payment> result = new ArrayList<>();
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getMethod().equalsIgnoreCase(method)) {
                result.add(savedPayment);
            }
        }
        return result;
    }
}