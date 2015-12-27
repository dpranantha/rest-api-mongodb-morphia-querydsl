package org.rsm;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.mongodb.morphia.Key;
import org.rsm.model.Customer;
import org.rsm.service.CustomerService;
import org.rsm.service.CustomerServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/rest-api")
public class RsmResource {
    private CustomerService customerService;

    //Need to do this because injection doesn't work with hk2
    public RsmResource() {
        final ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();
        DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class);
        DynamicConfiguration config = dcs.createDynamicConfiguration();
        config.bind(BuilderHelper.link(CustomerServiceImpl.class).to(CustomerService.class).in(Singleton.class).build());
        config.commit();

        this.customerService = locator.getService(CustomerServiceImpl.class);
    }

    @Path("/customers")
    @GET
    @Produces("application/json")
    @Transactional
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @POST
    @Path("/customers")
    @Consumes("application/json")
    public Response insertCustomers(List<Customer> customers) {
        List<Key<Customer>> keys = customerService.insertCustomers(ImmutableList.copyOf(customers));
        return Response.status(201).tag(Joiner.on(",").join(keys)).build();
    }
}
