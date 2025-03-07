package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;
    private Payment payment1;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        payment1 = new Payment("PAY-001", "voucher", "PENDING", paymentData1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("cardNumber", "4111111111111111");
        paymentData2.put("expiryDate", "12/25");
        payment2 = new Payment("PAY-002", "credit_card", "PENDING", paymentData2);
    }

    // Happy path: Test saving a new payment and retrieving it
    @Test
    void testSaveAndRetrievePayment() {
        paymentRepository.save(payment1);

        Optional<Payment> retrieved = paymentRepository.findById("PAY-001");
        assertTrue(retrieved.isPresent());
        assertEquals("voucher", retrieved.get().getMethod());
        assertEquals("PENDING", retrieved.get().getStatus());
    }

    // Happy path: Test updating an existing payment
    @Test
    void testSaveUpdatePayment() {
        paymentRepository.save(payment1);
        payment1.setStatus("SUCCESS");
        paymentRepository.save(payment1);

        Optional<Payment> updatedPayment = paymentRepository.findById("PAY-001");
        assertTrue(updatedPayment.isPresent());
        assertEquals("SUCCESS", updatedPayment.get().getStatus());
    }
}
