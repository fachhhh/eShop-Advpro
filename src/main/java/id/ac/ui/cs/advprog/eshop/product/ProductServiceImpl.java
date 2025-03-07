package id.ac.ui.cs.advprog.eshop.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        productRepository.create(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        productIterator.forEachRemaining(products::add);
        return products;
    }

    @Override
    public Product findById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        productRepository.delete(id);
    }

    @Override
    public Product update(String id, Product product) {
        return productRepository.update(id, product);
    }
}