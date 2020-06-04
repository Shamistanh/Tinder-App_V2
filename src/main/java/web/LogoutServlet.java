package web;

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
        Cookie[] cookies = req.getCookies();

        // this is actually deleting
        Arrays.stream(cookies)
                .forEach(c -> {
                    c.setMaxAge(0);
                    resp.addCookie(c);
                });

        try (PrintWriter w = resp.getWriter()) {
            w.write("<center><h1 style='color:green;'>You successfully logged out</h1></center>");
          //  w.write("<center><a style= 'text-decoration:none;' href='/login'>Login</a></center>");

        }
    }

}