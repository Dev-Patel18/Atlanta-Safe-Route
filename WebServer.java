import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class WebServer {
    public static void main(String[] args) {
        try {
            Server server = new Server(8080);

            // Configure the servlet context handler for API endpoints
            ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
            servletHandler.setContextPath("/api");
            servletHandler.addServlet(new ServletHolder(new OpenRouteServiceServlet()), "/directions");

            // Enable CORS
            FilterHolder cors = servletHandler.addFilter(CrossOriginFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
            cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
            cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,POST,HEAD");
            cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin");

            server.setHandler(servletHandler);

            System.out.println("Starting server...");
            server.start();
            System.out.println("Server started on port 8080");

            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
