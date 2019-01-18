package io.work.onlinestore.data.repository;

import io.work.onlinestore.data.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findAllByCustomerId(Integer customerId);
    List<Order> findAllByOrderStatusId(Integer orderStatusId);
    List<Order> findAllByPromotionId(Integer promotionId);
}
