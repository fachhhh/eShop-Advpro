package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    private Payment payment;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        payment = new Payment(
                "PAY-001",
                "voucher",
                "PENDING",
                paymentData
        );
    }

    // Test to verify default values when creating a Payment instance
    @Test
    void testCreatePaymentDefaultValues() {
        assertEquals("PAY-001", payment.getId());
        assertEquals("voucher", payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
        assertSame(paymentData, payment.getPaymentData());
    }

    // Happy path: Test setting status to "SUCCESS"
    @Test
    void testSetStatusToSuccess() {
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    // Happy path: Test setting status to "REJECTED"
    @Test
    void testSetStatusToRejected() {
        payment.setStatus("REJECTED");
        assertEquals("REJECTED", payment.getStatus());
    }

    // Unhappy path: Test setting an invalid status
    @Test
    void testSetStatusToInvalid() {
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("INVALID_STATUS"));
    }

    // Happy path: Test creating a payment with valid method "credit_card"
    @Test
    void testCreatePaymentWithCreditCard() {
        Map<String, String> ccData = new HashMap<>();
        ccData.put("cardNumber", "4111111111111111");
        ccData.put("expiryDate", "12/25");

        Payment creditCardPayment = new Payment("PAY-002", "credit_card", "PENDING", ccData);

        assertEquals("PAY-002", creditCardPayment.getId());
        assertEquals("credit_card", creditCardPayment.getMethod());
        assertEquals("PENDING", creditCardPayment.getStatus());
        assertSame(ccData, creditCardPayment.getPaymentData());
    }

    // Unhappy path: Test creating a payment with an unsupported method
    @Test
    void testCreatePaymentWithInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () -> new Payment("PAY-003", "bitcoin", "PENDING", new HashMap<>()));
    }

    // Happy path: Test updating payment data
    @Test
    void testUpdatePaymentData() {
        Map<String, String> newPaymentData = new HashMap<>();
        newPaymentData.put("bankName", "Bank ABC");
        newPaymentData.put("accountNumber", "1234567890");

        payment.setPaymentData(newPaymentData);
        assertSame(newPaymentData, payment.getPaymentData());
        assertEquals("Bank ABC", payment.getPaymentData().get("bankName"));
        assertEquals("1234567890", payment.getPaymentData().get("accountNumber"));
    }
}
