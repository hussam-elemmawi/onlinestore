package io.work.onlinestore.services.interfaces;

import io.work.onlinestore.data.model.Address;
import io.work.onlinestore.util.exception.ServiceException;

import java.util.List;

public interface AddressService {
    List<Address> getAllAddresses() throws ServiceException;
    Address getAddress(Address.CompositeKey Id) throws ServiceException;
    Integer createAddress(Address address) throws ServiceException;
    void updateAddress(Address address) throws ServiceException;
    void deleteAddress(Address.CompositeKey Id) throws ServiceException;
}
