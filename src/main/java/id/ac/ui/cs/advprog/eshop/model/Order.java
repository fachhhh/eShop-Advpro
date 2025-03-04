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
}