package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.payment.PaymentServiceImpl;
import id.ac.ui.cs.advprog.eshop.payment.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.payment.Payment;
import id.ac.ui.cs.advprog.eshop.payment.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    private List<Payment> payments;

    @BeforeEach
    void setUp() {
        payments = new ArrayList<>();

        // Using the correct constructor without status parameter
        Map<String, String> paymentData1 = new HashMap<>();
        Payment payment1 = new Payment("PAY-001", "ORDER-001", "voucher", paymentData1);

        Map<String, String> paymentData2 = new HashMap<>();
        Payment payment2 = new Payment("PAY-002", "ORDER-002", "credit_card", paymentData2);

        payments.add(payment1);
        payments.add(payment2);
    }

    // Happy path test: Set payment status to SUCCESS
    @Test
    void testSetStatusToSuccess() {
        Payment payment = payments.get(0);
        doReturn(Optional.of(payment)).when(paymentRepository).findById(payment.getId());

        paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    // Unhappy path test: Set payment status with invalid status
    @Test
    void testSetStatusInvalid() {
        Payment payment = payments.get(0);
        doReturn(Optional.of(payment)).when(paymentRepository).findById(payment.getId());

        assertThrows(IllegalArgumentException.class, () -> paymentService.setStatus(payment, "INVALID"));
        verify(paymentRepository, times(0)).save(payment);
    }

    // Unhappy path test: Set status for a non-existing payment
    @Test
    void testSetStatusForNonExistingPayment() {
        Payment nonExistingPayment = new Payment("UNKNOWN", "ORDER-UNKNOWN", "voucher", new HashMap<>());
        doReturn(Optional.empty()).when(paymentRepository).findById("UNKNOWN");

        assertThrows(IllegalStateException.class, () ->
                paymentService.setStatus(nonExistingPayment, PaymentStatus.SUCCESS.getValue()));
    }

    // Happy path test: Retrieve a payment by ID
    @Test
    void testGetPayment() {
        Payment payment = payments.get(0);
        doReturn(Optional.of(payment)).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());
        assertNotNull(result);
        assertEquals(payment.getId(), result.getId());
    }

    // Unhappy path test: Retrieve a payment with an unknown ID
    @Test
    void testGetPaymentNotFound() {
        doReturn(Optional.empty()).when(paymentRepository).findById("UNKNOWN");
        assertNull(paymentService.getPayment("UNKNOWN"));
    }

    // Happy path test: Retrieve all payments
    @Test
    void testGetAllPayments() {
        doReturn(payments).when(paymentRepository).findAll();
        List<Payment> result = paymentService.getAllPayments();

        assertEquals(2, result.size());
        assertEquals(payments.get(0).getId(), result.get(0).getId());
        assertEquals(payments.get(1).getId(), result.get(1).getId());
    }
}
