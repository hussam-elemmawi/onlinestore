package io.work.onlinestore.data.repository;

import io.work.onlinestore.data.model.Phone;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PhoneRepository extends CrudRepository<Phone, Phone.CompositeKey> {
    List<Phone> findAllByCustomerId(Integer customerId);
}
