package beans;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;

@Data
public class User {


    String username;

    String password;

    String profile;

    Date date;

    String id;
    public User(String username, String password, String profile, Date date, String id) {
        this.username = username;
        this.password = password;
        this.profile = profile;
        this.date = date;
        this.id = id;
    }

    public User(String username, String password, String profile, String id) {
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
