package io.work.onlinestore.services.implemetation;

import io.work.onlinestore.data.model.Address;
import io.work.onlinestore.data.model.Customer;
import io.work.onlinestore.data.model.FullCustomer;
import io.work.onlinestore.data.model.Phone;
import io.work.onlinestore.data.repository.AddressRepository;
import io.work.onlinestore.data.repository.CustomerRepository;
import io.work.onlinestore.data.repository.PhoneRepository;
import io.work.onlinestore.services.interfaces.CustomerService;
import io.work.onlinestore.util.exception.RecordNotFoundException;
import io.work.onlinestore.util.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    private final static Logger logger = Logger.getLogger(CustomerServiceImpl.class.getSimpleName());

    @Override
    public List<Customer> getAllCustomers() throws ServiceException {
        try {
            List<Customer> customers = new ArrayList<>();
            customerRepository.findAll().forEach(customers::add);
            return customers;
        } catch (Exception e) {
            logger.info("Can't get all customer " + e.getMessage() + " @ " + new Date(System.currentTimeMillis()));
            throw new ServiceException("Can't get all customer " + e.getMessage());
        }
    }

    @Override
    public FullCustomer getCustomer(Integer customerId) throws ServiceException {
        try {
            Customer customer = customerRepository.findById(customerId).get();
            if (customer == null) {
                throw new RecordNotFoundException("Customer not found customerId: " + customerId);
            }
            List<Address> addresses = addressRepository.findAllByCustomerId(customerId);
            List<Phone> phones = phoneRepository.findAllByCustomerId(customerId);
            FullCustomer fullCustomer = new FullCustomer(customer,addresses,phones);
            return fullCustomer;
        } catch (Exception e) {
            logger.info("Can't get customer by code " + customerId + ", " + e.getMessage() +
                    " @ " + new Date(System.currentTimeMillis()));
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Integer createCustomer(Customer customer) throws ServiceException {
        try {
            customer.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            customer = customerRepository.save(customer);
            return customer.getCustomerId();
        } catch (Exception e) {
            logger.info("Can't create customer " + e.getMessage() + " @ " + new Date(System.currentTimeMillis()));
            throw new ServiceException("Can't create customer " + e.getMessage());
        }
    }

    @Override
    public void updateCustomer(Customer customer) throws ServiceException {
        try {
            if(customer.getCreatedAt() == null)
                customer.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            customerRepository.save(customer);
        } catch (Exception e) {
            logger.info("Can't update customer by code " + customer.getCustomerId() + ", " + e.getMessage() +
                    " @ " + new Date(System.currentTimeMillis()));
            throw new ServiceException("Can't update customer " + e.getMessage());
        }
    }

    @Override
    public void deleteCustomer(Integer customerId) throws ServiceException {
        try {
            customerRepository.deleteByCustomerId(customerId);
        } catch (Exception e) {
            logger.info("Can't delete customer by code " + customerId + ", " + e.getMessage() +
                    " @ " + new Date(System.currentTimeMillis()));
            throw new ServiceException("Can't delete customer " + e.getMessage());
        }
    }
}
