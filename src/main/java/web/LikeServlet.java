package web;

import DAO.DAOUserSQL;
import beans.User;
import lombok.SneakyThrows;
import service.AddCookie;
import service.GiveMeUser;
import service.MyId;
import service.React;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;


/**
 * I want to clarify that in this servlet who is logged user's id,
 * but 'whom' is whom i will like (whole user)
 */
public class LikeServlet extends HttpServlet {

    static String WHO="";
    static User WHOM;
    static String reaction;
    TemplateEngine engine;
    private static AddCookie addCookie;
    Connection con;

    public LikeServlet(TemplateEngine engine, Connection con) {
        this.con = con;
        this.engine = engine;
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GiveMeUser giveMeUser = new GiveMeUser();
        MyId myId = new MyId();

        WHO = myId.id(req,con);
        reaction = req.getParameter("reaction");
        WHOM = giveMeUser.giveMeUserToLike(con, WHO);


        HashMap<String,String> data = new HashMap<>();
        data.put("username", WHOM.getUsername());
        data.put("profile", WHOM.getProfile());
        engine.render("like-page.ftl", data, resp);

        React react = new React();
        if (reaction != null) {
            if (reaction.equals("like")) {
                react.ireact(con, WHO, WHOM.getId(), "1");

                addCookie.add("liked_id",WHOM.getId(),resp);


            } else if (reaction.equals("dislike")) {
                react.ireact(con, WHO, WHOM.getId(), "2");
            }
        }


    }
}
