package org.rsm.service;

import org.mongodb.morphia.Key;
import org.rsm.dao.CustomerDao;
import org.rsm.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerDao customerDao;

    @Autowired
    public CustomerServiceImpl() {
        //TODO: find better way. this is done because we use XML instead of annotation for CustomerDao
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("mongodb-config-factory.xml");
        this.customerDao = (CustomerDao) context.getBean("customerDao");
    }

    @Override
    @Transactional
    public List<Customer> getAllCustomers() {
        return customerDao.findAllCustomer();
    }

    @Override
    public List<Key<Customer>> insertCustomers(Iterable<Customer> customers) {
        return customerDao.insertCustomers(customers);
    }
}
