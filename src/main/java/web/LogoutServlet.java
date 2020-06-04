package web;

import service.MyCookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.stream.Collectors;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        MyCookie.remove(req, resp);

        try (PrintWriter w = resp.getWriter()) {
            w.write("<h1 style='color:green;'>You successfully logged out</h1>");
            w.write("<a style= 'text-decoration:none; size: 30px;' href='/login'>Go to Login Page</a>");

        }
    }

}