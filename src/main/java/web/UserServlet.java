package web;

import beans.User;
import lombok.SneakyThrows;
import service.AddCookie;
import service.GiveMeUser;
import service.React;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

public class UserServlet extends HttpServlet {

    private final TemplateEngine engine;
    String sender_id;
    Connection con;

    public UserServlet(TemplateEngine engine, Connection con) {
        this.con = con;
        this.engine = engine;
    }


    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HashMap<String, Object> data = new HashMap<>();
        List<User> l_users = GiveMeUser.giveMeLikedUsers(con);
        data.put("users", l_users);
        sender_id = req.getParameter("user_idd");
        AddCookie.add("sender_id",sender_id,resp);
        System.out.println("Userdeki sender_id: "+sender_id);
        if (sender_id !=null){
            resp.sendRedirect("/messages");
        }
        engine.render("people-list.ftl", data, resp);

    }

}