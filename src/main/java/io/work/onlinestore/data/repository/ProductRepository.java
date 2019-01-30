package io.work.onlinestore.data.repository;

import io.work.onlinestore.data.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    Product findByProductId(Integer productId);

    @Transactional
    int deleteByProductId(Integer productId);
}
