package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.payment.Payment;
import id.ac.ui.cs.advprog.eshop.payment.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;
    private Payment payment1;
    private Payment payment2;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        // Fixed: Changed constructor call to match actual implementation
        payment1 = new Payment("PAY-001", "ORDER-001", "voucher", paymentData1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("cardNumber", "4111111111111111");
        paymentData2.put("expiryDate", "12/25");
        // Fixed: Changed constructor call to match actual implementation
        payment2 = new Payment("PAY-002", "ORDER-002", "credit_card", paymentData2);
    }

    // Happy path: Test saving a new payment and retrieving it
    @Test
    void testSaveAndRetrievePayment() {
        paymentRepository.save(payment1);

        Payment retrieved = paymentRepository.findById("PAY-001");
        assertNotNull(retrieved);
        assertEquals("voucher", retrieved.getMethod());
        assertEquals("PENDING", retrieved.getStatus());
    }

    // Happy path: Test updating an existing payment
    @Test
    void testSaveUpdatePayment() {
        paymentRepository.save(payment1);
        payment1.setStatus("SUCCESS");
        paymentRepository.save(payment1);

        Payment updatedPayment = paymentRepository.findById("PAY-001");
        assertNotNull(updatedPayment);
        assertEquals("SUCCESS", updatedPayment.getStatus());
    }

    // Unhappy path: Test retrieving a non-existent payment
    @Test
    void testFindByIdIfNotFound() {
        Payment result = paymentRepository.findById("INVALID-ID");
        assertNull(result);
    }

    // Happy path: Test saving multiple payments and retrieving them
    @Test
    void testSaveMultiplePayments() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        Payment retrieved1 = paymentRepository.findById("PAY-001");
        Payment retrieved2 = paymentRepository.findById("PAY-002");

        assertNotNull(retrieved1);
        assertNotNull(retrieved2);
        assertEquals("voucher", retrieved1.getMethod());
        assertEquals("credit_card", retrieved2.getMethod());
    }

    // Happy path: Test finding all payments by method
    @Test
    void testFindAllByMethod() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        List<Payment> vouchers = paymentRepository.findAllByMethod("voucher");
        List<Payment> creditCards = paymentRepository.findAllByMethod("credit_card");
        List<Payment> bankTransfers = paymentRepository.findAllByMethod("bank_transfer");

        assertEquals(1, vouchers.size());
        assertEquals(1, creditCards.size());
        assertEquals(0, bankTransfers.size());

        assertEquals("voucher", vouchers.get(0).getMethod());
        assertEquals("credit_card", creditCards.get(0).getMethod());
    }
}