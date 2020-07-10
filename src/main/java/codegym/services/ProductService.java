package codegym.services;

import codegym.model.Product;

public interface ProductService {
    Product findOne(Long id);
}
