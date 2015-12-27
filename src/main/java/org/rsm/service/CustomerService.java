package org.rsm.service;

import org.mongodb.morphia.Key;
import org.rsm.model.Customer;

import java.util.List;

public interface CustomerService {
    public List<Customer> getAllCustomers();
    public List<Key<Customer>> insertCustomers(Iterable<Customer> customers);
}
