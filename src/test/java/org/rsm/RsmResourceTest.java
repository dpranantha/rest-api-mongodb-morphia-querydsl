package org.rsm;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rsm.config.AppConfig;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.hasItems;

public class RsmResourceTest {
    private Server server;

    @Before
    public void setup() {
        //Create server
        server = new Server(8080);
        // Register and map the dispatcher servlet
        final ServletHolder servletHolder = new ServletHolder( new CXFServlet() );
        final ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        context.addServlet(servletHolder, "/*" );
        context.addEventListener( new ContextLoaderListener() );

        context.setInitParameter("contextClass", AnnotationConfigWebApplicationContext.class.getName() );
        context.setInitParameter("contextConfigLocation", AppConfig.class.getName() );

        server.setHandler(context);

        try {
            server.start();
        } catch (java.lang.Exception e) {
            server.destroy();
        }
    }

    @Test
    public void should_contain_all_customers(){
        get("rsm/rest-api/customers").then().assertThat().body("name", hasItems("Monica","Chandler"));
    }

    @After
    public void close() {
        try {
            server.stop();
        } catch (java.lang.Exception e) {
            server.destroy();
        }
    }
}