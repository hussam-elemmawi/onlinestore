package io.work.onlinestore.data.model;

import java.sql.Timestamp;
import java.util.List;

public class FullCustomer {

    private Customer customer;
    private List<Address> addresses;
    private List<Phone> phones;

    public FullCustomer(Customer customer, List<Address> addresses, List<Phone> phones) {
        this.customer = customer;
        this.addresses = addresses;
        this.phones = phones;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }
}
