package service;

import DAO.DAOUserSQL;
import beans.User;
import web.LoginServlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyId {

    static LoginServlet loginServlet;
    private static Connection con;
    static DAOUserSQL daoUserSQL = new DAOUserSQL(con);

    public MyId(Connection con) {
        this.con = con;
    }

    public static String id(HttpServletRequest req, Connection con) {

        String my_name = AddCookie.get("my_name", req);
        String my_pwd = AddCookie.get("my_pwd", req);


        return daoUserSQL.getAll().stream()
                .filter(e -> e.getUsername()
                        .equals(my_name) && e.getPassword()
                        .equals(my_pwd))
                .map(u -> u.getId())
                .collect(Collectors.joining());


    }

    public static String generateId(String username, String password) {
        return String.valueOf(username.hashCode() + password.hashCode());
    }
}
