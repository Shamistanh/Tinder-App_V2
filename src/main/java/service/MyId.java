package service;

import DAO.DAOUserSQL;
import web.LoginServlet;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.stream.Collectors;

public class MyId {

    static LoginServlet loginServlet;
    private static Connection con;
    static DAOUserSQL daoUserSQL = new DAOUserSQL(con);

    public MyId(Connection con) {
        this.con = con;
    }

    public static String id(HttpServletRequest req, Connection con) {

        String my_name = MyCookie.get("my_name", req);
        String my_pwd = MyCookie.get("my_pwd", req);


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
