package codegym.services;

import codegym.model.Product;
import codegym.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product findOne(Long id) {
        return productRepository.findOne(id);
    }
}
