package service;

import DAO.DAOUserSQL;
import beans.User;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class GiveMeUser extends HttpServlet {


    public static User giveMeUserToLike(Connection conn, String myID) {
        DAOUserSQL daoUserSQL = new DAOUserSQL(conn);
        List<User> all_users = daoUserSQL.getAll();
        return all_users.stream().filter(e->!myID.equals(e.getId())).findAny()
                .orElse(all_users.get((int)Math.random()*all_users.size()));
    }

}
