package maxime.mica.osgi;

import maxime.mica.servlet.EmbeddingJettyWithServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    private Server server;

    @Override
    public void start(BundleContext context) throws Exception {
        server = new Server(8080);

        ServletContextHandler servletContextHandler = new ServletContextHandler();

        servletContextHandler.setContextPath("/");

        server.setHandler(servletContextHandler);


        servletContextHandler.addServlet(new ServletHolder(new EmbeddingJettyWithServlet.HelloServlet()), "/*");
        server.start();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        server.stop();
    }
}