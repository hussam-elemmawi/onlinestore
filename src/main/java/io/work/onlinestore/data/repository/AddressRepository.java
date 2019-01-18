package io.work.onlinestore.data.repository;

import io.work.onlinestore.data.model.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Address.CompositeKey> {
    List<Address> findAllByCustomerId(Integer customerId);
}
