package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class Payment {
    private static final Set<String> VALID_STATUSES = Set.of("PENDING", "SUCCESS", "REJECTED");
    private static final Set<String> VALID_METHODS = Set.of("voucher", "credit_card", "bank_transfer");

    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    // Constructor to initialize Payment object
    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        this.id = id;
        setMethod(method);  // Validate method
        setStatus(status);  // Validate status
        this.paymentData = paymentData;
    }

    // Validate and set the status
    public void setStatus(String status) {
        if (!VALID_STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
        this.status = status;
    }

    // Validate and set the payment method
    public void setMethod(String method) {
        if (!VALID_METHODS.contains(method)) {
            throw new IllegalArgumentException("Invalid payment method: " + method);
        }
        this.method = method;
    }
}
