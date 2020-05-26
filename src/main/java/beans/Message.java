package beans;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@AllArgsConstructor
@Data
public class Message {


    String who;
    String whom;
    String message="";
    Date date;

}
