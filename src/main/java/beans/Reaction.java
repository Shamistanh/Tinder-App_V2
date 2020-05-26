package beans;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@AllArgsConstructor
@Data
public class Reaction {

    String id;
    String who;
    String whom;
    String reaction;
    Date date;

    public Reaction(String who, String whom, String reaction) {
        this.who = who;
        this.whom = whom;
        this.reaction = reaction;
    }
}
