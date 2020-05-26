package web;

import beans.User;
import lombok.SneakyThrows;
import service.AddCookie;
import service.LikeCollection;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

public class UserServlet extends HttpServlet {

    private final TemplateEngine engine;
    private static AddCookie addCookie = new AddCookie();
    String sender_id;
    Connection con;

    public UserServlet(TemplateEngine engine, Connection con) {
        this.con = con;
        this.engine = engine;
    }



    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HashMap<String, String> data = new HashMap<>();
        LikeCollection likeCollection = new LikeCollection();
        List<User> students = likeCollection.like_collection(con);
        System.out.println(likeCollection.like_collection(con));
        data.put("users", String.valueOf(students));
        sender_id = req.getParameter("user_idd");
        addCookie.add("sender_id",sender_id,resp);
        if (sender_id !=null){
            resp.sendRedirect("/messages");
        }
        engine.render("people-list.ftl", data, resp);

    }

}