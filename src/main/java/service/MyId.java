package service;

import DAO.DAOUserSQL;
import web.LoginServlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MyId {

    static LoginServlet loginServlet = new LoginServlet();

    public static String id(HttpServletRequest req,Connection con){
        DAOUserSQL daoUserSQL = new DAOUserSQL(con);
        Cookie[] cookies = req.getCookies();
       // String my_name = Arrays.stream(cookies).filter(e->e.getName().equals("my_name")).map(e->e.getName()).collect(Collectors.joining());
      // String my_pwd = Arrays.stream(cookies).filter(e->e.getName().equals("my_pwd")).map(e->e.getValue()).collect(Collectors.joining());
        String my_name = loginServlet.getUser();
        String my_pwd = loginServlet.getPwd();
        System.out.println("butun: " + daoUserSQL.getAll());
        System.out.println("burda olmalidir "+daoUserSQL.getBy(e->e.getUsername().equals(my_name)));
        return daoUserSQL.getAll().stream().filter(e->e.getUsername()
                .equals(my_name) && e.getPassword()
                .equals(my_pwd)).map(e->e.getId())
                .collect(Collectors.joining());

    }

    public String generateId(String username, String password) {
        return String.valueOf(username.hashCode()+password.hashCode());
    }
}
