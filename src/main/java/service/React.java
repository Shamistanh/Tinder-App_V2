package service;

import DAO.DAOReactionSQL;
import beans.Reaction;
import javax.servlet.http.HttpServlet;
import java.sql.Connection;
import java.util.HashMap;


public class React extends HttpServlet {

    static HashMap<String, String> rmap = new HashMap<>();

    public static void ireact(Connection con, String who, String whom, String reaction){
        DAOReactionSQL daoReactionSQL  = new DAOReactionSQL(con);
        daoReactionSQL.put(new Reaction(who,whom,reaction));
    }
}
