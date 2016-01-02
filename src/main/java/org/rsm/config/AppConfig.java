package org.rsm.config;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.rsm.RsmResource;
import org.rsm.app.JaxRsApiApplication;
import org.rsm.service.CustomerService;
import org.rsm.service.CustomerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ext.RuntimeDelegate;
import java.util.Arrays;

@Configuration
public class AppConfig {
    @Bean( destroyMethod = "shutdown" )
    public SpringBus cxf() {
        return new SpringBus();
    }

    @Bean
    public Server jaxRsServer() {
        JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance().createEndpoint( jaxRsApiApplication(), JAXRSServerFactoryBean.class );
        factory.setServiceBeans( Arrays.< Object >asList( rsmService() ) );
//        factory.setAddress(factory.getAddress());
        factory.setProviders( Arrays.< Object >asList( jsonProvider() ) );
        return factory.create();
    }

    @Bean
    public JaxRsApiApplication jaxRsApiApplication() {
        return new JaxRsApiApplication();
    }

    @Bean
    public RsmResource rsmService() {
        return new RsmResource();
    }

    @Bean
    public CustomerService customerService() {
        return new CustomerServiceImpl();
    }

    @Bean
    public JacksonJsonProvider jsonProvider() {
        return new JacksonJsonProvider();
    }
}
