package web;

import DAO.DAOUserSQL;
import beans.User;
import service.AddCookie;
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

    static String id;
    static  String profile;
     static Connection con;

    public RegisterServlet(Connection con) {
        this.con = con;
    }
    public static DAOUserSQL daoUserSQL = new DAOUserSQL(con);


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
        id = MyId.generateId(username,password);
        User user  = new User(username,password,profile,id);
        System.out.println(user);
       if (password.equals(repassword)){
            daoUserSQL.put(user);
           AddCookie.add("my_name", username, resp);
           AddCookie.add("my_pwd", password, resp);
            resp.sendRedirect("/liked");
        }else {
            resp.sendRedirect("/register");
        }




    }


}
