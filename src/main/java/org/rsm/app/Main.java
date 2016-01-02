package org.rsm.app;

import org.apache.cxf.common.i18n.Exception;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.rsm.config.AppConfig;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class Main {
    public static void main (String[] args) {
        //Create server
        Server server = new Server(8080);
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
            server.join();
        } catch (java.lang.Exception e) {
            server.destroy();
        } finally {
            try {
                server.stop();
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }
}
