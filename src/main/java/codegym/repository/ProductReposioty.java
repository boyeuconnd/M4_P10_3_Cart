package codegym.repository;

import codegym.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReposioty extends CrudRepository<Product,Long> {
}
