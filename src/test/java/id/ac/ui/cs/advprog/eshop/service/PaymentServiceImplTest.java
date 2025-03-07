package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    private PaymentServiceImpl paymentService;
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = mock(PaymentRepository.class);
        paymentService = new PaymentServiceImpl(paymentRepository);
    }

    @Test
    void testAddPayment() {
        Order order = new Order();
        order.setId("ORDER-001");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = paymentService.addPayment(order, "voucher", paymentData);

        assertNotNull(payment.getId());
        assertEquals("ORDER-001", payment.getOrderId());
        assertEquals("voucher", payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
        assertEquals(paymentData, payment.getPaymentData());

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusToSuccess() {
        Payment payment = new Payment();
        Order order = new Order();
        order.setId("ORDER-001");
        order.setStatus("WAITING_PAYMENT");

        payment.setId("PAY-001");
        payment.setOrderId("ORDER-001");
        payment.setStatus("PENDING");

        when(paymentRepository.findById("PAY-001")).thenReturn(Optional.of(payment));

        paymentService.setStatus(payment, "SUCCESS");

        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("SUCCESS", order.getStatus());

        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = new Payment();
        Order order = new Order();
        order.setId("ORDER-001");
        order.setStatus("WAITING_PAYMENT");

        payment.setId("PAY-001");
        payment.setOrderId("ORDER-001");
        payment.setStatus("PENDING");

        when(paymentRepository.findById("PAY-001")).thenReturn(Optional.of(payment));

        paymentService.setStatus(payment, "REJECTED");

        assertEquals("REJECTED", payment.getStatus());
        assertEquals("FAILED", order.getStatus());

        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testGetPayment() {
        Payment payment = new Payment();
        payment.setId("PAY-001");

        when(paymentRepository.findById("PAY-001")).thenReturn(Optional.of(payment));

        Payment result = paymentService.getPayment("PAY-001");

        assertNotNull(result);
        assertEquals("PAY-001", result.getId());

        verify(paymentRepository, times(1)).findById("PAY-001");
    }

    @Test
    void testGetAllPayments() {
        Payment payment1 = new Payment();
        payment1.setId("PAY-001");

        Payment payment2 = new Payment();
        payment2.setId("PAY-002");

        when(paymentRepository.findAll()).thenReturn(List.of(payment1, payment2));

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(2, result.size());
        assertEquals("PAY-001", result.get(0).getId());
        assertEquals("PAY-002", result.get(1).getId());

        verify(paymentRepository, times(1)).findAll();
    }
}