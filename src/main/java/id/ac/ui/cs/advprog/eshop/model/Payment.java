package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class Payment {
    private static final Set<String> VALID_METHODS = Set.of("voucher", "credit_card", "bank_transfer");

    private String id;
    private String orderId;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String orderId, String method, Map<String, String> paymentData) {
        this.id = id;
        this.orderId = orderId;
        setMethod(method);
        this.status = PaymentStatus.PENDING.getValue();
        this.paymentData = paymentData;
    }

    public void setStatus(String status) {
        if (!PaymentStatus.isValid(status)) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
        this.status = status;
    }

    public void setMethod(String method) {
        if (!VALID_METHODS.contains(method)) {
            throw new IllegalArgumentException("Invalid payment method: " + method);
        }
        this.method = method;
    }
}