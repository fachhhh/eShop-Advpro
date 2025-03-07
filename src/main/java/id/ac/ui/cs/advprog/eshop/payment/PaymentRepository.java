package id.ac.ui.cs.advprog.eshop.payment;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository {
    private final List<Payment> paymentData = new ArrayList<>();

    public Payment save(Payment payment) {
        int i = 0;
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getId().equals(payment.getId())) {
                paymentData.set(i, payment);
                return savedPayment;
            }
            i++;
        }

        paymentData.add(payment);
        return payment;
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

    public List<Payment> findAll() {
        return new ArrayList<>(paymentData);
    }
}