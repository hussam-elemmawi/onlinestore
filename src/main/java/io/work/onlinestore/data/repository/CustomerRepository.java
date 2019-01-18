package io.work.onlinestore.data.repository;

import io.work.onlinestore.data.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    @Transactional
    void deleteByCustomerId(Integer customerId);
}
