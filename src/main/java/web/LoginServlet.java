package web;


import service.AddCookie;
import service.Checker;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String user = "";
    private static String pwd = "";
    Connection connection ;
    private static AddCookie addCookie;

    public LoginServlet(Connection connection) {
        this.connection = connection;
    }

    public LoginServlet() {

    }

    public static String getUser() {
        return user;
    }

    public static String getPwd() {
        return pwd;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Checker checker = new Checker(connection);
        user = request.getParameter("logemail");
        pwd = request.getParameter("logpsw");



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
        addCookie.add("my_name", user, response);
        addCookie.add("my_pwd", pwd, response);

    }


}
