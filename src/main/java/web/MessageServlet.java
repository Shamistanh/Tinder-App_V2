package web;

import DAO.DAOMessageSQL;
import DAO.DAOUserSQL;
import beans.Message;
import lombok.SneakyThrows;
import service.MyId;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MessageServlet  extends HttpServlet {
    MyId myId = new MyId();

    String sender_id= "sinama";
    String msg_text="deyer";
    List<String> messages = new ArrayList<>();

    private Connection con;
    DAOMessageSQL daoMessageSQL = new DAOMessageSQL(con);
    DAOUserSQL daoUserSQL = new DAOUserSQL(con);
    private TemplateEngine engine;

    public MessageServlet(TemplateEngine engine, Connection con) throws SQLException, ClassNotFoundException {
        this.engine = engine;
        this.con = con;
    }



    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> data = new HashMap<>();
        Cookie[] cookies = req.getCookies();

        HashMap<String, String> cookieMap = new HashMap<>();
        Arrays.stream(cookies).map(e->cookieMap.put(e.getName(),e.getValue()));

            sender_id = cookieMap.get("sender_id");
            List<Message> all_sent = getMessages(myId.id(req, con),sender_id);
            List<Message> all_reveived = getMessages(sender_id, myId.id(req,con));
            data.put("sents", String.valueOf(all_sent));
            System.out.println("bu da sender id dir" + sender_id);
            data.put("receivings", String.valueOf(all_reveived));
            data.put("opp_profile", daoUserSQL.get(sender_id).get().getProfile());
            data.put("header_name", daoUserSQL.get(sender_id).get().getUsername());



        engine.render("chat.ftl", data,resp);


    }

    private List<Message> getMessages(String id, String sender_id) {
        return daoMessageSQL.getBy(e->e.getWho().equals(id) && e.getWhom().equals(sender_id));
    }


    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        msg_text = req.getParameter("msj");
        daoMessageSQL.put(new Message(myId.id(req,con),sender_id, msg_text, Date.valueOf(java.time.LocalDate.now())));
        messages.add(msg_text);
        System.out.println(msg_text);
        resp.sendRedirect("/messages");

    }
}