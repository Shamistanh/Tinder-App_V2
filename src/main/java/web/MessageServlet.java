package web;

import DAO.DAOMessageSQL;
import DAO.DAOUserSQL;
import beans.Message;
import lombok.SneakyThrows;
import service.MyCookie;
import service.GiveMeUser;
import service.MyId;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageServlet  extends HttpServlet {


    String sender_id= "sinama";
    String msg_text="deyer";
    List<String> messages = new ArrayList<>();

    private static Connection con;

    private TemplateEngine engine;

    public MessageServlet(TemplateEngine engine, Connection con) throws SQLException, ClassNotFoundException {
        this.engine = engine;
        this.con = con;
    }

    public static DAOMessageSQL daoMessageSQL = new DAOMessageSQL(con);
    static DAOUserSQL daoUserSQL = new DAOUserSQL(con);


    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, Object> data = new HashMap<>();


            sender_id = MyCookie.get("sender_id",req);
            List<Message> all_sent = GiveMeUser.giveMessages(MyId.id(req, con),sender_id, daoMessageSQL);
            List<Message> all_reveived = GiveMeUser.giveMessages(sender_id, MyId.id(req,con), daoMessageSQL);
            data.put("sents", all_sent);
            data.put("receivings", all_reveived);
            if (sender_id !=  null){
                data.put("opp_profile", daoUserSQL.get(sender_id).get().getProfile());
                data.put("header_name", daoUserSQL.get(sender_id).get().getUsername());
            }
        engine.render("chat.ftl", data,resp);

    }




    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        msg_text = req.getParameter("msj");
        sender_id = MyCookie.get("sender_id",req);
        System.out.println("bu da sender id dir" + sender_id);
        daoMessageSQL.put(new Message(MyId.id(req,con),sender_id, msg_text, Date.valueOf(java.time.LocalDate.now())));
        messages.add(msg_text);
        resp.sendRedirect("/messages");

    }
}