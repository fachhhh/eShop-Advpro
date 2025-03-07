package id.ac.ui.cs.advprog.eshop.product;
import java.util.List;

public interface ProductService {
    Product create(Product product);
    List<Product> findAll();
    Product findById(String id);
    void delete(String id);
    Product update(String id, Product product);
}