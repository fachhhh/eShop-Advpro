package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
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

        Payment payment1 = new Payment("PAY-001", "ORDER-001", "voucher", PaymentStatus.PENDING.getValue(), new HashMap<>());
        Payment payment2 = new Payment("PAY-002", "ORDER-002", "credit_card", PaymentStatus.PENDING.getValue(), new HashMap<>());

        payments.add(payment1);
        payments.add(payment2);
    }

    // Happy path test: Add a new payment successfully
    @Test
    void testAddPayment() {
        Payment payment = payments.get(0);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(new Order("ORDER-001"), "voucher", new HashMap<>());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertNotNull(result);
        assertEquals(payment.getOrderId(), result.getOrderId());
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
        doReturn(Optional.empty()).when(paymentRepository).findById("UNKNOWN");
        assertThrows(NoSuchElementException.class, () -> paymentService.setStatus(new Payment("UNKNOWN"), PaymentStatus.SUCCESS.getValue()));
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
