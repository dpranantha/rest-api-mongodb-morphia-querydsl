package org.rsm.dao;

import com.google.common.collect.ImmutableList;
import com.mongodb.MongoClient;
import com.mysema.query.mongodb.morphia.MorphiaQuery;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.rsm.model.Customer;
import org.rsm.model.QCustomer;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class CustomerDao {

    private final MongoClient mongo;
    private final Morphia morphia;
    private final String dbName;
    private final Datastore datastore;
    private final QCustomer qCustomer;

    public CustomerDao(MongoClient mongo, Morphia morphia, String dbName) {
        this.mongo = checkNotNull(mongo, "mongo client is Null");
        this.morphia = checkNotNull(morphia, "morphia is Null");
        this.dbName = checkNotNull(dbName, "dbName is Null");
        this.datastore = this.morphia.createDatastore(this.mongo,this.dbName);
        this.qCustomer = new QCustomer(this.dbName);
    }

    public List<Customer> findAllCustomer() {
        MorphiaQuery<Customer> query = new MorphiaQuery<Customer>(morphia, datastore, qCustomer);
        return query.list();
    }

    public List<Key<Customer>> insertCustomers(Iterable<Customer> customers) {
        return ImmutableList.copyOf(datastore.save(customers));
    }
}
