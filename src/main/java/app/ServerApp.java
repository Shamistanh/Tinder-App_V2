package app;


import db.ConnDetails;
import db.DbConn;
import db.DbSetup;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import web.*;

import java.sql.Connection;
import java.sql.DriverManager;

public class ServerApp {
    public static void main(String[] args) throws Exception {

        // temporary
   // DbSetup.migrate(ConnDetails.url, ConnDetails.username, ConnDetails.password);
      //  DbSetup.migrate(HerokuEnv.jdbc_url(), HerokuEnv.jdbc_username(), HerokuEnv.jdbc_password());
        // temporary
   // Connection conn = DbConn.create(ConnDetails.url, ConnDetails.username, ConnDetails.password);
       // Connection conn = DbConn.createFromURL(HerokuEnv.jdbc_url());
        Class.forName(ConnDetails.dbDriver);
        Connection conn = DriverManager.getConnection(ConnDetails.url,
                ConnDetails.username,
                ConnDetails.password);



        Server server = new Server(HerokuEnv.port());
        TemplateEngine engine = TemplateEngine.folder("content/templates");
        ServletContextHandler handler = new ServletContextHandler();

        handler.addServlet(new ServletHolder(new LikeServlet(engine,conn)), "/liked");
        handler.addServlet(new ServletHolder(new UserServlet(engine,conn)), "/user-list");
        //handler.addServlet(new ServletHolder(new RedirectServlet("/register")), "/");
        handler.addServlet(new ServletHolder(new MessageServlet(engine,conn)), "/messages");
        handler.addServlet(new ServletHolder(new RegisterServlet(conn)), "/register/*");
        handler.addServlet(new ServletHolder(new LoginServlet(conn)), "/login");
        handler.addServlet(new ServletHolder(new LogoutServlet()), "/logout");
        handler.addServlet(new ServletHolder(new StaticServlet("images")), "/images");
        handler.addServlet(new ServletHolder(new StaticServlet("js")), "/js/*");
        handler.addServlet(new ServletHolder(new StaticServlet("css")), "/css/*");

        server.setHandler(handler);

        server.start();
        server.join();
    }
}
