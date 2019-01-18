package io.work.onlinestore.services.interfaces;

import io.work.onlinestore.data.model.Customer;
import io.work.onlinestore.data.model.FullCustomer;
import io.work.onlinestore.util.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    List<Customer> getAllCustomers() throws ServiceException;
    FullCustomer getCustomer(Integer customerId) throws ServiceException;
    Integer createCustomer(Customer customer) throws ServiceException;
    void updateCustomer(Customer customer) throws ServiceException;
    void deleteCustomer(Integer customerId) throws ServiceException;
}
