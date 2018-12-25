package io.work.onlinestore.data.repository;

import io.work.onlinestore.data.model.ProductTagRelation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductTagRelationRepository extends CrudRepository<ProductTagRelation, Integer> {
    List<ProductTagRelation> findProductTagRelationByProductId(int productId);
    List<ProductTagRelation> findProductTagRelationByTagId(int tagId);
}
