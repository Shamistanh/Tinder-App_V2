package web;

import DAO.DAOUserSQL;
import beans.User;
import service.MyId;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;

public class RegisterServlet extends HttpServlet {


    static String username;
    static String password;
    static String repassword;
    MyId myId = new MyId();
    static String id;
    static  String profile;
     static Connection con;

    public RegisterServlet(Connection con) {
        this.con = con;
    }
    static DAOUserSQL daoUserSQL = new DAOUserSQL(con);



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (OutputStream os = resp.getOutputStream()) {
            Files.copy(Paths.get("content/templates", "registration.html"), os);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        username = req.getParameter("email");
        password = req.getParameter("psw");
        repassword = req.getParameter("repsw");
        profile = req.getParameter("profile");
        id = myId.generateId(username,password);
        System.out.println(username+", "+ password+", "+ repassword+", "+ profile+"," + id);
        User user  = new User(username,password,profile, Date.valueOf("2020-05-13"),id);
//        if (password == repassword){
        System.out.println(user);
            daoUserSQL.put(user);

            resp.sendRedirect("/liked");
//        }else {
//            resp.sendRedirect("/register");
//        }




    }


}
