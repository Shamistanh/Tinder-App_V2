package web;


import service.MyCookie;
import service.Checker;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static String user = "";
    public static String pwd = "";
    Connection connection ;


    public LoginServlet(Connection connection) {
        this.connection = connection;
    }
    Checker checker = new Checker(connection);
    public LoginServlet() {

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        user = request.getParameter("logemail");
        pwd = request.getParameter("logpsw");
        MyCookie.add("my_name", user, response);
        MyCookie.add("my_pwd", pwd, response);
        System.out.println("i am now in login servlet and user and pwd: "+user+" "+pwd);
        if(checker.check(user, pwd)){
            response.sendRedirect("/liked");
        }else {
            response.sendRedirect("/login");
        }


    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        try (OutputStream os = response.getOutputStream()) {
            Files.copy(Paths.get("content/templates", "login.html"), os);
        }


    }


}
