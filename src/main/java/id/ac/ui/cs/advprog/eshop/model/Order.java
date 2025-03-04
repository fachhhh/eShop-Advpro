package id.ac.ui.cs.advprog.eshop.model;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class Order {
    private String id;
    private List<Product> products;
    private long orderTime;
    private String author;
    private String status;

    /*
    private static List<String> VALID_STATUS = Arrays.asList("WAITING_PAYMENT", "FAILED", "CANCELLED", "SUCCESS");

    public Order(List<Product> products, String author) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one product");
        }
        this.products = products;
        this.author = author;
        this.orderTime = System.currentTimeMillis();
        this.status = "WAITING_PAYMENT";
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProducts(List<Product> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one product");
        }
        this.products = products;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setStatus(String status) {
        if (status == null || !VALID_STATUS.contains(status)) {
            return;
        }
        this.status = status;
    }
     */
}