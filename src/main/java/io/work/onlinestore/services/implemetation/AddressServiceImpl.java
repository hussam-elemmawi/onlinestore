package io.work.onlinestore.services.implemetation;

import io.work.onlinestore.data.model.Address;
import io.work.onlinestore.data.repository.AddressRepository;
import io.work.onlinestore.services.interfaces.AddressService;
import io.work.onlinestore.util.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> getAllAddresses() throws ServiceException {
        return null;
    }

    @Override
    public Address getAddress(Address.CompositeKey Id) throws ServiceException {
        return null;
    }

    @Override
    public Integer createAddress(Address address) throws ServiceException {
        return null;
    }

    @Override
    public void updateAddress(Address address) throws ServiceException {

    }

    @Override
    public void deleteAddress(Address.CompositeKey Id) throws ServiceException {

    }
}
