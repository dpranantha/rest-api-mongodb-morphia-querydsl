package org.rsm;

import com.github.fakemongo.Fongo;
import com.mongodb.*;
import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder;
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
    private Mongo mongoServer;

    @Before
    public void setup() {
        //Create embedded Mongo server
        try {
            mongoServer = new EmbeddedMongoBuilder()
                    .version("3.1.4")
                    .bindIp("127.0.0.1")
                    .port(27017)
                    .build();
        } catch (java.io.IOException e) {
            mongoServer = new Fongo("customer").getMongo();
        }
        DB db = mongoServer.getDB("customer");
        DBCollection collection = db.getCollection("Customer");
        if (collection.count() == 0) {
            DBObject customer1 = new BasicDBObject("_id", "1")
                    .append("name", "Monica");
            DBObject customer2 = new BasicDBObject("_id", "2")
                    .append("name", "Chandler");
            collection.insert(customer1);
            collection.insert(customer2);
        }

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
            mongoServer.close();
            server.stop();
        } catch (java.lang.Exception e) {
            server.destroy();
        }
    }
}