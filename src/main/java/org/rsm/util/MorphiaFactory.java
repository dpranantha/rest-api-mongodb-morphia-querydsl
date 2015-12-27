package org.rsm.util;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Morphia;
import org.rsm.model.Customer;

import java.net.UnknownHostException;

public class MorphiaFactory {
    private String host;
    private int    port;

    public MongoClient getMongoInstance() throws UnknownHostException {
        return new MongoClient(host, port);
    }

    public Morphia getMorphiaInstance() {
        Morphia morphia = new Morphia();

        morphia
                .map(Customer.class);

        return morphia;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = Integer.parseInt(port);
    }
}
