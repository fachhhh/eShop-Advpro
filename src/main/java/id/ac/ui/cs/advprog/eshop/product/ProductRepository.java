package id.ac.ui.cs.advprog.eshop.product;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        if (product == null) {
            return null;
        }
        product.setProductId(UUID.randomUUID().toString());
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String id) {
        if (id == null) {
            return null;
        }
        return productData.stream()
                .filter(product -> id.equals(product.getProductId()))
                .findFirst()
                .orElse(null);
    }

    public void delete(String id) {
        if (id != null) {
            productData.removeIf(product -> id.equals(product.getProductId()));
        }
    }

    public Product update(String id, Product updatedProduct) {
        if (id == null || updatedProduct == null) {
            return null;
        }

        for (Product product : productData) {
            if (id.equals(product.getProductId())) {
                product.setProductName(updatedProduct.getProductName());
                product.setProductQuantity(updatedProduct.getProductQuantity());
                return product;
            }
        }
        return null;
    }
}