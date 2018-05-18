import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;


public class MainClass {

    public static void main(String[] args) {
        // main class for the Jetty HTTP Servlet server
        Server server = new Server();

        // primary connector for the Jetty server over TCP/IP
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        // this handle will serve static content and handle If-Modified-Since headers
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("./src/main/resources/");

        // allows for simple construction of a context with ServletHandler and optionally session and security handlers
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/connect");

        // holds the name, params and some state of a javax.servlet.Servlet instance
        ServletHolder servletHolder = new ServletHolder("ws-events", new WebSocketServlet() {
            @Override
            public void configure(WebSocketServletFactory webSocketServletFactory) {
                webSocketServletFactory.register(MainWebSocket.class);
            }
        });
        context.addServlet(servletHolder, "/");

        // will call each contained handler in turn until an exception is thrown
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {context, resource_handler});
        server.setHandler(handlers);

        try {
            server.start();
            server.join();
        } catch (Throwable t) {
            t.getStackTrace();
        }
    }
}