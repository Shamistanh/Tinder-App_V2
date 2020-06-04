package web;

import beans.User;
import lombok.SneakyThrows;
import service.MyCookie;
import service.GiveMeUser;
import service.MyId;
import service.React;

import javax.servlet.ServletException;
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
    private static MyCookie addCookie;
    Connection con;

    public LikeServlet(TemplateEngine engine, Connection con) {
        this.con = con;
        this.engine = engine;
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        WHO = MyId.id(req,con);
        reaction = req.getParameter("reaction");
        WHOM = GiveMeUser.giveMeUserToLike(con, WHO);


        HashMap<String,Object> data = new HashMap<>();
        data.put("username", WHOM.getUsername());
        data.put("profile", WHOM.getProfile());
        engine.render("like-page.ftl", data, resp);

        if (reaction != null) {
            if (reaction.equals("like")) {
                React.ireact(con, WHO, WHOM.getId(), "1");
                MyCookie.add("liked_id",WHOM.getId(),resp);

            } else if (reaction.equals("dislike")) {
                React.ireact(con, WHO, WHOM.getId(), "2");
            }
        }


    }
}
