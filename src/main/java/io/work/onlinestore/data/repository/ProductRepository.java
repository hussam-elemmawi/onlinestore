package io.work.onlinestore.data.repository;

import io.work.onlinestore.data.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    Product findByProductId(String productId);
}
