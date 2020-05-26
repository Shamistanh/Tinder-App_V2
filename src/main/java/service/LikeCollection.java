package service;

import beans.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LikeCollection {



    public static List<User> like_collection(Connection con){
        List<User> liked_coll = new ArrayList<>();
        try {

            PreparedStatement st = con
                    .prepareStatement("SELECT users.id, users.username,users.password, users.pic, users.created_at  FROM users, reactions WHERE users.id = reactions.whom");
            ResultSet rset = st.executeQuery();
            while (rset.next()) {
                String id = rset.getString("id");
                String username = rset.getString("username");
                String password = rset.getString("password");
                String profile = rset.getString("pic");
                Date date = rset.getDate("created_at");
                liked_coll.add(new User(id, username,password,date, profile));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return liked_coll;
    }
}
