package io.work.onlinestore.data.repository;

import io.work.onlinestore.data.model.OrderStatus;
import org.springframework.data.repository.CrudRepository;

public interface OrderStatusRepository extends CrudRepository<OrderStatus, Integer> {
}
