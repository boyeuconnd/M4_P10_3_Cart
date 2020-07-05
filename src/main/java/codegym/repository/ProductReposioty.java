package codegym.repository;

import codegym.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductReposioty extends CrudRepository<Product,Long> {
}
