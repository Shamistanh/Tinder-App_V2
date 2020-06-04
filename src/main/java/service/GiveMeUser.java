package service;

import DAO.DAOMessageSQL;
import DAO.DAOReactionSQL;
import DAO.DAOUserSQL;
import beans.Message;
import beans.User;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GiveMeUser extends HttpServlet {

    public static User giveMeUserToLike(Connection conn, String myID) {
         DAOUserSQL daoUserSQL = new DAOUserSQL(conn);
        List<User> all_users = daoUserSQL.getAll();
        List<User> meExcluded =  all_users.stream().filter(u->!u.getId().equals(myID)).distinct().collect(Collectors.toList());
        int random= (int)(Math.random()*meExcluded.size());
        return meExcluded.get(random);
    }
    public static List<User> giveMeLikedUsers(Connection con){
        DAOReactionSQL daoReactionSQL = new DAOReactionSQL(con);
        DAOUserSQL daoUserSQL = new DAOUserSQL(con);
        List<User> liked_users = new ArrayList<>();
        List<String> whom = daoReactionSQL.getAll()
                .stream().filter(e->e.getReaction().equals("1")).map(u->u.getWhom()).collect(Collectors.toList());
       for (String id : whom){
           liked_users.add(daoUserSQL.get(id).orElseThrow(RuntimeException::new));
       }
        System.out.println("iked users "+ liked_users);
        return liked_users;
    }

    public static List<Message> giveMessages(String id, String sender_id, DAOMessageSQL daoMessageSQL) {
        List<Message> msg = new ArrayList<>();
        msg = daoMessageSQL.getBy(e->e.getWho().equals(id) && e.getWhom().equals(sender_id));
        if (msg!=null){
            return msg;
        }
        return new ArrayList<>();


    }
}
