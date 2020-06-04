package service;

import DAO.DAOReactionSQL;
import DAO.DAOUserSQL;
import beans.Reaction;
import beans.User;

import javax.servlet.http.HttpServlet;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class React{

    public static Connection con;
    public React(Connection con) {
        React.con = con;
    }
    public static DAOReactionSQL daoReactionSQL = new DAOReactionSQL(con);

    public static void ireact(Connection con, String who, String whom, String reaction) throws Exception {

        daoReactionSQL.put(new Reaction(who,whom,reaction));
    }

}
