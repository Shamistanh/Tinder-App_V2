package beans;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@AllArgsConstructor
@Data
public class User {


    String username;

    String password;

    String profile;

    Date date;

    String id;


    public User(String id, String username, String profile , String password) {
        this.username = username;
        this.password = password;
        this.profile = profile;
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", profile='" + profile + '\'' +
                ", date=" + date +
                ", id='" + id + '\'' +
                '}';
    }
}
